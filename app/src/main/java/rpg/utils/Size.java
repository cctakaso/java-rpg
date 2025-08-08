package rpg.utils;

/**
 * 2次元の大きさ（幅と高さ）を表現するクラス。
 * <p>
 * {@link Xy}クラスを継承しており、xを幅（width）、yを高さ（height）として利用します。
 * マップのサイズや、フィールドのエリアサイズなどを表すために使用されます。
 * </p>
 */
public class Size extends Xy {
  /**
   * デフォルトコンストラクタ。
   * 幅、高さともに0で初期化します。
   */
  public Size() {
    super();
  }

  /**
   * 文字列から大きさを生成するコンストラクタ。
   * <p>
   * "幅,高さ" の形式の文字列を解析して大きさを設定します。
   * </p>
   * @param str 大きさを表す文字列
   */
  public Size(String str) {
    super(str);
  }

  /**
   * 幅と高さを直接指定して大きさを生成するコンストラクタ。
   * @param x 幅 (width)
   * @param y 高さ (height)
   */
  public Size(int x, int y) {
    super(x, y);
  }

  /**
   * このSizeオブジェクトのディープコピー（複製）を生成します。
   * @return 複製された新しいSizeオブジェクト
   */
  public Size clone() {
    Size copy = null;
    try {
      copy = (Size)super.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * 指定された点が、特定の開始点とこのSizeが定義する矩形領域内に含まれるかを判定します。
   * @param start 矩形領域の開始点（左上の座標）
   * @param check 判定したい点の座標
   * @return 点が領域内に含まれる場合はtrue、そうでない場合はfalse
   */
  public boolean isWithinArea(Pt start, Pt check) {
    // 矩形の右下のX座標を計算
    int cx = this.x + start.x;
    // 矩形の右下のY座標を計算
    int cy = this.y + start.y;
    // X座標とY座標が、それぞれ矩形の範囲内にあるかチェック
    return (start.x<= check.x && check.x < cx) &&
          (start.y<= check.y && check.y < cy);
  }

}
