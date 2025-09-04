package rpg.model.objects;
import java.util.ArrayList;

import rpg.model.types.ItemType;
import rpg.model.types.ConditionType;
import rpg.utils.Pt;

/**
 * アイテムを表すクラス。
 * <p>
 * アイテムは、ゲーム内で使用されるアイテムの基本的なプロパティと機能を提供します。
 * アイテムの種類（ItemType）、数量、ステータスなどを管理します。
 * </p>
 */
public class Item extends Base{
  protected ItemType itemType;      // アイテムの種類
  protected int count;              // アイテムの数量
  protected Conditions conditions;  // アイテムに関連するステータス

  /**
   * デフォルトコンストラクタ。
   * <p>
   * アイテムのプロパティを初期化します。
   * </p>
   */
  public Item() {
    this(100);
  }

  /**
   * 重さを指定してアイテムを初期化します。
   * <p>
   * アイテムの重さを指定して、アイテムの種類と数量を初期化します。
   * </p>
   * @param weight アイテムの重さ
   */
  public Item(int weight) {
    super();
    this.count = 1;
    conditions = new Conditions();
    conditions.add(new Condition(ConditionType.Weight, weight));
  }

  /**
   * アイテムの種類を取得します。
   * <p>
   * アイテムの種類は、アイテムがどのタイプのアイテムであるかを示します。
   * </p>
   * @return アイテムの種類
   */
  public ItemType getItemType() {
    return this.itemType;
  }

  /**
   * アイテムの数量を取得します。
   * <p>
   * アイテムの数量は、所持しているアイテムの数を示します。
   * </p>
   * @return アイテムの数量
   */
  public int getCount() {
    return this.count;
  }

  /**
   * アイテムの数量を設定します。
   * <p>
   * アイテムの数量は、所持しているアイテムの数を更新します。
   * </p>
   * @param count アイテムの数量
   */
  public void setCount(int count) {
    this.count = count;
  }

  /**
   * アイテムに関連するステータスを取得します。
   * <p>
   * アイテムに関連するステータスは、アイテムの特性や効果を示します。
   * </p>
   * @return アイテムに関連するステータス
   */
  public Conditions getConditions() {
    return this.conditions;
  }

  /**
   * アイテムの価格を取得します。
   * <p>
   * アイテムに関連するステータスから金銭の価格を取得します。
   * </p>
   * @return アイテムの価格
   */
  public int getPrice() {
    for (Condition condition: (ArrayList<Condition>)this.conditions.getList()) {
      if (condition.type == ConditionType.Money) {
        return condition.point;
      }
    }
    return 0;
  }

  /**
   * アイテムの文字列表現を返します。
   * <p>
   * アイテムの名前と数量を含む文字列を返します。
   * </p>
   * @return アイテムの文字列表現
   */
  public String toString() {
    //return super.toString() + "x " + this.count + "["+ this.conditions.toString() + "]";
    return super.toString() + "x" + this.count;
  }

  /**
   * アイテムの数量を追加します。
   * <p>
   * アイテムの数量を指定されたアイテムの数量に加算します。
   * </p>
   * @param item アイテム
   */
  public void addCount(Item item) {
    this.count += item.count;
  }

  /**
   * アイテムのクローンを作成します。
   * <p>
   * アイテムの種類、数量、および関連するステータスを保持した新しいItemオブジェクトを返します。
   * </p>
   * @return 新しいItemオブジェクト
   */
  public Base clone() {
    return this.clone(0, null);
  }

  /**
   * アイテムのクローンを作成します。
   * <p>
   * 指定された数値とランダムポイントを持つ新しいItemオブジェクトを返します。
   * </p>
   * @param num クローンの識別番号
   * @param randomPt ランダムポイント
   * @return 新しいItemオブジェクト
   */
  public Base clone(int num, Pt randomPt) {
    Item copy = null;
    try {
      copy = (Item)super.clone(num, randomPt);
      copy.itemType = this.itemType;
      copy.count = this.count;
      copy.conditions = (Conditions)this.conditions.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * アイテムが装備品かどうかを判定します。
   * <p>
   * アイテムの種類が装備品である場合、trueを返します。
   * </p>
   * @return 装備品の場合はtrue、それ以外はfalse
   */
  public boolean isGear() {
    return this.itemType.isGear();
  }

  /**
   * アイテムが金銭であるかどうかを判定します。
   * <p>
   * アイテムの種類が金銭である場合、trueを返します。
   * </p>
   * @return 金銭の場合はtrue、それ以外はfalse
   */
  public boolean isMoney() {
    return this.itemType.isMoney();
  }
}
