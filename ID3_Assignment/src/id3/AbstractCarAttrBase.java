/**
 * 
 */
package id3;

/**
 * This is the abstract base class for the ICarAttribute interface.
 * If defines the attribute types and provides an abstract implementation according to it.
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 */
public abstract class AbstractCarAttrBase implements ICarAttribute
{
    public enum CAR_ATTR_TYPE_e
    {
        RECORD_ATTR_INDEX_BUYING(0),
        RECORD_ATTR_INDEX_MAINT(1),
        RECORD_ATTR_INDEX_DOORS(2),
        RECORD_ATTR_INDEX_PERSONS(3),
        RECORD_ATTR_INDEX_LUG_BOOT(4),
        RECORD_ATTR_INDEX_SAFETY(5);
        
        private final int m_nCode;
        private CAR_ATTR_TYPE_e( int nCode )
        {
            this.m_nCode = nCode;
        }
        public int GetNumericValue() { return m_nCode; }
    }
    
    private CAR_ATTR_TYPE_e m_eAttrType;
    private String m_strAttrType; // Holds the string of attribute type.
    protected int[] m_nAttrValues;
    
    
    /**
     * The constructor enforces the concrete class to call it with the attribute type. 
     * 
     * @param eAttrType
     * @param strAttrType
     */
    public AbstractCarAttrBase( CAR_ATTR_TYPE_e eAttrType, String strAttrType )
    {
        this.m_eAttrType = eAttrType;
        this.m_strAttrType = strAttrType;
        FillValues(); // This shall not be done here if the value list is huge.
    }
    
    
    // The concrete class for an attribute shall fill
    // all the possible values for the particular attribute.
    protected abstract void FillValues();
    
    
    /**
     * This function returns the possible value for the attribute. The concrete class shall 
     * fill the possible values for the attribute by overriding FillValues function so that 
     * the end user can iterate over the possible values by calling this method.
     */
    public int GetPossibleValue( int nIndex )
    {            
        return this.m_nAttrValues[nIndex];
    }
    
    
    /**
     * This function returns number of possible values for the attribute.
     */
    public int GetNumberOfPossibleValues()
    {
        return this.m_nAttrValues.length;
    }
    
    
    /**
     * This function returns the attribute type as integer value. 
     */
    public int GetAttrType()
    {
        return this.m_eAttrType.GetNumericValue();
    }
}
