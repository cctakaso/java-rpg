package rpg.utils;

import rpg.objects.Attack;
import rpg.objects.Statuses;
import rpg.utils.*;
import rpg.types.*;

public class Pt extends Xy {
  public Pt() {
    super();
  }
  public Pt(String str) {
    super(str);
  }
  public Pt(int x, int y) {
    super(x, y);
  }
  public Pt clone() {
    Pt copy = null;
    try {
      copy = (Pt)super.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public Pt moveKey(Defs.Key key) {
    switch (key) {
      case Defs.Key.West:
        this.x -=10;
        break;
      case Defs.Key.North:
        this.y -=10;
        break;
      case Defs.Key.East:
        this.x +=10;
        break;
      case Defs.Key.South:
        this.y +=10;
        break;
      default:
        break;
    }
    return this;
  }

  public Pt getAbsolutePt(Pt orginPt) {
    return new Pt(orginPt.x + this.x, orginPt.y + this.y);
  }

  public Pt getLocalPt(Pt orginPt) {
    return new Pt(this.x - orginPt.x, this.y - orginPt.y);
  }

  public boolean isEquals(Pt pt) {
    return pt.x == this.x && pt.y == this.y;
  }

}
