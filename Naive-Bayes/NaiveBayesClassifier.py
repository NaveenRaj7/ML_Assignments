# @authors:
# Naveen Raj Datha
# Saagar Gaikwad	
# Sumit Kundu

import csv
import random
import math

# This function reads the file containing data

def loadCsv(filename):
	lines = csv.reader(open(filename, "rt"))
	dataset = list(lines)
	# for i in range(len(dataset)):
	# 	dataset[i] = [float(x) for x in dataset[i]]
	return dataset

	# This method splits dataset in the specified ratio

def splitDataset(dataset, splitRatio):
	trainSize = int(len(dataset) * splitRatio)
	trainSet = []
	testSet = list(dataset)
	while len(trainSet) < trainSize:
		index = random.randrange(len(testSet))
		trainSet.append(testSet.pop(index))
	return [trainSet, testSet]

# This function separates the given instances based on their class values

def separateByClass(dataset):
	separated = {}

	for i in range(len(dataset)):
		vector = dataset[i]
		if (vector[-1] not in separated):
			separated[vector[-1]] = []

		separated[vector[-1]].append(vector)


	att = {j: separateByAttValue(separated[j]) for j in separated}
	cp = {j: len(separated[j])/len(dataset) for j in separated}

	return separated , att, cp

# This function separates the given instances based on their attribute values for each attribute

def separateByAttValue(dataset):

	separated2 = {}
	for j in range(4):
		separated2[j] = {}
		separated = {}
		for i in range(len(dataset)):
			vector = dataset[i]
			if (vector[j] not in separated):
				separated[vector[j]] = 0
				separated2[j][vector[j]] = []
			separated[vector[j]] = separated[vector[j]]+1
		#print('Separated attVal: {0}'.format(separated))
		prob = [separated[x]/sum(separated.values()) for x in separated]
		i=0
		for x in separated:
			separated2[j][x].append(prob[i])
			i+=1
	return separated2
	#print(' attVal: {0}'.format(separated2))

# This function calculates the probability of each class and conditional probability for each attribute value

def calculateClassProbabilities(summaries, inputVector):
	probabilities = {}
	for aclass in summaries:
		probabilities[aclass] = 1
		#print('S: {0}'.format(probabilities))
		for j in range(4):
			x = inputVector[j]
			#print('x: {0}'.format(x))
			if (inputVector[j] in summaries[aclass][j]):
				#if summaries[aclass][j].has_key(inputVector[j]):
				probabilities[aclass] *= summaries[aclass][j][inputVector[j]][0]
			else:
				probabilities[aclass] *= 0
	return probabilities


# This function predicts the class for each test instance based on the model designed 

def getPredictions(summaries, testSet, cp):
	predictions = []
	for i in range(len(testSet)):
		att_pr = calculateClassProbabilities(summaries, testSet[i])
		fin = {}
		for x in att_pr:
			for y in cp:
				if x == y:
					fin[x] = att_pr[x] * cp[y]
		# print('fin: {0}'.format(fin))
		mx = max(fin[x] for x in fin)
		for x in fin:
			if fin[x] == mx:
				# print('x: {0}'.format(x))
				predictions.append(x)
	#print('predictions: {0}'.format(predictions))
	return predictions


# This function calculates the accuracy by comparing actual and the predicted class values

def getAccuracy(testSet, predictions):
	correct = 0
	for x in range(len(testSet)):
		if testSet[x][-1] == predictions[x]:
			correct += 1
	return (correct/float(len(testSet))) * 100.0


# This function creates the confusion matrix based on predicted class values of the test data

def confusion_matrix():
	filename = 'Car_Data.csv'
	splitRatio = 0.67
	dataset = loadCsv(filename)
	trainingSet, testSet = splitDataset(dataset, splitRatio)
	#print('Split {0} rows into train={1} and test={2} rows'.format(len(dataset), len(trainingSet), len(testSet)))
	# prepare model
	#summaries = summarizeByClass(trainingSet)
	separated, att, cp = separateByClass(trainingSet)
	
    # test model
	predictions = getPredictions(att, testSet, cp)
	temp = {}
	for x in range(len(testSet)):
		vector = testSet[x]
		if (vector[-1] not in temp):
			temp[vector[-1]] = {};
		if (predictions[x] not in temp[vector[-1]]):
				temp[vector[-1]][predictions[x]] = 0;
				temp[predictions[x]][vector[-1]] = 0;
		if (vector[-1] not in temp[vector[-1]]):
			temp[vector[-1]][vector[-1]] = 0;
		temp[vector[-1]][predictions[x]]+=1;

	print('')
	print('CONFUSION MATRIX')
	print('Actual: {Predicted}')
	print('unacc: {0}'.format(temp['unacc']))
	print('good: {0}'.format(temp['good']))
	print('vgood: {0}'.format(temp['vgood']))
	print('acc: {0}'.format(temp['acc']))
	print('')

# This function implements the Naive Bayes algorithm with help of all the above functions

def model():
	filename = 'Car_Data.csv'
	splitRatio = 0.67
	dataset = loadCsv(filename)
	trainingSet, testSet = splitDataset(dataset, splitRatio)
	# print('Split {0} rows into train={1} and test={2} rows'.format(len(dataset), len(trainingSet), len(testSet)))
	# prepare model
	#summaries = summarizeByClass(trainingSet)
	separated, att, cp = separateByClass(trainingSet)
	# test model
	predictions = getPredictions(att, testSet, cp)
	accuracy = getAccuracy(testSet, predictions)
	return accuracy

	
# Perform 100 iterations to calculate the Average Accuracy and Average Error Rate	
avg = 0
total = 0
for i in range(100):
	avg = model()
	# print(avg)
	total = total + avg
	# print(total)    
avg_acc = total/100
err_rate = 100 - avg_acc
print('Average Accuracy: {0}%'.format(avg_acc))
print('Error Rate: {0}%'.format(err_rate))
confusion_matrix()
