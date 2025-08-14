package rpg.model.objects;

import java.util.ArrayList;
import java.util.List;

import rpg.model.types.ConditionType;

/**
 * 複数のConditionオブジェクトを管理するためのリストクラスです。
 * Listsクラスを継承し、キャラクターの全ステータスなどを表現するために使用されます。
 */
public class Conditions extends Lists{
  /**
   * Conditionオブジェクトを格納するリスト
   */
  protected ArrayList<Condition> children;

  /**
   * Conditionsの新しいインスタンスを生成します。
   * 内部のchildrenリストを初期化します。
   */
  public Conditions() {
    super();
    children = new ArrayList<Condition>();
  }

  /**
   * Conditionsクラスの新しいインスタンスを返します。
   * @return 新しいConditionsインスタンス
   */
  protected Lists getNewInstance() {
    return new Conditions();
  }

  /**
   * 管理しているStatusのリストを返します。
   * @return Statusオブジェクトのリスト
   */
  @Override
  public List<Condition> getList() {
    return children;
  }

  /**
   * 管理するStatusのリストを設定します。
   * @param children 設定するStatusオブジェクトのリスト
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setList(List<?> children) {
    this.children = (ArrayList<Condition>)children;
  }

  /**
   * すべてのステータス情報を連結した文字列を返します。
   * @return ステータス情報の文字列
   */
  @SuppressWarnings("unchecked")
  public String toString() {
    String str = "";
    for(Condition condition: (ArrayList<Condition>)getList()) {
      str += condition.toString() + " ";
    }
    return str;
  }

  /**
   * 指定されたステータスタイプの基本値(point)を取得します。
   * @param st 取得したいステータスのタイプ
   * @return ステータスの基本値。見つからない場合は0。
   */
  public int getPoint(ConditionType st) {
    Condition condition = getCondition(st);
    if (condition != null) {
        return condition.point;
    }
    return 0;
  }

  /**
   * 指定されたステータスタイプの比率を考慮した値(rated point)を取得します。
   * @param st 取得したいステータスのタイプ
   * @return 比率で計算されたステータス値。見つからない場合は0。
   */
  public int getRatedPoint(ConditionType st) {
    Condition condition = getCondition(st);
    if (condition != null) {
        return condition.point * condition.rate/Condition.INITIAL_RATE;
    }
    return 0;
  }

  /**
   * 指定されたステータスタイプのConditionオブジェクトをリストから検索して返します。
   * @param st 検索するステータスのタイプ
   * @return 見つかったConditionオブジェクト。見つからない場合はnull。
   */
  private Condition getCondition(ConditionType st) {
    for (Condition condition: children) {
      if (condition.type == st) {
        return condition;
      }
    }
    return null;
  }
}