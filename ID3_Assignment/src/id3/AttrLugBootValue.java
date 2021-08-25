/**
 * 
 */
package id3;

/**
 * Concrete class for the attribute lug_boot.
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 */
public class AttrLugBootValue extends AbstractCarAttrBase
{
    static final String STR_ATTR_LUG_BOOT = "lug_boot";
    
    static final String STR_ATTR_LUG_BOOT_VALUE_SMALL = "small";
    static final String STR_ATTR_LUG_BOOT_VALUE_MED   = "med";
    static final String STR_ATTR_LUG_BOOT_VALUE_BIG   = "big";
    
    static final int ATTR_LUG_BOOT_VALUE_SMALL = 0;
    static final int ATTR_LUG_BOOT_VALUE_MED = 1;
    static final int ATTR_LUG_BOOT_VALUE_BIG = 2;
    
    
    public AttrLugBootValue()
    {
        super( CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_LUG_BOOT, STR_ATTR_LUG_BOOT );
    }
    
    
    @Override
    public void FillValues()
    {
        m_nAttrValues = new int[3];
        m_nAttrValues[0] = ATTR_LUG_BOOT_VALUE_SMALL;
        m_nAttrValues[1] = ATTR_LUG_BOOT_VALUE_MED;
        m_nAttrValues[2] = ATTR_LUG_BOOT_VALUE_BIG;
    }
    
    public static String GetStringValue( int nVal )
    {
        String strVal = "";
        switch( nVal )
        {
        case ATTR_LUG_BOOT_VALUE_SMALL:
            strVal = STR_ATTR_LUG_BOOT_VALUE_SMALL;
            break;
        case ATTR_LUG_BOOT_VALUE_MED:
            strVal = STR_ATTR_LUG_BOOT_VALUE_MED;
            break;
        case ATTR_LUG_BOOT_VALUE_BIG:
            strVal = STR_ATTR_LUG_BOOT_VALUE_BIG;
            break;
        }
        return strVal;
    }
}
