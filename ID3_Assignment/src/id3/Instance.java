package id3;

import java.util.HashMap;

/**
 * This class represents an instance(record) in the data-set.
 * It translates each entity in the record to numerical figure for easier manipulation.
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 */
public class Instance
{   
    public final static int RECORD_CLASS_INDEX = 6;
    public final static int TARGET_CLASSIFICATION_COUNT = 4;
    
    // Target Classification
    public final static String REC_CLASS_VALUE_UNACC     = "unacc";
    public final static String REC_CLASS_VALUE_ACC       = "acc";
    public final static String REC_CLASS_VALUE_GOOD      = "good";
    public final static String REC_CLASS_VALUE_VERY_GOOD = "vgood";
    
    public enum RECORD_CLASS_e
    {
        REC_CLASS_VALUE_UNACC(0),
        REC_CLASS_VALUE_ACC(1),
        REC_CLASS_VALUE_GOOD(2),
        REC_CLASS_VALUE_VERY_GOOD(3);
        
        private final int m_nCode;
        private RECORD_CLASS_e( int nCode )
        {
            this.m_nCode = nCode;
        }
        public int GetNumericValue() { return m_nCode; }
    }
    
    
    private static int INSTANCE_ID_COUNTER = 0;
    int m_nInstanceID; // Holds the ID of the instance.
    RECORD_CLASS_e m_eRecordLabel; // Holds the classification bucket-label to which the instance fall in.
    HashMap< Integer, Integer > m_attrValueMap;
    
    public Instance( String strRecord )
    {
        final String ATTRIBUTE_DELIMITER = ",";
        this.m_nInstanceID = ++INSTANCE_ID_COUNTER;
        m_attrValueMap = new HashMap<Integer, Integer>();
        String[] strAttrValues = strRecord.split( ATTRIBUTE_DELIMITER );
        
        // Read the record label.
        switch( strAttrValues[RECORD_CLASS_INDEX] )
        {
        case REC_CLASS_VALUE_UNACC:
            m_eRecordLabel = RECORD_CLASS_e.REC_CLASS_VALUE_UNACC;
            break;
        case REC_CLASS_VALUE_ACC:
            m_eRecordLabel = RECORD_CLASS_e.REC_CLASS_VALUE_ACC;
            break;
        case REC_CLASS_VALUE_GOOD:
            m_eRecordLabel = RECORD_CLASS_e.REC_CLASS_VALUE_GOOD;
            break;
        case REC_CLASS_VALUE_VERY_GOOD:
            m_eRecordLabel = RECORD_CLASS_e.REC_CLASS_VALUE_VERY_GOOD;
            break;
        }
        
        // Read the buying attribute
        switch( strAttrValues[AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_BUYING.GetNumericValue()] )
        {
        case AttrBuyingValue.STR_ATTR_BUYING_VALUE_LOW:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_BUYING.GetNumericValue()),
                                new Integer(AttrBuyingValue.ATTR_BUYING_VALUE_LOW ));
            break;
        case AttrBuyingValue.STR_ATTR_BUYING_VALUE_MED:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_BUYING.GetNumericValue()),
                                new Integer(AttrBuyingValue.ATTR_BUYING_VALUE_MED ));
            break;
        case AttrBuyingValue.STR_ATTR_BUYING_VALUE_HIGH:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_BUYING.GetNumericValue()),
                                new Integer(AttrBuyingValue.ATTR_BUYING_VALUE_HIGH ));
            break;
        case AttrBuyingValue.STR_ATTR_BUYING_VALUE_VERY_HIGH:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_BUYING.GetNumericValue()),
                                new Integer(AttrBuyingValue.ATTR_BUYING_VALUE_VERY_HIGH ));
            break;
        }
        
        // Read the maintenance attribute
        switch( strAttrValues[AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_MAINT.GetNumericValue()] )
        {
        case AttrMaintValue.STR_ATTR_MAINT_VALUE_LOW:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_MAINT.GetNumericValue()),
                                new Integer(AttrMaintValue.ATTR_MAINT_VALUE_LOW ));
            break;
        case AttrMaintValue.STR_ATTR_MAINT_VALUE_MED:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_MAINT.GetNumericValue()),
                                new Integer(AttrMaintValue.ATTR_MAINT_VALUE_MED ));
            break;
        case AttrMaintValue.STR_ATTR_MAINT_VALUE_HIGH:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_MAINT.GetNumericValue()),
                                new Integer(AttrMaintValue.ATTR_MAINT_VALUE_HIGH ));
            break;
        case AttrMaintValue.STR_ATTR_MAINT_VALUE_VERY_HIGH:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_MAINT.GetNumericValue()),
                                new Integer(AttrMaintValue.ATTR_MAINT_VALUE_VERY_HIGH ));
            break;
        }
        
        // Read the doors attribute
        switch( strAttrValues[AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_DOORS.GetNumericValue()] )
        {
        case AttrDoorsValue.STR_ATTR_DOORS_VALUE_TWO:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_DOORS.GetNumericValue()),
                                new Integer(AttrDoorsValue.ATTR_DOORS_VALUE_TWO ));
            break;
        case AttrDoorsValue.STR_ATTR_DOORS_VALUE_THREE:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_DOORS.GetNumericValue()),
                                new Integer(AttrDoorsValue.ATTR_DOORS_VALUE_THREE ));
            break;
        case AttrDoorsValue.STR_ATTR_DOORS_VALUE_FOUR:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_DOORS.GetNumericValue()),
                                new Integer(AttrDoorsValue.ATTR_DOORS_VALUE_FOUR ));
            break;
        case AttrDoorsValue.STR_ATTR_DOORS_VALUE_FIVE_AND_MORE:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_DOORS.GetNumericValue()),
                                new Integer(AttrDoorsValue.ATTR_DOORS_VALUE_FIVE_AND_MORE ));
            break;
        }
        
        // Read the persons attribute
        switch( strAttrValues[AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_PERSONS.GetNumericValue()] )
        {
        case AttrPersonsValue.STR_ATTR_PERSONS_VALUE_TWO:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_PERSONS.GetNumericValue()),
                                new Integer(AttrPersonsValue.ATTR_PERSONS_VALUE_TWO ));
            break;
        case AttrPersonsValue.STR_ATTR_PERSONS_VALUE_FOUR:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_PERSONS.GetNumericValue()),
                                new Integer(AttrPersonsValue.ATTR_PERSONS_VALUE_FOUR ));
            break;
        case AttrPersonsValue.STR_ATTR_PERSONS_VALUE_MORE:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_PERSONS.GetNumericValue()),
                                new Integer(AttrPersonsValue.ATTR_PERSONS_VALUE_MORE ));
            break;
        }
        
        // Read the leg boot attribute
        switch( strAttrValues[AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_LUG_BOOT.GetNumericValue()] )
        {
        case AttrLugBootValue.STR_ATTR_LUG_BOOT_VALUE_SMALL:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_LUG_BOOT.GetNumericValue()),
                                new Integer(AttrLugBootValue.ATTR_LUG_BOOT_VALUE_SMALL ));
            break;
        case AttrLugBootValue.STR_ATTR_LUG_BOOT_VALUE_MED:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_LUG_BOOT.GetNumericValue()),
                                new Integer(AttrLugBootValue.ATTR_LUG_BOOT_VALUE_MED ));
            break;
        case AttrLugBootValue.STR_ATTR_LUG_BOOT_VALUE_BIG:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_LUG_BOOT.GetNumericValue()),
                                new Integer(AttrLugBootValue.ATTR_LUG_BOOT_VALUE_BIG ));
            break;
        }
        
        // Read the safety attribute
        switch( strAttrValues[AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_SAFETY.GetNumericValue()] )
        {
        case AttrSafetyValue.STR_ATTR_SAFETY_VALUE_LOW:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_SAFETY.GetNumericValue()),
                                new Integer(AttrSafetyValue.ATTR_SAFETY_VALUE_LOW ));
            break;
        case AttrSafetyValue.STR_ATTR_SAFETY_VALUE_MED:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_SAFETY.GetNumericValue()),
                                new Integer(AttrSafetyValue.ATTR_SAFETY_VALUE_MED ));
            break;
        case AttrSafetyValue.STR_ATTR_SAFETY_VALUE_HIGH:
            m_attrValueMap.put( new Integer(AbstractCarAttrBase.CAR_ATTR_TYPE_e.RECORD_ATTR_INDEX_SAFETY.GetNumericValue()),
                                new Integer(AttrSafetyValue.ATTR_SAFETY_VALUE_HIGH ));
            break;
        }
    }
    
    public int GetAttrValue( AbstractCarAttrBase.CAR_ATTR_TYPE_e eAttr )
    {
        return m_attrValueMap.get( eAttr.GetNumericValue()).intValue();
    }
}
