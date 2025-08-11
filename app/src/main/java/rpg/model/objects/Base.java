package rpg.model.objects;

import java.lang.reflect.Field;
import java.util.*;
import rpg.utils.*;

/**
 * RPGゲームの基本オブジェクトを表す抽象クラス。
 * <p>
 * このクラスは、ゲーム内の基本的なオブジェクト（キャラクター、アイテムなど）の
 * 共通のプロパティとメソッドを定義します。リフレクションを用いて、オブジェクトの
 * フィールドを動的にスキャンし、関連付けなどを行います。
 * </p>
 */
public abstract class Base implements Cloneable, Reflection{
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
   * オブジェクトの名前を設定します。
   * <p>
   * オブジェクトの名前は、ゲーム内での識別や表示に使用されます。
   * </p>
   * @param name オブジェクトの名前
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

  public String toString() {
    return this.name;
  }

  /**
   * オブジェクトのクローンを作成します。
   * <p>
   * このメソッドは、オブジェクトの名前と位置（座標）をコピーして新しいBaseオブジェクトを返します。
   * </p>
   * @return 新しいBaseオブジェクトのクローン
   */
  public Base clone() {
    Base copy = null;
    try {
      copy = (Base)super.clone();
      copy.name = this.name != null ? this.name:null;
      copy.pt = this.pt!=null ? this.pt.clone():null;
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
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
   * オブジェクトのクラス階層をスキャンします。
   * <p>
   * リフレクションを用いて、オブジェクトが持つフィールドを動的に検査し、
   * 特定の型（Listsなど）であれば、追加の初期化処理（SetFromDic）を呼び出します。
   * これにより、JSONファイル内ではIDでしか無かった関連が、実際のオブジェクト参照に解決されます。
   * </p>
   */
  public void scanClasses() {
    // このオブジェクトのクラスを取得
    // rpg.objectsパッケージに属するクラスであれば、親クラスを遡ってスキャンを続ける
    // もしオブジェクトがListsのインスタンスであれば、辞書からのデータ設定処理
    // を実行します。これにより、JSONファイル内ではIDでしか無かった関連が
    // 実際のオブジェクト参照に解決されます。
    // もしオブジェクトがReflectionインターフェースを実装している場合、
    // scanClassesメソッドを呼び出します。
    // これにより、オブジェクトのフィールドをスキャンし、
    // 特定の型（Listsなど）であれば、追加の初期化処理（SetFromDic）を呼び出します。
    // これにより、
    // JSONファイル内ではIDでしか無かった関連が、実際のオbジェクト参照に解決されます。
    // これにより、オブジェクトのフィールドを動的に検査し、
    // 特定の型（Listsなど）であれば、追加の初期化処理（SetFromDic）を呼び出します。
    // これに
    Class<?> cls = this.getClass();
    while(cls != null && cls.getPackageName() == "rpg.model.objects") {
      // rpg.objectsパッケージに属するクラスであれば、フィールドをスキャン
      this.scanClass(cls);
      cls = cls.getSuperclass();
    }
    // もしこのオブジェクトがListsのインスタンスであれば、辞書からのデータ設定処理を実行
    // これにより、JSONファイル内ではIDでしか無かった関連が
    // 実際のオブジェクト参照に解決されます。
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
    // クラスが持つすべてのフィールドを取得
    Field[] fields = cls.getDeclaredFields();
    for (Field field: fields) {
      // フィールドがLists型なら、辞書からのデータ設定処理を実行
      // もしフィールドがList型なら、そのリスト内の各要素に
      // 対して再帰的にスキャンを実行します。
      // その他のオブジェクトなら、そのオブジェクトに対して再帰的に
      // スキャンを実行します。
      // これにより、JSONファイル内ではIDでしか無かった関連が
      // 実際のオブジェクト参照に解決されます。
      // もしフィールドがReflectionインターフェースを実装している場合
      // scanClassesメソッドを呼び出します。
      try {
        //if (!field.getDeclaredAnnotations().equals("rpg.objects.Characters")) {
        //  continue;
        //}
        Object val = field.get(this);
        if (val == null) {
        }else if (val instanceof Lists) {
          ((Lists)val).SetFromDic();
        }else if (val instanceof List) {
          for(Object son: (List)val) {
            if (son instanceof Reflection) {
              ((Reflection)son).scanClasses();
            }
          }
        }else{
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
