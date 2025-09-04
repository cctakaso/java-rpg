package rpg.view.console;

import rpg.Adventure;

/**
 * コンソールベースのRPGゲームにおける表示（ビュー）を担当するクラスです。
 * <p>
 * このクラスは、シングルトンパターンで実装されており、ゲーム全体の表示処理を一元管理します。
 * 国際化対応されたメッセージの取得や、整形されたテキストのコンソールへの出力を担当します。
 * </p>
 */
public class View {
  private static View singleton = new View();
  private Adventure adventure;  // ゲームの冒険情報を保持する

  /**
   * Viewクラスのシングルトンインスタンスを返します。
   * @return Viewの唯一のインスタンス
   */
  public static View get(){
      return singleton;
  }

  /**
   * Viewを初期化します。
   * <p>
   * ゲームのメインロジックを持つAdventureインスタンスを保持し、
   * 表示に必要な情報にアクセスできるようにします。
   * </p>
   * @param adventure ゲームの冒険情報を持つAdventureインスタンス
   */
  public void initialize(Adventure adventure) {
      this.adventure = adventure;
  }

  /**
   * 指定されたキーに対応するメッセージをリソースバンドルから取得し、フォーマットします。
   * <p>
   * 可変長引数を受け取り、{@link String#format(String, Object...)} を用いて
   * メッセージ内のプレースホルダを動的な値に置き換えます。
   * キーが見つからない場合は、デバッグのためにキー自体を返します。
   * </p>
   * @param key メッセージのキー
   * @param args フォーマット文字列に埋め込む引数
   * @return 翻訳およびフォーマットされた文字列
   */
  public static String toString(String key, Object... args) {
    if (key == null || key.isEmpty()) {
      return "";
    }
    String format;
    try {
      format = Adventure.messages.getString(key);
    } catch(Exception e) {
      format = key; // キーが見つからない場合はキー自体を返す
    }
    if (args.length > 0) {
      return String.format(format, args);
    }
    return format;
  }

  /**
   * コンソールに空行を一つ出力します。
   */
  public void printMessage() {
    System.out.println();
  }

  /**
   * 指定されたキーに対応するメッセージをコンソールに出力します。
   * <p>
   * 可変長引数を受け取り、フォーマットされたメッセージを出力することができます。
   * </p>
   * @param key メッセージのキー
   * @param args フォーマット文字列に埋め込む引数
   */
  public void printMessage(String key, Object... args) {
    String message = toString(key, args);
    System.out.println(message);
  }

  /**
   * プレイヤーが次に行く場所を尋ねるプロンプトと、現在のマップ、パーティのステータスを表示します。
   */
  public void whereYouGoing() {
      // 現在のフィールドとパーティの情報を表示
      printMap();
      printMessage();
      adventure.getParty().printParty();
  }

  /**
   * マップをコンソールに描画します。
   */
  private void printMap() {
      // 現在位置を含めてマップ全体を描画
      String str = adventure.getFields().toString(adventure.getParty());
      printMessage(str);
  }


}
