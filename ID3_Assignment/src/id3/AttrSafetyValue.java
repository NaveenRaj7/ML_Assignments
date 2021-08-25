/**
 * 
 */
package id3;

/**
 * Concrete class for the attribute safety.
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 */
public class AttrSafetyValue extends AbstractCarAttrBase
{
    static final String STR_ATTR_SAFETY = "safety";
    
    static final String STR_ATTR_SAFETY_VALUE_LOW  = "low";
    static final String STR_ATTR_SAFETY_VALUE_MED  = "med";
    static final String STR_ATTR_SAFETY_VALUE_HIGH = "high";
    
    static final int ATTR_SAFETY_VALUE_LOW = 0;
    static final int ATTR_SAFETY_VALUE_MED = 1;
    static final int ATTR_SAFETY_VALUE_HIGH = 2;
    
    public AttrSafetyValue()
    {
        super( CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_SAFETY, STR_ATTR_SAFETY );
    }
    
    
    @Override
    public void FillValues()
    {
        m_nAttrValues = new int[3];
        m_nAttrValues[0] = ATTR_SAFETY_VALUE_LOW;
        m_nAttrValues[1] = ATTR_SAFETY_VALUE_MED;
        m_nAttrValues[2] = ATTR_SAFETY_VALUE_HIGH;            
    }
    
    
    public static String GetStringValue( int nVal )
    {
        String strVal = "";
        switch( nVal )
        {
        case ATTR_SAFETY_VALUE_LOW:
            strVal = STR_ATTR_SAFETY_VALUE_LOW;
            break;
        case ATTR_SAFETY_VALUE_MED:
            strVal = STR_ATTR_SAFETY_VALUE_MED;
            break;
        case ATTR_SAFETY_VALUE_HIGH:
            strVal = STR_ATTR_SAFETY_VALUE_HIGH;
            break;
        }
        return strVal;
    }
}
