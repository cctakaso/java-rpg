package rpg.model.objects;

/**
 * オブジェクトのリストを管理するための基本的なインターフェースです。
 */
public interface ListInter {
  /**
   * リストにオブジェクトを追加します。
   * @param one 追加するオブジェクト
   */
  public void add(Base one);
  /**
   * リストからオブジェクトを削除します。
   * @param one 削除するオブジェクト
   * @return 削除されたオブジェクト
   */
  public Base remove(Base one);
  /**
   * リストのサイズを返します。
   * @return リスト内のオブジェクト数
   */
  public int size();
}
