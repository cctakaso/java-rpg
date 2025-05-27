package rpg.objects;
import java.util.ArrayList;

import rpg.types.StatusType;
import rpg.utils.Pt;

public class Status extends Base{
  //比率初期値
  static int INITIAL_RATE = 100;

  protected StatusType type;
  //能力値（１〜100）
  protected int point;
  //能力値の一時比率（100がデフォルト）
  protected int rate;   //一時比率

  public Status(StatusType type, int point) {
    this();
    this.type = type;
    this.point = point;
  }

  public Status() {
    super();
    this.rate = INITIAL_RATE;
  }

  public String toPrinting() {
    return this.type+":"+this.point+"("+this.rate+")";
  }

  public Status clone() {
    return this.clone(0, null);
  }

  public Status clone(int num, Pt randomPt) {
    Status copy = null;
    try {
      copy = (Status)super.clone(num, randomPt);
      copy.type = this.type;
      copy.point = this.point;
      copy.rate = this.rate;
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }
}
