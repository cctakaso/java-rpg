package rpg.objects;

import rpg.types.AbilityType;
import rpg.types.StatusType;
import rpg.utils.Pt;

import java.util.*;
public class CharStatus extends Base{
  protected ArrayList<Integer> abilities;
  protected ArrayList<Integer> abilitiesRate;
  protected ArrayList<Integer> statues;
  protected ArrayList<Integer> statuesRate;
  protected Level level;

  public CharStatus() {
    super();
    this.abilities = new ArrayList<Integer>();
    this.abilitiesRate = new ArrayList<Integer>(Arrays.asList(10,10,10,10,10,10,10,10,10,10,10));
    this.statues = new ArrayList<Integer>();
    this.level = new Level();
    setReset();
  }

  public ArrayList<Integer> getStatues() {
    return this.statues;
  }
  public ArrayList<Integer> getStatuesRate() {
    return this.statuesRate;
  }
  public Level getLevel() {
    return this.level;
  }

  public void doGard(boolean isGard) {
    this.abilitiesRate.set(StatusType.Defense.id, isGard ? 20:10);
    this.abilitiesRate.set(StatusType.MagicDefense.id, isGard ? 20:10);
  }

  public void setReset() {
    this.statuesRate = new ArrayList<Integer>(Arrays.asList(100,100,100,100,100,100,100,100,  10,10,10,10,10,10,10,10,10));
  }

  public String toPartyPrinting() {
    String str = toPrintingEx();
    if (str.length()>0) {
      str = "\n"+ str;
    }
    return this.level.toPrinting(this.statues, this.statuesRate)+str;
  }

  public String toPrinting() {
    String str = toPrintingEx();
    if (str.length()>0) {
      str = "\n"+ str;
    }
    return this.level.toPrinting(this.statues, this.statuesRate)+str;
  }

  public String toPrintingEx() {
    String str = "";
    for(int id = StatusType.Poisoned.id; id<=StatusType.Silenced.id; id++) {
      int value = this.statues.get(id);
      if (value>0) {
        StatusType st = StatusType.valueOf(id);
        str += st+":"+value+" ";
      }
    }
    return str;
  }

  public void merge(CharStatus charStatus) {
    for(int i=0; i<statues.size(); i++) {
      this.statues.set(i, charStatus.statues.get(i));
    }
  }

  public CharStatus clone() {
    return this.clone(0, null);
  }

  public CharStatus clone(int num, Pt randomPt) {
    CharStatus copy = null;
    try {
      copy = (CharStatus)super.clone(num, randomPt);
      copy.abilities = (ArrayList<Integer>)abilities.clone();
      copy.abilitiesRate = (ArrayList<Integer>)abilitiesRate.clone();
      copy.statues = (ArrayList<Integer>)statues.clone();
      copy.statuesRate = (ArrayList<Integer>)statuesRate.clone();
      copy.level = level.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public int getMoney() {
    return getPoint(StatusType.Money);
  }

  public int addMoney(int money) {
    Integer now = getPoint(StatusType.Money);
    this.statues.set(StatusType.Money.id, now+money);
    return now+money;
  }

  public int decMoney(int money) {
    return addMoney(money*-1);
  }

  private int getPoint(StatusType type) {
    return this.statues.get(type.id);
  }

  public int getMagicPoint() {
    return this.level.getMagicPoint(statues, statuesRate);
  }

  public int getRatePoint(StatusType type) {
    return this.level.getRatePoint(type, statues, statuesRate);
  }

  public int incHealthPoint(int delta) {
    return this.level.incHealthPoint(delta, statues, statuesRate);
  }

  public int incMagicPoint(int delta) {
    return this.level.incMagicPoint(delta, statues, statuesRate);
  }

  public int decHealthPoint(int delta) {
    return this.level.decHealthPoint(delta, statues, statuesRate);
  }

  public int decMagicPoint(int delta) {
    return this.level.decMagicPoint(delta, statues, statuesRate);
  }

}
