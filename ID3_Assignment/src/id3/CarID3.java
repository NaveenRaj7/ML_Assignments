/**
 * 
 */
package id3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import id3.AbstractCarAttrBase.CAR_ATTR_TYPE_e;

/**
 * This class implements the ID3 algorithm for car-data. 
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 *
 */
public class CarID3
{
    /**
     * This class represents a bucket which gets formed according to a value of the 
     * decision attribute at the parent node. The number of instances in such a bucket could be
     * considered as Sv(i), where i = 1, 2, ... number of possible values of the parent level 
     * decision attribute.
     * 
     * @author Rohith Ravindran
     */
    class ResultingBucket
    {
        ResultingBucket()
        {
            this.m_instList = new ArrayList< Instance >();
        }
        ArrayList<Instance> m_instList;
        int m_nDecisionAttrValue;
    }
    
    
    private Node m_rootNode;
    
    
    /**
     * This function learns the decision tree based on the input instances.
     * 
     * @param instanceList
     */
    public void LearnTree( ArrayList< Instance > instanceList )
    {
        ArrayList<ICarAttribute> attrList = new ArrayList<ICarAttribute>();
        attrList.add( new AttrBuyingValue());  // Attr#1
        attrList.add( new AttrMaintValue());   // Attr#2
        attrList.add( new AttrDoorsValue());   // Attr#3
        attrList.add( new AttrPersonsValue()); // Attr#4
        attrList.add( new AttrLugBootValue()); // Attr#5
        attrList.add( new AttrSafetyValue());  // Attr#6
        
        ResultingBucket rootBucket = new ResultingBucket();
        rootBucket.m_instList = instanceList;
        FindDecisionAttributeAndGrow( null, rootBucket, attrList );
        if( null != m_rootNode )
        {
            m_rootNode.DumpToXML( "CarID3Tree.xml" );
        }
    }
    
    
    /**
     * This function implements the core ID3 algorithm.
     * 
     * @param parentNode
     * @param bkt
     * @param attrList
     */
    private void FindDecisionAttributeAndGrow( Node parentNode, ResultingBucket bkt,
                                               ArrayList<ICarAttribute> attrList )
    {
        // Create the node and calculate entropy at the node(at first it will be the root).
        Node currentNode = new Node( parentNode, bkt );
        
        // If entropy is zero, there is no more information to extract.
        // So stop growing by setting the current node as leaf. 
        double dEntropy = currentNode.CalcEntropy();
        if( 0 == dEntropy )
        {
            currentNode.SetAsLeaf();
            currentNode.CalculateMostCommonLabel();
            return;
        }
        
        // If there is no more attribute left we could not grow anymore.
        // So return from processing by setting the current node as leaf.
        if( attrList.isEmpty())
        {
            currentNode.SetAsLeaf();
            currentNode.CalculateMostCommonLabel();
            return;
        }
        
        // Calculate the gain for each attribute.
        // Pick the attribute with the highest gain for further growing the tree.
        double dMaxInfoGain = 0.0;
        ICarAttribute maxGainAttr = null;
        for( ICarAttribute attrObj : attrList )
        {
            double dInfoGain = CalcInfoGain( dEntropy, bkt.m_instList, attrObj );
            if( dMaxInfoGain < dInfoGain )
            {
                dMaxInfoGain = dInfoGain;
                maxGainAttr  = attrObj;
            }
        }
        
        if( null == maxGainAttr )
        {
            // TODO: Need to re-look at this situation
            currentNode.SetAsLeaf();
            currentNode.CalculateMostCommonLabel();
            return;
        }
        
        // Assign the decision attribute to the node.
        currentNode.SetDecisionAttribute( CAR_ATTR_TYPE_e.values()[maxGainAttr.GetAttrType()]);
        // Make it the parent node, if it's the parent node is null.
        // On further recursion, the parent node would be non-null value.
        if( null == parentNode )
        {
            m_rootNode = currentNode;
        }
        // An attribute shall not be used more than once for the decision-making in a tree hierarchy.
        // So remove the decision attribute used at this node while going to the next child level.
        ArrayList<ICarAttribute> pendingAttrList = ( ArrayList< ICarAttribute > ) attrList.clone();
        pendingAttrList.remove( maxGainAttr );
        
        // For each value of the decision attribute, figure out the number of buckets and go deep to child level.
        int nBucketCount = maxGainAttr.GetNumberOfPossibleValues();
        ArrayList< ResultingBucket > childBuckets = new ArrayList< ResultingBucket >();
        for( int nPossValIndex = 0; nPossValIndex < nBucketCount; ++nPossValIndex )
        {
            childBuckets.add( new ResultingBucket());
        }
        // Distribute the instances to each bucket
        for( Instance inst : bkt.m_instList )
        {
            for( int nPossValIndex = 0; nPossValIndex < nBucketCount; ++nPossValIndex )
            {
                int nPossValue = maxGainAttr.GetPossibleValue( nPossValIndex );
                if( nPossValue == inst.GetAttrValue( CAR_ATTR_TYPE_e.values()[maxGainAttr.GetAttrType()]))
                {
                    childBuckets.get( nPossValIndex ).m_nDecisionAttrValue = nPossValue;
                    childBuckets.get( nPossValIndex ).m_instList.add( inst );
                }
            }
        }
        
        if( childBuckets.isEmpty())
        {
            // TODO: Need to re-look at this situation
            currentNode.SetAsLeaf();
            currentNode.CalculateMostCommonLabel();
            return;
        }
        
        // Continue the process to the next level.
        for( ResultingBucket childBucket : childBuckets )
        {
            FindDecisionAttributeAndGrow( currentNode, childBucket, pendingAttrList );
        }
    }
    
    
    class SvBucket
    {
        int m_nSv;
        int m_nLocalClassDistribution[] = { 0, 0, 0, 0 };
    }
    
    
    /**
     * This function calculates the information gain for a given attribute.
     * 
     * @param dParentEntropy
     * @param instanceList
     * @param attrInstance
     * @return
     */
    private double CalcInfoGain( double dParentEntropy, ArrayList< Instance > instanceList,
                                 ICarAttribute attrInstance )
    {
        double dInfoGain = 0.0;
        double dWeightedEntropyAtChildren = 0.0;
        
        // Allocate the SvBuckets based on the possible number of values of the decision attribute under consideration.
        SvBucket[] svBucketArray = new SvBucket[attrInstance.GetNumberOfPossibleValues()];
        for( int nAllocIndex = 0; nAllocIndex < attrInstance.GetNumberOfPossibleValues(); ++nAllocIndex )
        {
            svBucketArray[nAllocIndex] = new SvBucket();
        }
        
        // Calculate expected entropy at children
        // Iterate over the decision attribute values and find the Sv/S * Entropy(Sv)
        for( int nPossValueIndex = 0; nPossValueIndex < attrInstance.GetNumberOfPossibleValues(); ++nPossValueIndex )
        {
            int nPossibleVal = attrInstance.GetPossibleValue( nPossValueIndex );
            for( Instance inst : instanceList )
            {                
                if( nPossibleVal == inst.GetAttrValue( CAR_ATTR_TYPE_e.values()[attrInstance.GetAttrType()]))
                {
                    svBucketArray[nPossValueIndex].m_nSv++;
                    svBucketArray[nPossValueIndex].m_nLocalClassDistribution[inst.m_eRecordLabel.GetNumericValue()]++;
                }                
            }
        }
        
        int nCountInstAtParent = instanceList.size();
        for( int nSvIndex =0; nSvIndex < svBucketArray.length; ++nSvIndex )
        {
            dWeightedEntropyAtChildren += ( (double)( svBucketArray[nSvIndex].m_nSv ) / (double)( nCountInstAtParent )) * 
                                          Node.CalculateEntropy( svBucketArray[nSvIndex].m_nLocalClassDistribution );
        }
        // Calculate info-gain by subtracting the expected entropy at child level
        // from the parent level entropy. 
        dInfoGain = dParentEntropy - dWeightedEntropyAtChildren;
        return dInfoGain;
    }
    
    
    /**
     * 
     * @param strTrainingDataFile
     * @param trainingInstList
     */
    public static void LoadTrainingData( String strTrainingDataFile, ArrayList< Instance > trainingInstList )
    {
        try
        {
            BufferedReader bufRdr = new BufferedReader( new FileReader( strTrainingDataFile ));
            String strLine;
            while( ( strLine = bufRdr.readLine()) != null )
            {
                Instance instance = new Instance( strLine );
                trainingInstList.add( instance );
            }
            bufRdr.close();
        } 
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
	    // Load the training data.
		//String strTrainingDataFile = "C://Users//Rohith Ravindran//Documents//Personal//Mission MS//#Otto//Sem#1//ML//cardata//cardaten//car.data";
	    String strTrainingDataFile = "./TrainingDataFolder/car.data";
		ArrayList<Instance> instanceList = new ArrayList<Instance>();
		LoadTrainingData( strTrainingDataFile, instanceList );
		
		CarID3 id3Obj = new CarID3();
		id3Obj.LearnTree( instanceList );
	}
}
