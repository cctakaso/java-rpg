package rpg.objects;
import java.util.ArrayList;

import rpg.types.AttackType;
import rpg.types.StatusType;
import rpg.utils.Pt;

public class Attack extends Base{
  protected AttackType type;
  protected Statuses statuses;

  public Attack() {
    super();
    this.statuses = new Statuses();
  }

  public AttackType getType() {
    return this.type;
  }
  public Statuses getStatuses() {
    return this.statuses;
  }

  public Attack clone() {
    return this.clone(0, null);
  }

  public Attack clone(int num, Pt randomPt) {
    Attack copy = null;
    try {
      copy = (Attack)super.clone(num, randomPt);
      copy.type = this.type;
      copy.statuses = (Statuses)this.statuses.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public boolean isAvailable(Character character) {
    return character.charStatus.getMagicPoint() >= usedMagicPoint();
  }

  public String toPrinting(boolean isDetail) {
    return super.toPrinting() + (isDetail ? ("["+ this.type + ":" + this.statuses.toPrinting() + "]"):"");
  }

  public int usedMagicPoint() {
    return getTypePoint(StatusType.MagicPoint);
  }

  public int getAtackedHealthPoint() {
    return getTypePoint(StatusType.HealthPoint);
  }


  public int calcHealthDamage(Character attacker, Character reciver) {
    int damage, atackHp, attackerPoint, reciverPoint;
    if (isHeal()) {
      atackHp = getAtackedHealthPoint();
      attackerPoint= attacker.charStatus.getRatePoint(StatusType.MagicAttack);
      reciverPoint = 1;
    }else if (isPhysical()) {
      atackHp = getAtackedHealthPoint();
      attackerPoint = attacker.charStatus.getRatePoint(StatusType.Attack);
      reciverPoint = reciver.charStatus.getRatePoint(StatusType.Defense);
    }else if (isMagicalOffence()) {
      atackHp = getAtackedHealthPoint();
      attackerPoint = attacker.charStatus.getRatePoint(StatusType.MagicAttack);
      reciverPoint = reciver.charStatus.getRatePoint(StatusType.MagicDefense);
    }else{
      atackHp = 0;
      attackerPoint = 1;
      reciverPoint = 1;
    }
    damage = atackHp *  attackerPoint / reciverPoint;
    return damage;
  }

  public boolean isOffence() {
    return isPhysical() || isMagicalOffence();
  }

  public boolean isMulti() {
    return this.type == AttackType.PartyAttack || this.type == AttackType.PartyHeal ||
      (AttackType.MagicMultiOffenceMin.id <= this.type.id &&
          this.type.id <=  AttackType.MagicMultiOffenceMax.id);
  }

  private boolean isPhysical() {
    return this.type == AttackType.Attack || this.type == AttackType.PartyAttack;
  }

  public boolean isHeal() {
    return this.type == AttackType.Heal || this.type == AttackType.PartyHeal;
  }

  public boolean isMagic() {
    return isMagicDefense() ||  isMagicalOffence();
  }

  private boolean isMagicDefense() {
    return AttackType.MagicDefenseMin.id <= this.type.id &&
          this.type.id <=  AttackType.MagicDefenseMin.id;
  }

  private boolean isMagicalOffence() {
    return AttackType.MagicOffenceMin.id <= this.type.id &&
          this.type.id <=  AttackType.MagicOffenceMin.id;
  }

  private int getTypePoint(StatusType type) {
    for(Status status: (ArrayList<Status>)this.statuses.getList()) {
      if (status.type == type) {
        return status.point;
      }
    }
    return 0;
  }

}
