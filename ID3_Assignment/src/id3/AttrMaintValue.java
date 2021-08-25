/**
 * 
 */
package id3;

/**
 * Concrete class for the attribute maint.
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 */
public class AttrMaintValue extends AbstractCarAttrBase
{
    static final String STR_ATTR_MAINT = "maint";
    
    static final String STR_ATTR_MAINT_VALUE_LOW       = "low";
    static final String STR_ATTR_MAINT_VALUE_MED       = "med";
    static final String STR_ATTR_MAINT_VALUE_HIGH      = "high";
    static final String STR_ATTR_MAINT_VALUE_VERY_HIGH = "vhigh";
    
    static final int ATTR_MAINT_VALUE_LOW = 0;
    static final int ATTR_MAINT_VALUE_MED = 1;
    static final int ATTR_MAINT_VALUE_HIGH = 2;
    static final int ATTR_MAINT_VALUE_VERY_HIGH = 3;
    
    
    public AttrMaintValue()
    {
        super( CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_MAINT, STR_ATTR_MAINT );
    }
    
    
    @Override
    public void FillValues()
    {
        m_nAttrValues = new int[4];
        m_nAttrValues[0] = ATTR_MAINT_VALUE_LOW;
        m_nAttrValues[1] = ATTR_MAINT_VALUE_MED;
        m_nAttrValues[2] = ATTR_MAINT_VALUE_HIGH;
        m_nAttrValues[3] = ATTR_MAINT_VALUE_VERY_HIGH;
    }
    
    public static String GetStringValue( int nVal )
    {
        String strVal = "";
        switch( nVal )
        {
        case ATTR_MAINT_VALUE_LOW:
            strVal = STR_ATTR_MAINT_VALUE_LOW;
            break;
        case ATTR_MAINT_VALUE_MED:
            strVal = STR_ATTR_MAINT_VALUE_MED;
            break;
        case ATTR_MAINT_VALUE_HIGH:
            strVal = STR_ATTR_MAINT_VALUE_HIGH;
            break;
        case ATTR_MAINT_VALUE_VERY_HIGH:
            strVal = STR_ATTR_MAINT_VALUE_VERY_HIGH;
            break;
        }
        return strVal;
    }
}
