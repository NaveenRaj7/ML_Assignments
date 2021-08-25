/**
 * 
 */
package id3;

/**
 * Concrete class for the attribute doors.
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 */
public class AttrDoorsValue extends AbstractCarAttrBase
{
    static final String STR_ATTR_DOORS = "doors";
    
    static final String STR_ATTR_DOORS_VALUE_TWO           = "2";
    static final String STR_ATTR_DOORS_VALUE_THREE         = "3";
    static final String STR_ATTR_DOORS_VALUE_FOUR          = "4";
    static final String STR_ATTR_DOORS_VALUE_FIVE_AND_MORE = "5more";
    
    static final int ATTR_DOORS_VALUE_TWO = 2;
    static final int ATTR_DOORS_VALUE_THREE = 3;
    static final int ATTR_DOORS_VALUE_FOUR = 4;
    static final int ATTR_DOORS_VALUE_FIVE_AND_MORE = 5;
    
    
    public AttrDoorsValue()
    {
        super( CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_DOORS, STR_ATTR_DOORS );
    }
    
    
    @Override
    public void FillValues()
    {
        m_nAttrValues = new int[4];
        m_nAttrValues[0] = ATTR_DOORS_VALUE_TWO;
        m_nAttrValues[1] = ATTR_DOORS_VALUE_THREE;
        m_nAttrValues[2] = ATTR_DOORS_VALUE_FOUR;
        m_nAttrValues[3] = ATTR_DOORS_VALUE_FIVE_AND_MORE;
    }
    
    public static String GetStringValue( int nVal )
    {
        String strVal = "";
        switch( nVal )
        {
        case ATTR_DOORS_VALUE_TWO:
            strVal = STR_ATTR_DOORS_VALUE_TWO;
            break;
        case ATTR_DOORS_VALUE_THREE:
            strVal = STR_ATTR_DOORS_VALUE_THREE;
            break;
        case ATTR_DOORS_VALUE_FOUR:
            strVal = STR_ATTR_DOORS_VALUE_FOUR;
            break;
        case ATTR_DOORS_VALUE_FIVE_AND_MORE:
            strVal = STR_ATTR_DOORS_VALUE_FIVE_AND_MORE;
            break;
        }
        return strVal;
    }
}
