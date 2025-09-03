package rpg.utils;

import rpg.view.console.View;

/**
 * ユーザーに提示する選択肢一つ分を表現するクラス。
 * <p>
 * 質問に対する回答の選択肢として使われます。
 * 例えば、「1. はい」「2. いいえ」の一つ分がこのクラスのインスタンスにあたります。
 * ジェネリクス{@code <T>} を使用しており、選択肢に関連付ける値（value）を任意の型にできます。
 * </p>
 * @param <T> 選択肢に関連付けられる値の型
 */
public class Answer<T> {
  // 選択肢の番号（例: 1, 2, 3...）
  private int index;
  // 画面に表示される選択肢の文言（例: 「はい」「いいえ」）
  private String label;
  // この選択肢が選ばれた時に返される実際の値
  private T value;
  // 選択肢に関する補足情報（例: アイテムの説明など）
  private String info;

  /**
   * デフォルトコンストラクタ。
   * 空のラベル、null値、空情報で初期化します。
   */
  public Answer() {
    this(new String(), null, new String());
  }

  /**
   * ラベルのみを指定するコンストラクタ。
   * @param label 選択肢の文言
   */
  public Answer(String label) {
    this(label, null, "");
  }

  /**
   * ラベルと値を指定するコンストラクタ。
   * @param label 選択肢の文言
   * @param value 選択肢に関連付けられる値
   */
  public Answer(String label, T value) {
    this(label, value, "");
  }

  /**
   * ラベル、値、補足情報を指定するコンストラクタ。
   * @param label 選択肢の文言
   * @param value 選択肢に関連付けられる値
   * @param info 選択肢の補足情報
   */
  public Answer(String label, T value, String info) {
    //this.index = 0; // indexはAnswersクラスによって後から設定される
    this.label = label;
    this.value = value;
    this.info = info;
  }

  /**
   * 選択肢のインデックス（番号）を取得します。
   * @return インデックス
   */
  public int getIndex() {
      return index;
  }

  /**
   * 選択肢のインデックス（番号）を設定します。
   * @param index インデックス
   */
  public void setIndex(int index) {
      this.index = index;
  }

  /**
   * 選択肢の表示文言（ラベル）を取得します。
   * @return ラベル文字列
   */
  public String getLabel() {
      return View.toString(label);
  }

  /**
   * 選択肢に関連付けられた値を取得します。
   * @return 関連付けられた値
   */
  public T getValue() {
      return value;
  }

  /**
   * 選択肢の補足情報を取得します。
   * @return 補足情報の文字列
   */
  public String getInfo() {
      return info;
  }

}
