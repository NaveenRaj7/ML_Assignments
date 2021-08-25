//@Authors
//Naveen Datha(217470)
//Saagar Gaikwad(217484)
//Sumit Kundu(217453)

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class KMeans {

	//Class variables
	//Attributes
	static String[] a1 = { "vhigh", "high", "med", "low" };
	static String[] a2 = { "vhigh", "high", "med", "low" };
	static String[] a3 = { "2", "3", "4", "5more" };
	static String[] a4 = { "2", "4", "more" };
	static String[] a5 = { "small", "med", "big" };
	static String[] a6 = { "low", "med", "high" };

	//classes
	static String[] c1 = { "unacc", "acc", "good", "vgood" };
	
	static Double[] xCentroid = new Double[6];
	static Double[][] allCentroid;
	static int[][] clusters;

	public static void main(String[] args) {
		
		//User input's number of Clusters desired
		Scanner scanner = new Scanner(System.in);
		System.out.print("Input the no. of clusters desired (any value between 1-1728): ");
		int numberOfClusters = scanner.nextInt();
		scanner.close();

		//Read car data file
		Path file = Paths.get("./car.data");
		String line = null;
		int numberOfTrainingSamples = 0;
		try (InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			while ((line = reader.readLine()) != null) {
				numberOfTrainingSamples++;
			}
		} catch (IOException x) {
			System.err.println(x);
		}

		String[][] multD = new String[numberOfTrainingSamples][7];

		try (InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			int i = 0;
			while ((line = reader.readLine()) != null) {
				multD[i][0] = line;
				i++;
			}
		} catch (IOException x) {
			System.err.println(x);
		}

		String ss = ",";
		String rawCarData[][] = new String[numberOfTrainingSamples][7];

		for (int i = 0; i < numberOfTrainingSamples; i++) {
			rawCarData[i] = multD[i][0].split(ss);
		}
		
		//Assign numeric values to each attribute value, so that the distance between instances and clusters can be calculated in attribute space
		int numCarData[][] = new int[numberOfTrainingSamples][6];

		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < numberOfTrainingSamples; i++) {
				switch (j) {
				case 0:
					for (int m = 0; m < a1.length; m++) {
						if (rawCarData[i][j].compareTo(a1[m]) == 0) {
							numCarData[i][j] = m;
						}
					}
					break;

				case 1:
					for (int m = 0; m < a2.length; m++) {
						if (rawCarData[i][j].compareTo(a2[m]) == 0) {
							numCarData[i][j] = m;
						}
					}
					break;

				case 2:
					for (int m = 0; m < a3.length; m++) {
						if (rawCarData[i][j].compareTo(a3[m]) == 0) {
							numCarData[i][j] = m;
						}
					}
					break;

				case 3:
					for (int m = 0; m < a4.length; m++) {
						if (rawCarData[i][j].compareTo(a4[m]) == 0) {
							numCarData[i][j] = m;
						}
					}
					break;

				case 4:
					for (int m = 0; m < a5.length; m++) {
						if (rawCarData[i][j].compareTo(a5[m]) == 0) {
							numCarData[i][j] = m;
						}
					}
					break;

				case 5:
					for (int m = 0; m < a6.length; m++) {
						if (rawCarData[i][j].compareTo(a6[m]) == 0) {
							numCarData[i][j] = m;
						}
					}
					break;

				}

			}

		}

		//declaring size of Class(static) variables
		allCentroid = new Double[numberOfClusters][6];
		clusters = new int[numberOfClusters][4];

		
		//Considering the first 'numberOfClusters' rows of the car data set as initial cluster centers
		Double[][] initialClusterCenters = new Double[numberOfClusters][6];

		for (int i = 0; i < numberOfClusters; i++) {
			for (int j = 0; j < 6; j++) {
				int a = numCarData[i][j];
				initialClusterCenters[i][j] = (double) a;
			}
		}

		newCentroid(rawCarData, numCarData, numberOfTrainingSamples, initialClusterCenters, numberOfClusters);

	}

	//Computes the new centers of the clusters recursively
	public static void newCentroid(String[][] rawCarData, int[][] numCarData, int numberOfTrainingSamples, Double[][] initialClusterCenters, int numberOfClusters) {

		Double[][] comp = new Double[numberOfClusters][6];
		Double[][] D = new Double[numberOfClusters][numberOfTrainingSamples];
		double temp2 = 0;
		double temp3 = 0;

		//Calculate Euclidean distance of each instance from each cluster center
		for (int i = 0; i < numberOfClusters; i++) {
			for (int j = 0; j < numberOfTrainingSamples; j++) {
				for (int p = 0; p < 6; p++) {
					temp2 = (initialClusterCenters[i][p] - numCarData[j][p]) * (initialClusterCenters[i][p] - numCarData[j][p]);
					temp3 = temp3 + temp2;
				}
				D[i][j] = Math.sqrt(temp3);
				temp3 = 0;
			}
		}
		
		//Group instances into clusters, such that the Euclidean distance between each instance and its cluster center is minimum when compared 
		//to other cluster centers in attribute space
		int ind = 0;
		int p = 0;
		int[][] arrays = new int[numberOfClusters][1728];
		for (int i = 0; i < numberOfTrainingSamples; i++) {
			Double lmin = D[0][i];
			for (int j = 0; j < numberOfClusters; j++) {
				if (Math.min(lmin, D[j][i]) < lmin) {
					ind = j;
					lmin = Math.min(lmin, D[j][i]);
				}
			}
			arrays[ind][p] = i;
			p++;
			ind = 0;
		}

		//Calculate new centroid for each cluster 
		int[][] va = new int[1728][];
		for (int i = 0; i < numberOfClusters; i++) {
			int v = 0;
			for (int j = 0; j < numberOfTrainingSamples; j++) {
				if (i == 0 && j == 0) {
					va[v] = numCarData[arrays[i][j]];
					v++;
				}
				if (arrays[i][j] != 0) {
					va[v] = numCarData[arrays[i][j]];
					v++;
				}
			}
			for (int m = 0; m < 6; m++) {
				int sum1 = 0;
				for (int n = 0; n < v; n++) {
					sum1 = sum1 + va[n][m];
				}
				xCentroid[m] = ((double) sum1 / (double) v);
			}
			for (int t = 0; t < 6; t++) {
				allCentroid[i][t] = xCentroid[t];
			}
		}
		for (int i = 0; i < numberOfClusters; i++) {
			for (int j = 0; j < 6; j++) {
				comp[i][j] = allCentroid[i][j];
			}
		}

		//Comparing previous iteration cluster centers with the current cluster centers. Stop iterating if the centers are similar
		if (!(Arrays.deepEquals(allCentroid, initialClusterCenters))) {
			newCentroid(rawCarData, numCarData, numberOfTrainingSamples, comp, numberOfClusters);
		}
		else {
			//Assign Class values to each cluster by choosing majority class of the training examples of that cluster
			String[][] va1 = new String[numberOfClusters][1728];
			for (int i = 0; i < numberOfClusters; i++) {
				int v = 0;
				for (int j = 0; j < numberOfTrainingSamples; j++) {
					if (i == 0 && j == 0) {
						va1[i][v] = rawCarData[arrays[i][j]][6];
						v++;
					}
					if (arrays[i][j] != 0) {
						va1[i][v] = rawCarData[arrays[i][j]][6];
						v++;
					}
				}
			}
			for (int i = 0; i < numberOfClusters; i++) {
				for (int j = 0; j < va1[i].length; j++) {
					if (va1[i][j] != null) {
						if (va1[i][j].contentEquals("unacc")) {
							clusters[i][0] = clusters[i][0] + 1;
						} else if (va1[i][j].contentEquals("acc")) {
							clusters[i][1] = clusters[i][1] + 1;
						} else if (va1[i][j].contentEquals("good")) {
							clusters[i][2] = clusters[i][2] + 1;
						} else if (va1[i][j].contentEquals("vgood")) {
							clusters[i][3] = clusters[i][3] + 1;
						}
					}
				}
			}
			
			//Print to console the number of training examples with 'unacc', 'acc', 'good', 'vgood' classes in each cluster
			int majority = 0;
			int classMax = 0;
			System.out.println();
			System.out.println("Cluster Classification using K-Means:");
			System.out.println("                            unacc  acc  good  vgood");
			 for (int i = 0; i < numberOfClusters; i++) { 
				 for (int j = 0; j < 4; j++) {
					 if(Math.max(classMax, clusters[i][j])>classMax){
						 classMax = clusters[i][j];
						 majority = j;
						 }
					 }
				 System.out.print("Cluster " + (i+1) +"(majority: "+ c1[majority] + "): ");
				 for (int j = 0; j < 4; j++) {
					 System.out.print(clusters[i][j]);
					 System.out.print("    "); 
					 }
				 System.out.println(); 
				 majority = 0;
				 classMax = 0;
				 }
			classificationErrorRate(clusters);
		}
	}

	//Calculates the Classification Error Rate
	public static void classificationErrorRate(int[][] cl2) {
		double cer = 0;
		double acc = 0;
		int sum_acc = 0;
		for (int i = 0; i < cl2.length; i++) {
			int maxi = 0;
			for (int j = 0; j < 4; j++) {
				if (Math.max(maxi, cl2[i][j]) > maxi) {
					maxi = cl2[i][j];
				}
			}
			sum_acc = sum_acc + maxi;
		}
		cer = (double) (1728 - sum_acc) / 1728;
		acc = (double) sum_acc / 1728;
		System.out.println();
		System.out.println("Classification error rate: " + cer);
		System.out.println("Accuracy: " + acc);
	}
}
