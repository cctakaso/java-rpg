package rpg.model.objects;

import java.lang.reflect.Field;
import java.util.*;

import rpg.view.console.View;
import rpg.utils.*;

/**
 * RPGゲームの基本オブジェクトを表す抽象クラス。
 * <p>
 * このクラスは、ゲーム内の基本的なオブジェクト（キャラクター、アイテムなど）の
 * 共通のプロパティとメソッドを定義します。リフレクションを用いて、オブジェクトの
 * フィールドを動的にスキャンし、関連付けなどを行います。
 * </p>
 */
public abstract class Base implements Cloneable, CloneInter, Reflection{
  protected String name;  // オブジェクトの名前
  protected Pt pt;  // オブジェクトの位置（座標）

  /**
   * デフォルトコンストラクタ。
   * <p>
   * オブジェクトの名前を空文字列で初期化し、位置（座標）をPtオブジェクトで初期化します。
   * </p>
   */
  public Base() {
    this.name = new String();
    this.pt = new Pt();
  }

  /**
   * オブジェクトの名前を取得します。
   * <p>
   * オブジェクトの名前は、ゲーム内での識別や表示に使用されます。
   * </p>
   * @return オブジェクトの名前
   */
  public String getName() {
    return this.name;
  }

  /**
   * オブジェクトの位置（座標）を取得します。
   * <p>
   * オブジェクトの位置は、ゲーム内での配置や移動に使用されます。
   * </p>
   * @return オブジェクトの位置（座標）
   */
  public Pt getPt() {
    return this.pt;
  }

  /**
   * オブジェクトの位置（座標）を設定します。
   * <p>
   * オブジェクトの位置は、ゲーム内での配置や移動に使用されます。
   * </p>
   * @param pt オブジェクトの位置（座標）
   */
  public void setPt(Pt pt) {
    this.pt = pt;
  }

  /**
   * オブジェクトの名前を文字列として返します。
   * @return オブジェクトの名前
   */
  public String toString() {
    return View.toString(this.name);
  }

  /**
   * オブジェクトのクローンを作成します。
   * <p>
   * このメソッドは、オブジェクトの名前と位置（座標）をコピーして新しいBaseオブジェクトを返します。
   * </p>
   * @return 新しいBaseオブジェクトのクローン
   */
  public Base clone() {
    return this.clone(0, null);
  }

  /**
   * オブジェクトのクローンを作成します。
   * <p>
   * 指定された数値とランダムポイントを持つ新しいBaseオブジェクトを返します。
   * </p>
   * @param num クローンの識別番号
   * @param randomPt ランダムポイント
   * @return 新しいBaseオブジェクト
   */
  public Base clone(int num, Pt randomPt) {
    Base copy = null;
    try {
      copy = (Base)super.clone();
      copy.name = this.name != null ? this.name:null;
      copy.pt = this.pt!=null ? this.pt.clone():null;
      if (randomPt != null) {
        Random rand = new Random();
        copy.pt = new Pt(rand.nextInt(randomPt.x)/10*10, rand.nextInt(randomPt.y)/10*10);
      }
    }catch (Exception e){
      e.printStackTrace();
    }

    return copy;
  }

  /**
   * オブジェクトの名前をコピーします。
   * <p>
   * 引数として渡されたBaseオブジェクトの名前と位置（座標）を、このオブジェクトにコピーします。
   * </p>
   * @param base コピー元のBaseオブジェクト
   */
  public void copy(Base base) {
    this.name = base.name;
    this.pt = base.pt;
  }

  /**
   * オブジェクトのクラス階層をスキャンし、リフレクションを用いて関連付けを解決します。
   * <p>
   * フィールドが {@code Lists} 型であれば {@code SetFromDic} を呼び出し、
   * {@code Reflection} インターフェースを実装していれば再帰的に {@code scanClasses} を呼び出します。
   * これにより、JSONファイル内のIDと実際のオブジェクト参照を解決します。
   * </p>
   */
  public void scanClasses() {
    Class<?> cls = this.getClass();
    // rpg.model.objectsパッケージに属するクラスであれば、親クラスを遡ってスキャンを続ける
    while(cls != null && cls.getPackageName().startsWith("rpg.model.objects")) { // パッケージ名の比較をより堅牢に
      this.scanClass(cls);
      cls = cls.getSuperclass();
    }
    // もしこのオブジェクトがListsのインスタンスであれば、辞書からのデータ設定処理を実行
    if (this instanceof Lists) {
      ((Lists)this).SetFromDic();
    }
  }

  /**
 * 指定されたクラスのフィールドをスキャンします。
 * <p>
 * リフレクションを用いて、クラスが持つフィールドを動的に検査し、
 * 特定の型（Listsなど）であれば、追加の初期化処理（SetFromDic）を呼び出します。
 * </p>
 * @param cls スキャン対象のクラス
 */
public void scanClass(Class<?> cls) {
  Field[] fields = cls.getDeclaredFields();
  for (Field field: fields) {
    try {
      field.setAccessible(true);
      Object val = field.get(this);
      if (val == null) {
        // 何もしない
      } else if (val instanceof Lists) {
        // Lists型ならデータ設定処理を実行
        ((Lists)val).SetFromDic();
      } else if (val instanceof List) {
        // List型なら各要素に対して再帰的にスキャンを実行
        for(Object son: (List)val) {
          if (son instanceof Reflection) {
            ((Reflection)son).scanClasses();
          }
        }
      } else {
        // その他のオブジェクトでReflectionインターフェースを実装していればスキャンを実行
        if (val instanceof Reflection) {
          ((Reflection)val).scanClasses();
        }
      }

    }catch(Exception ex) {

      ex.printStackTrace();

      System.err.println(ex.toString());

    }

  }

}


}