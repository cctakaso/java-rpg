package rpg.objects;

import java.util.ArrayList;
import java.util.Random;

import rpg.types.StatusType;
import rpg.utils.Pt;

public class Level {
  public int level;
  public int experience;
  public int expTotal;
  public int expForNextLevel;
  public float levelFactor;
  public static int FIRST_LEVEL = 1;
  public static int NEED_NUM_DEFEAT = 5;
  public static int AVE_HP = 10;

  public Level() {
    this.level = 1;
    experience = 0;
    buildLevelFactor();
  }

  public Level clone() {
    Level copy = null;
    try {
      copy = new Level();
      copy.level = this.level;
      copy.experience = this.experience;
      copy.expTotal = this.expTotal;
      copy.expForNextLevel = this.expForNextLevel;
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public String toPrinting(ArrayList<Integer> statues, ArrayList<Integer> rates) {
    buildLevelFactor();
    return "LEVEL:"+this.level+" EX:"+this.experience+"/"+this.expForNextLevel + "\n" +
            "HP:"+getRatePoint(StatusType.HealthPoint, statues, rates)+"/"+getFullPoint(StatusType.HealthPoint, statues)+
            " MP:"+getRatePoint(StatusType.MagicPoint, statues, rates)+"/"+getFullPoint(StatusType.MagicPoint, statues)+
            " AK:"+getRatePoint(StatusType.Attack, statues, rates)+
            " DF:"+getRatePoint(StatusType.Defense, statues, rates)+
            " MA:"+getRatePoint(StatusType.MagicAttack, statues, rates)+
            " MD:"+getRatePoint(StatusType.MagicDefense, statues, rates)+
            " AG:"+getRatePoint(StatusType.Agility, statues, rates)+
            " DX:"+getRatePoint(StatusType.Dexterity, statues, rates)+
            " EV:"+getRatePoint(StatusType.Evansion, statues, rates)+
            " CR:"+getRatePoint(StatusType.Critical, statues, rates);
  }

  public int fullHeal(ArrayList<Integer> statues, ArrayList<Integer> rates) {
    rates.set(StatusType.HealthPoint.id, 100);
    return statues.get(StatusType.HealthPoint.id);
  }

  public int getHealthPoint(ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return getRatePoint(StatusType.HealthPoint, statues, rates);
  }

  public int incHealthPoint(int delta, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return incPoint(delta, StatusType.HealthPoint, statues, rates);
  }
  public int incHealthRate(int delta, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return incRate(delta, StatusType.HealthPoint, statues, rates);
  }

  public int decHealthPoint(int delta, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return decPoint(delta, StatusType.HealthPoint, statues, rates);
  }

  public int getMagicPoint(ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return getRatePoint(StatusType.MagicPoint, statues, rates);
  }

  public int incMagicPoint(int delta, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return incPoint(delta, StatusType.MagicPoint, statues, rates);
  }

  public int incMagicRate(int delta, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return incRate(delta, StatusType.MagicPoint, statues, rates);
  }

  public int decMagicPoint(int delta, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return decPoint(delta, StatusType.MagicPoint, statues, rates);
  }

  public int decMagicRate(int delta, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return decRate(delta, StatusType.MagicPoint, statues, rates);
  }

  private int incPoint(int delta, StatusType type, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    int point = getRatePoint(type, statues, rates);
    point += delta;
    int rate = point*100 / getFullPoint(type, statues);
    if (rate>=100) {
      rates.set(type.id, 100);
      return 100;
    }else{
      rates.set(type.id, rate);
    }
    return getRatePoint(type, statues, rates);
  }

  private int decPoint(int delta, StatusType type, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    int point = getRatePoint(type, statues, rates);
    point -= delta;
    if (point<=0) {
      rates.set(type.id, 0);
      return 0;
    }else{
      rates.set(type.id, (int)point*100/getFullPoint(type, statues));
    }
    return getRatePoint(type, statues, rates);
  }

  private int incRate(int delta, StatusType type, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    int rate = rates.get(type.id);
    rate += delta;
    if (rate>=100) {
      rates.set(type.id, 100);
      return 100;
    }else{
      rates.set(type.id, (int)rate);
    }
    return getRatePoint(type, statues, rates);
  }


  private int decRate(int delta, StatusType type, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    int rate = rates.get(type.id);
    rate -= delta;
    if (rate<=0) {
      rates.set(type.id, 0);
      return 0;
    }else{
      rates.set(type.id, (int)rate);
    }
    return getRatePoint(type, statues, rates);
  }

  public int addExperience(int delta) {
    buildLevelFactor();
    this.experience += delta;
    while (this.experience >= this.expForNextLevel) {
      this.level ++;
      this.experience -= this.expForNextLevel;
      this.expTotal = this.expForNextLevel;
      this.expForNextLevel = this.expForNextLevel * 3 / 2;
    }
    buildLevelFactor();
    return this.level;
  }

  private int getFullPoint(StatusType type, ArrayList<Integer> statues) {
    return calcLevelFactor(statues.get(type.id));
  }

  public int getRatePoint(StatusType type, ArrayList<Integer> statues, ArrayList<Integer> rates) {
    return calcLevelFactor(statues.get(type.id), rates.get(type.id));
  }

  private int calcLevelFactor(int point) {
    return calcLevelFactor(point, 100);
  }

  private int calcLevelFactor(int point, int rate) {
    return (int)(point * rate * this.levelFactor /100);
  }

  private void buildLevelFactor() {
    this.levelFactor = getLevelFactor(this.level);
    this.expForNextLevel = (int)(NEED_NUM_DEFEAT * AVE_HP * this.levelFactor);
    this.expTotal = this.expForNextLevel * 2 / 3;
  }

  private float getLevelFactor(int level) {
    float f = 1;
    for (int i= 1; i<level; i++) {
      f *= 1.5;
    }
    return f;
  }



}
