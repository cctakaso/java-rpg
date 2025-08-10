package rpg.objects;

import rpg.types.StatusType;
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
  protected ArrayList<Integer> abilities; // キャラクターの能力値
  protected ArrayList<Integer> abilitiesRate; // 能力値の割合
  // ステータスの割合（100が通常、0が無効）
  // ステータスの割合は、ステータスの種類に応じて
  // 変化することがあります（例：防御時に上昇する防御力や魔法防御力など）。
  // ステータスの種類は、StatusTypeで定義されている定数を使用します。
  protected ArrayList<Integer> statues; // キャラクターのステータス
  protected ArrayList<Integer> statuesRate; // ステータスの割合
  // レベルに関する情報を管理するLevelオブジェクト
  // レベルは、キャラクターの成長や経験値に基づいて変化します。
  // レベルアップ時に能力値やステータスが増加することがあります。
  // Levelクラスは、レベルに関する詳細な情報を提供します。
  // レベルの成長や経験値の管理、レベルアップ時の処理などを行います。
  protected Level level;  // キャラクターのレベル

  /**
   * デフォルトコンストラクタ。
   * <p>
   * キャラクターのステータスを初期化します。能力値、ステータス、レベルなどを
   * 初期状態に設定します。
   * </p>
   */
  public CharStatus() {
    super();
    this.abilities = new ArrayList<Integer>();
    this.abilitiesRate = new ArrayList<Integer>(Arrays.asList(10,10,10,10,10,10,10,10,10,10,10));
    this.statues = new ArrayList<Integer>();
    this.level = new Level();
    setReset();
  }

  /**
   * キャラクターの能力値を取得します。
   * <p>
   * キャラクターの能力値は、ゲーム内でのキャラクターの強さや特性を表します。
   * </p>
   * @return キャラクターの能力値のリスト
   */
  protected ArrayList<Integer> getStatues() {
    return this.statues;
  }

  /**
   * キャラクターの能力値の割合を取得します。
   * <p>
   * 能力値の割合は、キャラクターの成長や装備によって変化することがあります。
   * </p>
   * @return キャラクターの能力値の割合のリスト
   */
  protected ArrayList<Integer> getStatuesRate() {
    return this.statuesRate;
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

  public void doGard(boolean isGard) {
    this.abilitiesRate.set(StatusType.Defense.id, isGard ? 20:10);
    this.abilitiesRate.set(StatusType.MagicDefense.id, isGard ? 20:10);
  }

  public void setReset() {
    this.statuesRate = new ArrayList<Integer>(Arrays.asList(100,100,100,100,100,100,100,100,  10,10,10,10,10,10,10,10,10));
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
    return this.level.toString(this.statues, this.statuesRate)+str;
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
    return this.level.toString(this.statues, this.statuesRate)+str;
  }

  /**
   * キャラクターの文字列表現を返します。
   * <p>
   * キャラクターの名前と位置（座標）を含む文字列を返します。
   * </p>
   * @return キャラクターの文字列表現
   */
  public String toPrintingEx() {
    String str = "";
    if (this.statues == null ||this.statues.size() == 0) {
      return str;
    }
    for(int id = StatusType.Poisoned.id; id<=StatusType.Silenced.id; id++) {
      int value = this.statues.get(id);
      if (value>0) {
        StatusType st = StatusType.valueOf(id);
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
    for(int i=0; i<statues.size(); i++) {
      this.statues.set(i, charStatus.statues.get(i));
    }
  }

  /**
   * キャラクターのクローンを作成します。
   * <p>
   * キャラクターの名前、位置、タイプ、会話、ステータス、装備、アイテム、攻撃を
   * 保持した新しいCharacterオブジェクトを返します。
   * </p>
   * @return 新しいCharacterオブジェクト
   */
  public CharStatus clone() {
    return this.clone(0, null);
  }

  /**
   * キャラクターのクローンを作成します。
   * <p>
   * キャラクターの名前、位置、タイプ、会話、ステータス、装備、アイテム、攻撃を
   * 保持した新しいCharacterオブジェクトを返します。
   * </p>
   * @param num クローンの番号（識別用）
   * @param randomPt ランダムな位置（座標）
   * @return 新しいCharacterオブジェクト
   */
  @Override
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

  /**
   * キャラクターの所持金を取得します。
   * <p>
   * キャラクターが持っているお金の量を返します。
   * </p>
   * @return キャラクターの所持金
   */
  public int getMoney() {
    return getPoint(StatusType.Money);
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
    Integer now = getPoint(StatusType.Money);
    this.statues.set(StatusType.Money.id, now+money);
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
   * キャラクターステータスのポイントを取得します。
   * <p>
   * ステータスのポイントは、キャラクターの能力や特性を表します。
   * </p>
   * @param type ステータスの種類
   * @return 指定されたステータスのポイント
   */
  private int getPoint(StatusType type) {
    return this.statues.get(type.id);
  }

  /**
   * キャラクターステータスのマジックポイントを取得します。
   * <p>
   * キャラクターのマジックポイントを表します。
   * </p>
   * @return マジックステータスのポイント
   */
  public int getMagicPoint() {
    return this.level.getMagicPoint(statues, statuesRate);
  }

  /**
   * キャラクターステータスの割合ポイントを取得します。
   * <p>
   * ステータスの割合ポイントは、キャラクターの能力や特性の比率を表します。
   * </p>
   * @param type ステータスの種類
   * @return 指定されたステータスの割合ポイント
   */
  public int getRatePoint(StatusType type) {
    return this.level.getRatePoint(type, statues, statuesRate);
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
    return this.level.incHealthPoint(delta, statues, statuesRate);
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
    return this.level.incMagicPoint(delta, statues, statuesRate);
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
    return this.level.decHealthPoint(delta, statues, statuesRate);
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
    return this.level.decMagicPoint(delta, statues, statuesRate);
  }

}
