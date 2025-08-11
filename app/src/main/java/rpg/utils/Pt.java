package rpg.utils;
import rpg.model.types.*;

/**
 * 2次元座標（ポイント）を表現するクラス。
 * <p>
 * {@link Xy}クラスを継承しており、マップ上のキャラクターの位置や、
 * フィールドの原点などを表すために使用されます。
 * </p>
 */
public class Pt extends Xy {
  /**
   * デフォルトコンストラクタ。
   * (0, 0) の座標で初期化します。
   */
  public Pt() {
    super();
  }

  /**
   * 文字列から座標を生成するコンストラクタ。
   * <p>
   * "x,y" の形式の文字列を解析して座標を設定します。
   * </p>
   * @param str 座標を表す文字列
   */
  public Pt(String str) {
    super(str);
  }

  /**
   * x, yの値を直接指定して座標を生成するコンストラクタ。
   * @param x X座標
   * @param y Y座標
   */
  public Pt(int x, int y) {
    super(x, y);
  }

  /**
   * このPtオブジェクトのディープコピー（複製）を生成します。
   * @return 複製された新しいPtオブジェクト
   */
  public Pt clone() {
    Pt copy = null;
    try {
      // 親クラス(Xy)がCloneableを実装していることを前提とする
      copy = (Pt)super.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * 指定されたキー入力に基づいて座標を移動させます。
   * @param key 移動方向を示すキー
   * @return 移動後の自身のインスタンス
   */
  public Pt moveKey(Defs.Key key) {
    switch (key) {
      case Defs.Key.West: // 西へ
        this.x -=10;
        break;
      case Defs.Key.North: // 北へ
        this.y -=10;
        break;
      case Defs.Key.East: // 東へ
        this.x +=10;
        break;
      case Defs.Key.South: // 南へ
        this.y +=10;
        break;
      default:
        break;
    }
    return this;
  }

  /**
   * 指定された原点座標を基準とした、この点の絶対座標を計算します。
   * @param orginPt 基準となる原点座標
   * @return 計算された絶対座標を持つ新しいPtオブジェクト
   */
  public Pt getAbsolutePt(Pt orginPt) {
    return new Pt(orginPt.x + this.x, orginPt.y + this.y);
  }

  /**
   * 指定された原点座標を基準とした、この点の相対座標（ローカル座標）を計算します。
   * @param orginPt 基準となる原点座標
   * @return 計算された相対座標を持つ新しいPtオブジェクト
   */
  public Pt getLocalPt(Pt orginPt) {
    return new Pt(this.x - orginPt.x, this.y - orginPt.y);
  }

  /**
   * 指定されたPtオブジェクトと、このオブジェクトの座標が等しいかどうかを判定します。
   * @param pt 比較対象のPtオブジェクト
   * @return 座標が等しい場合はtrue、そうでない場合はfalse
   */
  public boolean isEquals(Pt pt) {
    return pt.x == this.x && pt.y == this.y;
  }

}
