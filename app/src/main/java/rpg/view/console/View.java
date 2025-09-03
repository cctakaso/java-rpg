package rpg.view.console;

import rpg.App;
import rpg.Adventure;

/**
 * コンソールベースのRPGゲームにおける表示（ビュー）を担当するクラス。
 * <p>
 * ゲームの状態をユーザーに表示するための各種メソッドを提供します。
 * </p>
 */
public class View {
  private static View singleton = new View();
  private Adventure adventure;  // ゲームの冒険情報を保持する

  /**
   * シングルトンインスタンスを取得するメソッド。
   * @return ビューのインスタンス
   */
  public static View get(){
      return singleton;
  }

  /**
   * ゲームビューの初期化処理を実装します。
   * <p>
   * ゲームの冒険情報を設定し、必要な初期化を行います。
   * </p>
   * @param adventure ゲームの冒険情報
   */
  public void initialize(Adventure adventure) {
      // ゲームビューの初期化処理を実装
      this.adventure = adventure;
  }

  public static String toString(String key, Object... args) {
    if (key == null || key.isEmpty()) {
      return "";
    }
    String str=key;
    try {
      str = Adventure.messages.getString(key);
    }catch(Exception e) {
    }
    if (args.length > 0) {
      return String.format(str, args);
    }
    return str;
  }

  public void printMessage() {
    // メッセージをコンソールに出力
    System.out.println();
  }

  public void printMessage(String key, Object... args) {
    String message = toString(key, args);
    System.out.println(message);
  }

  public void whereYouGoing() {
      // 現在のフィールドとパーティの情報を表示
      printMap();
      printMessage();
      adventure.getParty().printParty();
  }

  private void printMap() {
      // 現在位置を含めてマップ全体を描画
      String str = adventure.getFields().toString(adventure.getParty());
      printMessage(str);
  }


}
