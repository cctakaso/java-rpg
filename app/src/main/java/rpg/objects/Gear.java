package rpg.objects;
import java.util.*;
import rpg.types.CharacterType;
import rpg.types.GearType;
import rpg.utils.Pt;

public class Gear extends Item {

  protected GearType gearType;
  protected ArrayList<CharacterType> characterTypes;  //装備可能職業

  public Gear() {
    this(500);
  }
  public Gear(int weight) {
    super(weight);
    characterTypes = new ArrayList<CharacterType>();
  }

  public GearType getGearType() {
    return this.gearType;
  }

  public Gear clone() {
    return this.clone(0, null);
  }

  public Gear clone(int num, Pt randomPt) {
    Gear copy = null;
    try {
      copy = (Gear)super.clone(num, randomPt);
      copy.gearType = this.gearType;
      copy.characterTypes = (ArrayList<CharacterType>)characterTypes.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public boolean isFiting(Character character) {
    return this==null || this.characterTypes.contains(character.type);
  }
}
