package rpg.utils;
import java.lang.reflect.Field;
import java.util.*;
import rpg.objects.Lists;
import rpg.objects.Base;

/**
 * ゲームリソースの読み込みと初期化を管理するクラス。
 * <p>
 * 主に、JSONファイルから読み込んだデータを、ゲーム内で利用可能なオブジェクトとして
 * 構築（ビルド）する役割を担います。リフレクションを用いて、オブジェクト間の関連付けなどを
 * 動的に行っていると考えられます。
 * </p>
 */
public class Resource {

  /**
   * 指定されたパスからリソース（JSONファイル群）を読み込み、データ辞書（Dic）を初期化します。
   * @param dic 初期化対象のデータ辞書オブジェクト
   * @param pathName リソースが格納されているフォルダのパス
   */
  public static void load(Dic dic, String pathName) {
    // 1. Jsonユーティリティを使って、指定パスのJSONファイルを読み込み、dicに移す
    Json.getMapFromResouceJsons(dic, pathName);
    // 2. 読み込んだデータ間の関連付けなどを解決する
    buildScan(dic);
    System.out.println("Dic.load end");
  }

  //-----------------------------------------//

  /**
   * 辞書内のすべてのオブジェクトをスキャンし、ビルド処理（関連付けなど）を実行します。
   * @param dic 対象となるデータ辞書
   */
  static void buildScan(HashMap<String, HashMap<String, Base>> dic) {
    // 辞書の第一階層（"Characters", "Items"など）をループ
    for(String type: dic.keySet()) {
      HashMap<String, Base> objs = dic.get(type);
      if (objs == null) {
        continue;
      }
      // 辞書の第二階層（"スライム", "やくそう"など）をループ
      for(String key: objs.keySet()) {
        Base obj = objs.get(key);
        if (obj == null) {
          continue;
        }
        // Baseクラスを継承したオブジェクトであれば、scanClassesメソッドを呼び出す
        if (obj instanceof Base) {
          ((Base)obj).scanClasses();
        }
      }
    }
  }

  /**
   * 指定されたオブジェクトのクラス階層を遡りながら、各クラスのフィールドをスキャンします。
   * <p>
   * リフレクションを用いて、オブジェクトが持つフィールドを動的に検査し、
   * 特定の型（Listsなど）であれば、追加の初期化処理（SetFromDic）を呼び出します。
   * これにより、JSONファイル内ではIDでしか無かった関連が、実際のオブジェクト参照に解決されます。
   * </p>
   * @param obj スキャン対象のオブジェクト
   */
  static void scanClasses(Object obj) {
    Class<?> cls = obj.getClass();
    // rpg.objectsパッケージに属するクラスである限り、親クラスを遡ってスキャンを続ける
    while(cls != null && cls.getPackageName() == "rpg.objects") {
      scanClass(obj, cls);
      cls = cls.getSuperclass();
    }
    // もしオブジェクトがListsのインスタンスであれば、辞書からのデータ設定処理を実行
    if (obj instanceof Lists) {
      ((Lists)obj).SetFromDic();
    }
  }

  /**
   * 指定されたオブジェクトとクラスについて、フィールドを一つずつスキャンする内部メソッド。
   * @param obj スキャン対象のオブジェクト
   * @param cls スキャン対象のクラス
   */
  static void scanClass(Object obj, Class<?> cls) {
    // クラスが持つすべてのフィールドを取得
    Field[] fields = cls.getDeclaredFields();
    for (Field field: fields) {
      try {
        // リフレクションでフィールドの値を取得
        Object val = field.get(obj);
        if (val == null) {
          // 何もしない
        }else if (val instanceof Lists) {
          // フィールドがLists型なら、辞書からのデータ設定処理を実行
          ((Lists)val).SetFromDic();
        }else if (val instanceof List) {
          // フィールドがList型なら、そのリスト内の各要素に対して再帰的にスキャンを実行
          for(Object son: (List)val) {
            scanClasses(son);
          }
        }else{
          // その他のオブジェクトなら、そのオブジェクトに対して再帰的にスキャンを実行
          scanClasses(val);
        }
      }catch(Exception ex) {
        ex.printStackTrace();
      }
    }
  }

}
