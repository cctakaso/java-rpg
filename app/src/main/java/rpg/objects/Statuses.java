package rpg.objects;

import java.util.ArrayList;
import java.util.List;
import rpg.types.StatusType;

/**
 * 複数のStatusオブジェクトを管理するためのリストクラスです。
 * Listsクラスを継承し、キャラクターの全ステータスなどを表現するために使用されます。
 */
public class Statuses extends Lists{
  /**
   * Statusオブジェクトを格納するリスト
   */
  public ArrayList<Status> children;

  /**
   * Statusesの新しいインスタンスを生成します。
   * 内部のchildrenリストを初期化します。
   */
  public Statuses() {
    super();
    children = new ArrayList<Status>();
  }

  /**
   * Statusesクラスの新しいインスタンスを返します。
   * @return 新しいStatusesインスタンス
   */
  protected Lists getNewInstance() {
    return new Statuses();
  }

  /**
   * 管理しているStatusのリストを返します。
   * @return Statusオブジェクトのリスト
   */
  public List<?> getList() {
    return children;
  }

  /**
   * 管理するStatusのリストを設定します。
   * @param children 設定するStatusオブジェクトのリスト
   */
  @SuppressWarnings("unchecked")
  protected void setList(List<?> children) {
    this.children = (ArrayList<Status>)children;
  }

  /**
   * すべてのステータス情報を連結した文字列を返します。
   * @return ステータス情報の文字列
   */
  @SuppressWarnings("unchecked")
  public String toString() {
    String str = "";
    for(Status status: (ArrayList<Status>)getList()) {
      str += status.toString() + " ";
    }
    return str;
  }

  /**
   * 指定されたステータスタイプの基本値(point)を取得します。
   * @param st 取得したいステータスのタイプ
   * @return ステータスの基本値。見つからない場合は0。
   */
  public int getPoint(StatusType st) {
    Status status = getStatus(st);
    if (status != null) {
        return status.point;
    }
    return 0;
  }

  /**
   * 指定されたステータスタイプの比率を考慮した値(rated point)を取得します。
   * @param st 取得したいステータスのタイプ
   * @return 比率で計算されたステータス値。見つからない場合は0。
   */
  public int getRatedPoint(StatusType st) {
    Status status = getStatus(st);
    if (status != null) {
        return status.point * status.rate/100;
    }
    return 0;
  }

  /**
   * 指定されたステータスタイプのStatusオブジェクトをリストから検索して返します。
   * @param st 検索するステータスのタイプ
   * @return 見つかったStatusオブジェクト。見つからない場合はnull。
   */
  private Status getStatus(StatusType st) {
    for (Status status: children) {
      if (status.type == st) {
        return status;
      }
    }
    return null;
  }
}