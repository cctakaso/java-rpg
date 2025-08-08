package rpg.objects;
import java.util.ArrayList;

import rpg.types.AttackType;
import rpg.types.StatusType;
import rpg.utils.Pt;

/**
 * 攻撃を表すクラス。
 * <p>
 * 攻撃の種類（AttackType）とステータス（Statuses）を持ち、
 * 攻撃の詳細やダメージ計算などの機能を提供します。
 * </p>
 */
public class Attack extends Base{
  protected AttackType type;  // 攻撃の種類
  protected Statuses statuses;  // 攻撃に関連するステータス

  /**
   * デフォルトコンストラクタ。
   * <p>
   * 攻撃の種類を初期化し、ステータスを空のStatusesオブジェクトで初期化します。
   * </p>
   */
  public Attack() {
    super();
    this.statuses = new Statuses(); // ステータスを空のStatusesオブジェクトで初期化
  }

  /**
   * 攻撃の種類とステータスを指定して初期化します。
   * @param type 攻撃の種類
   * @param statuses 攻撃に関連するステータス
   */
  public AttackType getType() {
    return this.type;
  }


  /**
   * 攻撃に関連するステータスを取得します。
   * @return 攻撃に関連するステータス
   */
  public Statuses getStatuses() {
    return this.statuses;
  }

  /**
   * 攻撃のクローンを作成します。
   * <p>
   * 攻撃の種類とステータスを保持した新しいAttackオブジェクトを返します。
   * </p>
   * @return 新しいAttackオブジェクト
   */
  public Attack clone() {
    return this.clone(0, null);
  }

  /**
   * 攻撃のクローンを作成します。
   * <p>
   * 指定された数値とランダムポイントを持つ新しいAttackオブジェクトを返します。
   * </p>
   * @param num クローンの識別番号
   * @param randomPt ランダムポイント
   * @return 新しいAttackオブジェクト
   */
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

  /**
   * 攻撃の詳細を文字列として返します。
   * <p>
   * 攻撃の種類とステータスを含む文字列を返します。
   * </p>
   * @return 攻撃の詳細を表す文字列
   */
  public boolean isAvailable(Character character) {
    return character.charStatus.getMagicPoint() >= usedMagicPoint();
  }

  /**
   * 攻撃の詳細を文字列として返します。
   * <p>
   * 攻撃の種類とステータスを含む文字列を返します。
   * </p>
   * @param isDetail 詳細表示フラグ
   * @return 攻撃の詳細を表す文字列
   */
  public String toString(boolean isDetail) {
    return super.toString() + (isDetail ? ("["+ this.type + ":" + this.statuses.toString() + "]"):"");
  }

  /**
   * 使用する魔法ポイントを取得します。
   * <p>
   * 攻撃の種類に応じて、必要な魔法ポイントを返します。
   * </p>
   * @return 使用する魔法ポイント
   */
  public int usedMagicPoint() {
    return getTypePoint(StatusType.MagicPoint);
  }

  /**
   * 攻撃対象のキャラクターのHPを取得します。
   * <p>
   * 攻撃の種類に応じて、攻撃対象のキャラクターのHPを返します。
   * </p>
   * @return 攻撃対象のキャラクターのHP
   */
  public int getAtackedHealthPoint() {
    return getTypePoint(StatusType.HealthPoint);
  }

  /**
   * 攻撃のダメージを計算します。
   * <p>
   * 攻撃の種類に応じて、攻撃者と受け手のステータスを考慮し、ダメージを計算します。
   * </p>
   * @param attacker 攻撃者のキャラクター
   * @param reciver 受け手のキャラクター
   * @return 計算されたダメージ
   */
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

  /**
   * 攻撃の種類が攻撃的かどうかを判定します。
   * <p>
   * 攻撃の種類が物理攻撃または魔法攻撃の場合、攻撃的とみなします。
   * </p>
   * @return 攻撃的な攻撃かどうか
   */
  public boolean isOffence() {
    return isPhysical() || isMagicalOffence();
  }

  /**
   * 攻撃の種類が防御的かどうかを判定します。
   * <p>
   * 攻撃の種類が防御または魔法防御の場合、防御的とみなします。
   * </p>
   * @return 防御的な攻撃かどうか
   */
  public boolean isMulti() {
    return this.type == AttackType.PartyAttack || this.type == AttackType.PartyHeal ||
      (AttackType.MagicMultiOffenceMin.id <= this.type.id &&
          this.type.id <=  AttackType.MagicMultiOffenceMax.id);
  }

  /**
   * 攻撃の種類が物理攻撃かどうかを判定します。
   * <p>
   * 攻撃の種類が物理攻撃またはパーティー攻撃の場合、物理攻撃とみなします。
   * </p>
   * @return 物理攻撃かどうか
   */
  private boolean isPhysical() {
    return this.type == AttackType.Attack || this.type == AttackType.PartyAttack;
  }

  /**
   * 攻撃の種類が魔法攻撃かどうかを判定します。
   * <p>
   * 攻撃の種類が魔法攻撃またはパーティー魔法攻撃の場合、魔法攻撃とみなします。
   * </p>
   * @return 魔法攻撃かどうか
   */
  public boolean isHeal() {
    return this.type == AttackType.Heal || this.type == AttackType.PartyHeal;
  }

  /**
   * 攻撃の種類が魔法防御かどうかを判定します。
   * <p>
   * 攻撃の種類が魔法防御の場合、魔法防御とみなします。
   * </p>
   * @return 魔法防御かどうか
   */
  public boolean isMagic() {
    return isMagicDefense() ||  isMagicalOffence();
  }

  /**
   * 攻撃の種類が魔法防御かどうかを判定します。
   * <p>
   * 攻撃の種類が魔法防御の場合、魔法防御とみなします。
   * </p>
   * @return 魔法防御かどうか
   */
  private boolean isMagicDefense() {
    return AttackType.MagicDefenseMin.id <= this.type.id &&
          this.type.id <=  AttackType.MagicDefenseMin.id;
  }

  /**
   * 攻撃の種類が魔法攻撃かどうかを判定します。
   * <p>
   * 攻撃の種類が魔法攻撃の場合、魔法攻撃とみなします。
   * </p>
   * @return 魔法攻撃かどうか
   */
  private boolean isMagicalOffence() {
    return AttackType.MagicOffenceMin.id <= this.type.id &&
          this.type.id <=  AttackType.MagicOffenceMin.id;
  }

  /**
   * 指定されたステータスのポイントを取得します。
   * <p>
   * 攻撃に関連するステータスのポイントを取得します。
   * </p>
   * @param type ステータスの種類
   * @return ステータスのポイント
   */
  private int getTypePoint(StatusType type) {
    for(Status status: (ArrayList<Status>)this.statuses.getList()) {
      if (status.type == type) {
        return status.point;
      }
    }
    return 0;
  }

}
