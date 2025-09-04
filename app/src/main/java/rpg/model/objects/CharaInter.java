package rpg.model.objects;

import java.util.Scanner;

/**
 * キャラクターがプレイヤーと出会った際の対話やイベントを処理するインターフェースです。
 */
public interface CharaInter {
  /**
   * プレイヤーとの遭遇イベントを処理します。
   * @param scanner ユーザー入力用
   * @param myParty プレイヤーのパーティ
   * @return イベントが正常に終了した場合はtrue、そうでない場合はfalse
   */
  public boolean meet(Scanner scanner, Party myParty);
}
