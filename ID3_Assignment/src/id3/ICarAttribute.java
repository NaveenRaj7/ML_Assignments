/**
 * 
 */
package id3;

/**
 * Interface to all the attributes of car. Each attribute shall be a concrete class.
 * 
 * @author Rohith Ravindran, Naveen Datha, Saagar Gaikwad, Sumit Kundu
 * 
 */
public interface ICarAttribute
{
    // Each possible value shall be treated as integer value, and the user of ICarAttrbute
    // should be able iterate over those values.
    int GetPossibleValue( int nIndex );
    // Returns the count of possible values for an attribute.
    int GetNumberOfPossibleValues();
    // Returns the attribute type.
    int GetAttrType();
}