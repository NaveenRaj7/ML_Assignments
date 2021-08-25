/**
 * 
 */
package id3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import id3.CarID3.ResultingBucket;
import id3.Instance.RECORD_CLASS_e;

/**
 * This class represents a tree node for ID3. It maintains the parent-child relationships with other nodes.
 * It holds the entropy, class-distribution, decision attribute  and the most common classification details 
 * with respect to the current node.
 *
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 */
public class Node
{
    private Node m_parentNode;
    private int m_nValueForParentLevelDecisionAttr;
    private ArrayList<Node> m_childNodeList;
    private Instance.RECORD_CLASS_e m_eMostCommonLabel;
    private AbstractCarAttrBase.CAR_ATTR_TYPE_e m_eDecisionAttribute;
    private double m_dLocalEntrpy = 0.0;
    private boolean m_bLeaf = false;
    // [ Class#1-Counter, Class#2-Counter, Class#3-Counter, Class#4-Counter ]
    private int [] m_nClassDistribution = new int[Instance.TARGET_CLASSIFICATION_COUNT];
    
    /**
     * 
     * @param parent
     * @param bkt
     */
    public Node( Node parent, ResultingBucket bkt )
    {
        this.m_parentNode = parent;
        if( null != parent )
        {
            parent.AddChild( this );
        }
        this.m_nValueForParentLevelDecisionAttr = bkt.m_nDecisionAttrValue;
        
        // Calculate bucket classification iterating all the instances.
        for( Instance instance : bkt.m_instList )
        {
            m_nClassDistribution[instance.m_eRecordLabel.GetNumericValue()]++;
        }
        CalculateMostCommonLabel();
    }
    
    
    /**
     * This function calculates the target-classification based on the class-distribution.
     * It's relevant when the node is leaf.
     */
    public void CalculateMostCommonLabel()
    {
        // Figure out the most common label.
        ArrayList<Integer> tempCounter = new ArrayList<>(Arrays.asList( m_nClassDistribution[0],
                                                                        m_nClassDistribution[1],
                                                                        m_nClassDistribution[2], 
                                                                        m_nClassDistribution[3]));
        int nHighestCounter = Math.max( m_nClassDistribution[0],
                                        Math.max( m_nClassDistribution[1],
                                                  Math.max( m_nClassDistribution[2], m_nClassDistribution[3] )));
        
        int nClassIndex = tempCounter.indexOf( new Integer( nHighestCounter ));

        if( nClassIndex == Instance.RECORD_CLASS_e.REC_CLASS_VALUE_UNACC.GetNumericValue())
        {
            this.m_eMostCommonLabel = Instance.RECORD_CLASS_e.REC_CLASS_VALUE_UNACC;
        }
        else if( nClassIndex == Instance.RECORD_CLASS_e.REC_CLASS_VALUE_ACC.GetNumericValue())
        {
            this.m_eMostCommonLabel = Instance.RECORD_CLASS_e.REC_CLASS_VALUE_ACC;
        }
        else if( nClassIndex == Instance.RECORD_CLASS_e.REC_CLASS_VALUE_GOOD.GetNumericValue())
        {
            this.m_eMostCommonLabel = Instance.RECORD_CLASS_e.REC_CLASS_VALUE_GOOD;
        }
        else if( nClassIndex == Instance.RECORD_CLASS_e.REC_CLASS_VALUE_VERY_GOOD.GetNumericValue())
        {
            this.m_eMostCommonLabel = Instance.RECORD_CLASS_e.REC_CLASS_VALUE_VERY_GOOD;
        }
        else
        {
            // Shouldn't happen.
        }
    }
    
    /**
     * 
     * @param childNode
     */
    private void AddChild( Node childNode )
    {
        if( null == this.m_childNodeList )
        {
            this.m_childNodeList = new ArrayList<Node>();
        }
        this.m_childNodeList.add( childNode );
    }
    
    
    /**
     * 
     * This function sets the node as a leaf.
     */
    public void SetAsLeaf()
    {
        this.m_bLeaf = true;
    }
    
    
    /**
     * This function calculates the Entropy at a particular node.
     * The number of classification is defined by TARGET_CLASSIFICATION_COUNT.
     * 
     * @param nClassificationCounter
     * @return
     */
    public double CalcEntropy()
    {
        this.m_dLocalEntrpy = CalculateEntropy( this.m_nClassDistribution );
        return this.m_dLocalEntrpy;
    }
    
    
    public static double CalculateEntropy( int [] nClassDistribution )
    {
        double dEntropy = 0.0;
        double dProbability = 0.0;
        
        int nInputInstCount = nClassDistribution[Instance.RECORD_CLASS_e.REC_CLASS_VALUE_UNACC.GetNumericValue()] +
                              nClassDistribution[Instance.RECORD_CLASS_e.REC_CLASS_VALUE_ACC.GetNumericValue()] +
                              nClassDistribution[Instance.RECORD_CLASS_e.REC_CLASS_VALUE_GOOD.GetNumericValue()] +
                              nClassDistribution[Instance.RECORD_CLASS_e.REC_CLASS_VALUE_VERY_GOOD.GetNumericValue()];
        // Calculate the probability of each classification and figure out the entropy.
        for( int nClassificationIndex = 0; nClassificationIndex < Instance.TARGET_CLASSIFICATION_COUNT; ++nClassificationIndex )
        {
            if( nClassDistribution[nClassificationIndex] <= 0 )
            {
                continue;
            }
            dProbability = (double)nClassDistribution[nClassificationIndex] / (double)nInputInstCount;
            // Logarithm rule: log-base-2(x) = log-base-10(x) / log-base-10(2)
            dEntropy += -( dProbability * ( Math.log(dProbability) / Math.log(2) ));
        }
        return dEntropy;
    }
    
    
    /**
     * This function sets the decision attribute to the node, based on which
     * the tree will grow further from this node.
     * 
     * @param eAttrType
     */
    public void SetDecisionAttribute( AbstractCarAttrBase.CAR_ATTR_TYPE_e eAttrType )
    {
        this.m_eDecisionAttribute = eAttrType;
    }
    
    
    /**
     * This function dumps the tree hierarchy from this node onwards to an XML file.
     * @param strXMLFileName
     */
    public void DumpToXML( String strXMLFileName )
    {
        PrintWriter xmlWriter = null;
        try
        {
            xmlWriter = new PrintWriter( new FileWriter( strXMLFileName ));
            xmlWriter.println( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" );
            
            // Write the information about the current node.
            xmlWriter.println( "<tree " );
            xmlWriter.print( this.toString());
            xmlWriter.print( ">" );
            WriteChildNodesToXML( xmlWriter );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        
        xmlWriter.println("</tree>");
        xmlWriter.close();
    }
    
    
    /**
     * This recursive function iterates over the child nodes and writes the details to XML
     * @param xmlWriter
     */
    private void WriteChildNodesToXML( PrintWriter xmlWriter )
    {
        // Iterate over the child nodes and do the same.
        for( Node childNode : m_childNodeList )
        {
            xmlWriter.println( "<node " );
            xmlWriter.print( childNode.toString());
            if( !childNode.m_bLeaf )
            {
                xmlWriter.print( ">" );
                childNode.WriteChildNodesToXML( xmlWriter );
                xmlWriter.println( "</node>" );
            }
            else
            {
                xmlWriter.print( ">" );
                switch( childNode.m_eMostCommonLabel )
                {
                case REC_CLASS_VALUE_UNACC:
                    xmlWriter.print( Instance.REC_CLASS_VALUE_UNACC );
                    break;
                case REC_CLASS_VALUE_ACC:
                    xmlWriter.print( Instance.REC_CLASS_VALUE_ACC );
                    break;
                case REC_CLASS_VALUE_GOOD:
                    xmlWriter.print( Instance.REC_CLASS_VALUE_GOOD );
                    break;
                case REC_CLASS_VALUE_VERY_GOOD:
                    xmlWriter.print( Instance.REC_CLASS_VALUE_VERY_GOOD );
                    break;
                }
                xmlWriter.print( "</node>" );
            }
        }
    }
    
    
    /**
     * This function returns the class-distributions, entropy and decision attribute details for a node.
     * For root node, it returns class-distributions & entropy.
     * For child node, it returns class-distributions, entropy and decision attribute along with it's value.
     */
    public String toString()
    {
        String strClasses = "classes=\"" +
                Instance.REC_CLASS_VALUE_UNACC     + ":" + this.m_nClassDistribution[RECORD_CLASS_e.REC_CLASS_VALUE_UNACC.GetNumericValue()]     + ", " +
                Instance.REC_CLASS_VALUE_ACC       + ":" + this.m_nClassDistribution[RECORD_CLASS_e.REC_CLASS_VALUE_ACC.GetNumericValue()]       + ", " + 
                Instance.REC_CLASS_VALUE_GOOD      + ":" + this.m_nClassDistribution[RECORD_CLASS_e.REC_CLASS_VALUE_GOOD.GetNumericValue()]      + ", " +
                Instance.REC_CLASS_VALUE_VERY_GOOD + ":" + this.m_nClassDistribution[RECORD_CLASS_e.REC_CLASS_VALUE_VERY_GOOD.GetNumericValue()] +
                "\"";
        String strEntropy = "entropy=\"" + this.m_dLocalEntrpy + "\"";
        
        // Root node: classes="unacc:123, acc:234, good:345, vgood:456" entropy="0.123"
        if( null == this.m_parentNode )
        {
            return strClasses + " " + strEntropy;
        }
        // Non-root node: classes="unacc:123, acc:234, good:345, vgood:456" entropy="0.234" attr1="value1"
        String strParentLevelDecAttr = null;
        switch( this.m_parentNode.m_eDecisionAttribute )
        {
        case RECORD_ATTR_INDEX_BUYING:
            strParentLevelDecAttr = AttrBuyingValue.STR_ATTR_BUYING + "=\"" +
                                    AttrBuyingValue.GetStringValue( this.m_nValueForParentLevelDecisionAttr ) +
                                    "\"";
            break;
        case RECORD_ATTR_INDEX_MAINT:
            strParentLevelDecAttr = AttrMaintValue.STR_ATTR_MAINT + "=\"" +
                                    AttrMaintValue.GetStringValue( this.m_nValueForParentLevelDecisionAttr ) +
                                    "\"";
            break;
        case RECORD_ATTR_INDEX_DOORS:
            strParentLevelDecAttr = AttrDoorsValue.STR_ATTR_DOORS + "=\"" +
                                    AttrDoorsValue.GetStringValue( this.m_nValueForParentLevelDecisionAttr ) +
                                    "\"";
            break;
        case RECORD_ATTR_INDEX_PERSONS:
            strParentLevelDecAttr = AttrPersonsValue.STR_ATTR_PERSONS + "=\"" +
                                    AttrPersonsValue.GetStringValue( this.m_nValueForParentLevelDecisionAttr )+
                                    "\"";
            break;
        case RECORD_ATTR_INDEX_LUG_BOOT:
            strParentLevelDecAttr = AttrLugBootValue.STR_ATTR_LUG_BOOT + "=\"" +
                                    AttrLugBootValue.GetStringValue( this.m_nValueForParentLevelDecisionAttr ) +
                                    "\"";
            break;
        case RECORD_ATTR_INDEX_SAFETY:
            strParentLevelDecAttr = AttrSafetyValue.STR_ATTR_SAFETY + "=\"" +
                                    AttrSafetyValue.GetStringValue( this.m_nValueForParentLevelDecisionAttr ) +
                                    "\"";
            break;
        }
        return strClasses + " " + strEntropy + " " + strParentLevelDecAttr;
    }
}
