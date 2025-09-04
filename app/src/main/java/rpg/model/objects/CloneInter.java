package rpg.model.objects;

import rpg.utils.Pt;

/**
 * オブジェクトのクローン（複製）を作成するためのインターフェースです。
 */
public interface CloneInter {
  /**
   * オブジェクトのシャローコピーを作成します。
   * @return クローンされたオブジェクト
   */
  public Base clone();
  /**
   * オブジェクトを複数回クローンし、それぞれにランダムな位置情報を設定します。
   * @param num クローンする数
   * @param randomPt 位置情報のランダム範囲
   * @return クローンされたオブジェクト
   */
  public Base clone(int num, Pt randomPt);
}
