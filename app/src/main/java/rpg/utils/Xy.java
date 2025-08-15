package rpg.utils;

/**
 * 2次元の座標(x, y)や大きさを表現するための基本的なクラス。
 * <p>
 * {@link Pt} (座標) や {@link Size} (大きさ) クラスの親クラス（スーパークラス）として機能します。
 * Cloneableインターフェースを実装しており、オブジェクトの複製が可能です。
 * </p>
 */
public class Xy implements Cloneable{
  /** X座標または幅を表すフィールド */
  public int x;
  /** Y座標または高さを表すフィールド */
  public int y;

  /**
   * デフォルトコンストラクタ。
   * x, yをともに0で初期化します。
   */
  public Xy() {
    this.x = 0;
    this.y = 0;
  }

  /**
   * 文字列からx, yの値を設定するコンストラクタ。
   * <p>
   * "x.y" の形式の文字列を解析して座標を設定します。
   * </p>
   * @param str "x.y"形式の座標文字列
   */
  public Xy(String str) {
    if (str != null) {
      try {
        // 文字列を区切り文字で分割して座標を解析
        int index = str.indexOf("."); // 区切り文字としてドットを探す
        this.x = Integer.parseInt(str.substring(0, index-1));
        this.y = Integer.parseInt(str.substring(index+1));
      } catch(Exception e) {
        // 文字列のフォーマットが不正な場合、エラーになる可能性がある
        System.err.println("Xy(String str)の解析に失敗しました: " + str);
        this.x = 0;
        this.y = 0;
      }
    }
  }

  /**
   * x, yの値を直接指定して初期化するコンストラクタ。
   * @param x X座標または幅
   * @param y Y座標または高さ
   */
  public Xy(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * このXyオブジェクトのディープコピー（複製）を生成します。
   * @return 複製された新しいXyオブジェクト
   */
  public Xy clone() {
    Xy copy = null;
    try {
      // super.clone()でシャローコピーを作成し、
      // プリミティブ型であるx, yを個別にコピーすることでディープコピーとする
      copy = (Xy)super.clone();
      copy.x = this.x;
      copy.y = this.y;
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

}