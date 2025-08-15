package rpg.model.objects;

import rpg.model.types.ConditionType;
import rpg.utils.Pt;

/**
 * キャラクターの個別のステータス（能力値）を表すクラスです。
 * 例えば、HP、攻撃力、防御力などがこのクラスのインスタンスとして表現されます。
 */
public class Condition extends Base{
  /**
   * ステータスの初期比率（100%）
   */
  static int INITIAL_RATE = 100;

  /**
   * ステータスの種類（HP, MP, 攻撃力など）
   */
  protected ConditionType type;
  /**
   * ステータスの基本値（1〜100）
   */
  protected int point;
  /**
   * ステータスの一時的な比率（%）。100がデフォルト。
   * 魔法やアイテムによる一時的な増減を表します。
   */
  protected int rate;

  /**
   * ステータスの種類と基本値を指定して新しいStatusインスタンスを生成します。
   * @param type ステータスの種類
   * @param point ステータスの基本値
   */
  public Condition(ConditionType type, int point) {
    this();
    this.type = type;
    this.point = point;
  }

  /**
   * デフォルトコンストラクタ。
   * 比率を初期値（100）に設定します。
   */
  public Condition() {
    super();
    this.rate = INITIAL_RATE;
  }

  /**
   * ステータスの基本値を取得します。
   * @return ステータスの基本値
   */
  public ConditionType getType() {
    return this.type;
  }

  /**
   * ステータスの比率を取得します。
   * <p>
   * 比率は、ステータスの一時的な増減を表します。
   * </p>
   * @return ステータスの比率
   */
  public int getRate() {
    return this.rate;
  }

  /**
   * ステータスの情報を文字列として返します。
   * @return "種類:基本値(比率)" の形式の文字列
   */
  public String toString() {
    return this.type+":"+this.point+"("+this.rate+")";
  }

  /**
   * このStatusオブジェクトのクローンを作成します。
   * @return Statusオブジェクトの新しいインスタンス
   */
  public Condition clone() {
    return this.clone(0, null);
  }

  /**
   * このStatusオブジェクトのクローンを、指定された番号とランダムな位置で作成します。
   * @param num クローン番号
   * @param randomPt ランダムな位置
   * @return Statusオブジェクトの新しいインスタンス
   */
  public Condition clone(int num, Pt randomPt) {
    Condition copy = null;
    try {
      copy = (Condition)super.clone(num, randomPt);
      copy.type = this.type;
      copy.point = this.point;
      copy.rate = this.rate;
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }
}