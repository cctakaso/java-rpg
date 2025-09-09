package rpg.model.objects;

import rpg.model.types.ConditionType;
import rpg.utils.Pt;

import java.util.*;

/**
 * キャラクターのステータスを表すクラス。
 * <p>
 * キャラクターの能力値やステータス、レベルなどを管理し、
 * ゲーム内でのキャラクターの状態を表現します。
 * </p>
 */
public class CharStatus extends Base{
  protected ArrayList<Integer> condarray;     // キャラクターのステータス基本値
  protected ArrayList<Integer> condarrayRate; // キャラクターのステータス割合（100が通常、0が無効）
  protected Level level;                      // キャラクターのレベルと経験値に関する情報

  /**
   * デフォルトコンストラクタ。
   * <p>
   * キャラクターのステータスを初期化します。能力値、ステータス、レベルなどを
   * 初期状態に設定します。
   * </p>
   */
  public CharStatus() {
    super();
    this.condarray = new ArrayList<Integer>();
    this.level = new Level();
    setReset();
  }

  /**
   * キャラクターのレベルを取得します。
   * <p>
   * キャラクターのレベルは、経験値に基づいて変化し、能力値やステータスに影響を与えます。
   * </p>
   * @return キャラクターのレベル
   */
  public Level getLevel() {
    return this.level;
  }

  /**
   * キャラクターの所持金を取得します。
   * <p>
   * キャラクターが持っているお金の量を返します。
   * </p>
   * @return キャラクターの所持金
   */
  public int getMoney() {
    return getPoint(ConditionType.Money);
  }

  /**
   * キャラクターステータスのマジックポイントを取得します。
   * <p>
   * キャラクターのマジックポイントを表します。
   * </p>
   * @return マジックステータスのポイント
   */
  public int getMagicPoint() {
    return this.level.getMagicPoint(condarray, condarrayRate);
  }

  /**
   * キャラクターステータスの割合ポイントを取得します。
   * <p>
   * ステータスの割合ポイントは、キャラクターの能力や特性の比率を表します。
   * </p>
   * @param type ステータスの種類
   * @return 指定されたステータスの割合ポイント
   */
  public int getRatePoint(ConditionType type) {
    return this.level.getRatePoint(type, condarray, condarrayRate);
  }

  /**
   * キャラクターのヘルスポイントを取得します。
   * <p>
   * キャラクターのヘルスポイントは、キャラクターの体力を表します。
   * </p>
   * @return ヘルスポイント
   */
  public int getHealthPoint() {
    return this.level.getHealthPoint(condarray, condarrayRate);
  }

  /**
   * 防御時のステータスを設定します。
   * <p>
   * 攻撃がある場合は防御力を上げ、ない場合は通常の防御力を設定します。
   * </p>
   * @param attack 防御に関連する攻撃（nullの場合は通常の防御）
   */
  public void doProtect(Attack attack) {
    if (attack == null) {
      this.condarrayRate.set(ConditionType.Defence.id, Condition.INITIAL_RATE);
      this.condarrayRate.set(ConditionType.MagicDefence.id, Condition.INITIAL_RATE);
    } else if (attack.isDefence()) {
      ArrayList<Condition> condarray = (ArrayList<Condition>)attack.getConditions().getList();
      for (int i=0; i<condarray.size(); i++) {
        ConditionType type = condarray.get(i).getType();
        this.condarrayRate.set(type.id, condarray.get(i).getRate());
      }
    }
  }

  public void setReset() {
    this.condarrayRate = new ArrayList<Integer>(Arrays.asList(100,100,100,100,100,100,100,100,  10,10,10,10,10,10,10,10,10));
  }

  /**
   * キャラクターのパーティーにおける印刷用の文字列表現を返します。
   * <p>
   * キャラクターのレベルとステータスを含む文字列を返します。
   * </p>
   * @return キャラクターのパーティー印刷用の文字列表現
   */
  public String toPartyPrinting() {
    String str = toPrintingEx();
    if (str.length()>0) {
      str = "\n"+ str;
    }
    return this.level.toString(this.condarray, this.condarrayRate)+str;
  }

  /**
   * キャラクターの文字列表現を返します。
   * <p>
   * キャラクターのレベルとステータスを含む文字列を返します。
   * </p>
   * @return キャラクターの文字列表現
   */
  public String toString() {
    String str = toPrintingEx();
    if (str.length()>0) {
      str = "\n"+ str;
    }
    return this.level.toString(this.condarray, this.condarrayRate)+str;
  }

  /**
   * キャラクターの状態異常の文字列表現を返します。
   * <p>
   * 現在の状態異常とその値を含む文字列を返します。
   * </p>
   * @return 状態異常の文字列表現
   */
  public String toPrintingEx() {
    String str = "";
    if (this.condarray == null ||this.condarray.size() == 0) {
      return str;
    }
    for(int id = ConditionType.Poisoned.id; id<=ConditionType.Silenced.id; id++) {
      int value = this.condarray.get(id);
      if (value>0) {
        ConditionType st = ConditionType.valueOf(id);
        str += st+":"+value+" ";
      }
    }
    return str;
  }

  /**
   * キャラクターのステータスをマージします。
   * <p>
   * 他のキャラクターのステータスを現在のキャラクターに統合します。
   * </p>
   * @param charStatus マージするキャラクターのステータス
   */
  public void merge(CharStatus charStatus) {
    for(int i=0; i<condarray.size(); i++) {
      this.condarray.set(i, charStatus.condarray.get(i));
    }
  }


  /**
   * このCharStatusオブジェクトのクローンを、指定された番号とランダムな位置で作成します。
   * <p>
   * ステータス配列、ステータス割合配列、レベルを保持した新しいCharStatusオブジェクトを返します。
   * </p>
   * @param num クローンの番号（識別用）
   * @param randomPt ランダムな位置（座標）
   * @return 新しいCharStatusオブジェクト
   */
  @SuppressWarnings("unchecked")
  @Override
  public Base clone(int num, Pt randomPt) {
    CharStatus copy = null;
    try {
      copy = (CharStatus)super.clone(num, randomPt);
      copy.condarray = (ArrayList<Integer>)condarray.clone();
      copy.condarrayRate = (ArrayList<Integer>)condarrayRate.clone();
      copy.level = level.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * キャラクターの所持金を追加します。
   * <p>
   * 指定された金額をキャラクターの所持金に追加します。
   * </p>
   * @param money 追加する金額
   * @return 更新後の所持金
   */
  public int addMoney(int money) {
    Integer now = getPoint(ConditionType.Money);
    this.condarray.set(ConditionType.Money.id, now+money);
    return now+money;
  }

  /**
   * キャラクターの所持金を減らします。
   * <p>
   * 指定された金額をキャラクターの所持金から減らします。
   * </p>
   * @param money 減らす金額
   * @return 更新後の所持金
   */
  public int decMoney(int money) {
    return addMoney(money*-1);
  }

  /**
   * キャラクターのヘルスポイントを増加させます。
   * <p>
   * 指定された量だけキャラクターのヘルスポイントを増加させます。
   * </p>
   * @param delta 増加させるヘルスポイント
   * @return 更新後のヘルスポイント
   */
  public int incHealthPoint(int delta) {
    return this.level.incHealthPoint(delta, condarray, condarrayRate);
  }

  /**
   * キャラクターのマジックポイントを増加させます。
   * <p>
   * 指定された量だけキャラクターのマジックポイントを増加させます。
   * </p>
   * @param delta 増加させるマジックポイント
   * @return 更新後のマジックポイント
   */
  public int incMagicPoint(int delta) {
    return this.level.incMagicPoint(delta, condarray, condarrayRate);
  }

  /**
   * キャラクターのヘルスポイントを減少させます。
   * <p>
   * 指定された量だけキャラクターのヘルスポイントを減少させます。
   * </p>
   * @param delta 減少させるヘルスポイント
   * @return 更新後のヘルスポイント
   */
  public int decHealthPoint(int delta) {
    return this.level.decHealthPoint(delta, condarray, condarrayRate);
  }

  /**
   * キャラクターのマジックポイントを減少させます。
   * <p>
   * 指定された量だけキャラクターのマジックポイントを減少させます。
   * </p>
   * @param delta 減少させるマジックポイント
   * @return 更新後のマジックポイント
   */
  public int decMagicPoint(int delta) {
    return this.level.decMagicPoint(delta, condarray, condarrayRate);
  }

  /**
   * キャラクターの能力値を取得します。
   * <p>
   * キャラクターの能力値は、ゲーム内でのキャラクターの強さや特性を表します。
   * </p>
   * @return キャラクターの能力値のリスト
   */
  protected ArrayList<Integer> getConditions() {
    return this.condarray;
  }

  /**
   * キャラクターの能力値の割合を取得します。
   * <p>
   * 能力値の割合は、キャラクターの成長や装備によって変化することがあります。
   * </p>
   * @return キャラクターの能力値の割合のリスト
   */
  protected ArrayList<Integer> getConditionsRate() {
    return this.condarrayRate;
  }

  /**
   * キャラクターステータスのポイントを取得します。
   * <p>
   * ステータスのポイントは、キャラクターの能力や特性を表します。
   * </p>
   * @param type ステータスの種類
   * @return 指定されたステータスのポイント
   */
  private int getPoint(ConditionType type) {
    return this.condarray.get(type.id);
  }

}