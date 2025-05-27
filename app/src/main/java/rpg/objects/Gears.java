package rpg.objects;
import java.util.*;

import rpg.types.GearType;

public class Gears extends Lists{
  public ArrayList<Gear> children;

  public Gears() {
    super();
    children = new ArrayList<Gear>();
  }

  protected Lists getNewInstance() {
    return new Gears();
  }
  public List<?> getList() {
    return children;
  }

  protected void setList(List<?> children) {
    this.children = (ArrayList<Gear>)children;
  }

  public Gear getGear(GearType gearType) {
    for (Gear gear: children) {
      if (gear.getGearType() == gearType) {
        return gear;
      }
    }
    return null;
  }

}
