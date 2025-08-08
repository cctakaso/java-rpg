package rpg.types;

/**
 * プロジェクト全体で使われる定義（Definitions）をまとめたクラス。
 * <p>
 * 主に、入れ子になったEnumなど、特定のカテゴリに分類しにくい定数を定義します。
 * </p>
 */
public class Defs {
  /**
   * 方向や特殊な入力キーを定義する列挙型(Enum)。
   * <p>
   * テンキーの配置に似たIDが割り当てられています。
   * </p>
   */
  public enum Key {
    West(4),      // 西 (テンキーの4)
    North(8),     // 北 (テンキーの8)
    East(6),      // 東 (テンキーの6)
    South(2),     // 南 (テンキーの2)
    End(0),       // 終了・キャンセル
    Error(-1);    // エラー・無効な値

    /**
     * キーに対応する整数ID。
     */
    public int id; // フィールドの定義

    /**
     * Enumのコンストラクタ。
     * @param id キーに割り当てるID
     */
    private Key(int id) { // コンストラクタの定義
      this.id = id;
    }
  };

  /**
   * 数値から対応するKey Enum定数を取得します。
   * <p>
   * 注意: 現在の実装にはバグがあります。
   * `Key.West`の比較が重複しており、正しい分岐が行われません。
   * </p>
   * @param num 変換したい数値
   * @return 対応するKey定数。見つからない場合はKey.Error。
   */
  public static Key getDirection(int num) {
    // TODO: バグ修正 - Key.Westの比較が重複している。switch文を使うとより安全で効率的。
    if (num == Key.West.id) {
      return Key.West;
    }else if (num == Key.West.id) { // この行は重複している
      return Key.West;
    }else if (num == Key.North.id) {
      return Key.North;
    }else if (num == Key.East.id) {
      return Key.East;
    }else if (num == Key.South.id) {
      return Key.South;
    }else if (num == Key.End.id) {
      return Key.End;
    }
    return Key.Error;
  }

}
