/**
 * 
 */
package id3;

/**
 * Concrete class for the attribute persons.
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 */
public class AttrPersonsValue extends AbstractCarAttrBase
{
    static final String STR_ATTR_PERSONS = "persons";
    
    static final String STR_ATTR_PERSONS_VALUE_TWO  = "2";
    static final String STR_ATTR_PERSONS_VALUE_FOUR = "4";
    static final String STR_ATTR_PERSONS_VALUE_MORE = "more";
    
    static final int ATTR_PERSONS_VALUE_TWO = 2;
    static final int ATTR_PERSONS_VALUE_FOUR = 4;
    static final int ATTR_PERSONS_VALUE_MORE = 5;
    
    
    public AttrPersonsValue()
    {
        super( CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_PERSONS, STR_ATTR_PERSONS );
    }
    
    
    @Override
    public void FillValues()
    {
        m_nAttrValues = new int[3];
        m_nAttrValues[0] = ATTR_PERSONS_VALUE_TWO;
        m_nAttrValues[1] = ATTR_PERSONS_VALUE_FOUR;
        m_nAttrValues[2] = ATTR_PERSONS_VALUE_MORE;
    }
    
    
    public static String GetStringValue( int nVal )
    {
        String strVal = "";
        switch( nVal )
        {
        case ATTR_PERSONS_VALUE_TWO:
            strVal = STR_ATTR_PERSONS_VALUE_TWO;
            break;
        case ATTR_PERSONS_VALUE_FOUR:
            strVal = STR_ATTR_PERSONS_VALUE_FOUR;
            break;
        case ATTR_PERSONS_VALUE_MORE:
            strVal = STR_ATTR_PERSONS_VALUE_MORE;
            break;
        }
        return strVal;
    }
}
