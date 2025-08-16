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

  public void printMessage() {
    // メッセージをコンソールに出力
    System.out.println();
  }

  public void printMessage(String message) {
      // メッセージをコンソールに出力
      System.out.println(message!=null ? message : "");
  }

  public void whereYouGoing() {
      // 現在のフィールドとパーティの情報を表示
      printMap();
      printPartyStatus();
  }

  public void printPartyStatus() {
    // プレイヤーのステータスを表示
    App.view.printMessage();
    App.view.printMessage(adventure.getParty().toString(true));
  }

  private void printMap() {
      // 現在位置を含めてマップ全体を描画
      String str = adventure.getFields().toString(adventure.getParty());
      App.view.printMessage(str);
  }


}
