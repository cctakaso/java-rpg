package rpg.objects;
import java.util.ArrayList;
import java.util.Scanner;

import rpg.types.*;
import rpg.utils.*;

public class Character extends Base{
  protected CharacterType type;
  protected Talks talks;
  protected CharStatus charStatus;
  protected Gears gears;
  protected Items items;
  protected Attacks attacks;

  public Character() {
    super();
    this.talks = new Talks();
    this.charStatus = new CharStatus();
    this.gears = new Gears();
    this.items = new Items();
    this.attacks = new Attacks();
  }

  public String getName() {
    return super.getName();
  }

  public String toPrinting(boolean isDetail) {
    return super.toPrinting()+"["+this.type+"]"
    + (isDetail ? " "+this.charStatus.toPrinting()+"\n"+this.attacks.toPrinting():"");
  }

  public String toWithGearPrinting(GearType gearType) {
    for(Gear gear: (ArrayList<Gear>)this.gears.getList()) {
      if (gear.getGearType() == gearType) {
        return super.toPrinting()+"["+this.type+ " " + gear.toPrinting()+ "]";
      }
    }
    return toPrinting(false);
  }

  public Answer<Attack> selectAttack(Scanner scan) {
    Answers<Attack> ansers = attacks.toAnswers(this);
    return ansers.printChoice(scan, getName(), true);
  }

  public Answer<Integer> haveAttack(Attack attack, Character attacker) {
    int damage = attack.calcHealthDamage(attacker, this);
    if (attack.isHeal()) {
      int reciverHp = this.charStatus.incHealthPoint(damage);
      attacker.charStatus.decMagicPoint(attack.usedMagicPoint());
      return new Answer<Integer>(attacker.getName()+"が"+this.getName()+"に、ヒール:"+damage+"によりHP:"+reciverHp+"になりました。", Integer.valueOf(reciverHp));
    }else{
      int reciverHp = this.charStatus.decHealthPoint(damage);
      attacker.charStatus.decMagicPoint(attack.usedMagicPoint());
      return new Answer<Integer>(attacker.getName()+"が"+this.getName()+"に、攻撃:"+attack.getName()+"で"+damage+"ダメージ与えました\n"+this.getName()+"は、現在HP:"+reciverHp+"です。", Integer.valueOf(reciverHp));
    }
  }

  public Character clone() {
    return this.clone(0, null);
  }

  public Character clone(int num, Pt randomPt) {
    Character copy = null;
    try {
      copy = (Character)super.clone(num, randomPt);
      copy.type = this.type;
      copy.talks = (Talks)this.talks.clone();
      copy.charStatus = (CharStatus)this.charStatus.clone();
      copy.gears = (Gears)this.gears.clone();
      copy.items = (Items)this.items.clone();
      copy.attacks = (Attacks)this.attacks.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public void copy(Character character) {
    super.copy(character);
    this.type = character.type;
    this.talks = character.talks;
    this.charStatus = character.charStatus;
    this.gears = character.gears;
    this.items = character.items;
    this.attacks = character.attacks;
  }

  public boolean meet(Scanner scan, Party myParty) {
    return false;
  }

  public void setReset() {
    this.charStatus.setReset();
  }

  public int getTotalExpericence() {
    return this.charStatus.getLevel().expTotal;
  }

  public int addExperience(int ex) {
    return this.charStatus.getLevel().addExperience(ex);
  }
}

