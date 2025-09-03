package rpg.utils;
import java.util.*;

import rpg.model.objects.Base;

/**
 * ゲーム内の様々なデータ（マスターデータ）を管理するための辞書クラス。
 * <p>
 * {@code HashMap<String, HashMap<String, Base>>} を継承しており、
 * 「データの種類（Type）」と「データ名（Name）」の二段階で、
 * ゲームオブジェクトの元となるインスタンス（プロトタイプ）を管理します。
 * 例: `dic.get("Characters", "スライム")` のようにして、スライムの元データにアクセスします。
 * </p>
 */
public class Dic extends HashMap<String, HashMap<String, Base>>{

  /**
   * 指定されたパスからリソースを読み込み、辞書を初期化します。
   * <p>
   * このコンストラクタは、特定のパスからゲームのマスターデータを読み込むために使用されます。
   * </p>
   * @param path 読み込むリソースのパス
   */
  public void load(String path) {
    Resource.load(this, path);
  }

  /**
   * 辞書から指定されたタイプの特定の名前を持つオブジェクト（プロトタイプ）を取得し、
   * そのクローンを生成します。
   * <p>
   * このメソッドは、指定されたタイプと名前に基づいて、辞書からオブジェクトを取得し、
   * そのクローンを生成して返します。
   * </p>
   * @param type オブジェクトの種類
   * @param name オブジェクトの名前
   * @return 生成されたBaseオブジェクトのクローン。見つからない場合はnull。
   */
  public Base getClone(String type, String name) {
    try {
      // まず、辞書から元となるオブジェクト（プロトタイプ）を取得
      Base one = get(type, name);
      if (one != null) {
        // クローン生成前に、必要なクラス情報をスキャン（リフレクションの準備）
        one.scanClasses();
        // 指定された数だけクローンを生成し、リストに追加
        return one.clone();
      }
    }catch(Exception ex) {
      // エラーが発生した場合はスタックトレースを出力
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * 辞書に登録されているオブジェクトのクローン（複製）を生成します。
   * <p>
   * ゲーム内で実際に使用されるキャラクターやアイテムは、
   * このメソッドを通じて辞書のマスターデータから複製されて生成されます。
   * これにより、マスターデータを汚さずに、個別の状態を持つオブジェクトを安全に利用できます。
   * </p>
   * @param type 取得したいオブジェクトの種類（例: "Characters", "Items"）
   * @param name 取得したいオブジェクトの名前（例: "スライム"）
   * @param number 生成するクローンの数
   * @param randomPt クローンに設定するランダムな座標（オプション）
   * @return 生成されたクローンのリスト。見つからない場合はnull。
   */
  public List<Base> getClones(String type, String name, int number, Pt randomPt) {
    // デバッグ用のブレークポイント
    //if (type == "Parties") {
    //  App.view.printMessage();
    //}
    List<Base> list = new ArrayList<Base>();
    try {
      // まず、辞書から元となるオブジェクト（プロトタイプ）を取得
      Base one = get(type, name);
      if (one != null) {
        // クローン生成前に、必要なクラス情報をスキャン（リフレクションの準備）
        one.scanClasses();
        // 指定された数だけクローンを生成し、リストに追加
        do {
          list.add(one.clone(number, randomPt));
        }while(--number >0);
        return list;
      }
    }catch(Exception ex) {
      // エラーが発生した場合はスタックトレースを出力
      ex.printStackTrace();
    }
    return null;
  }


  //-----------------------------------------//
  /**
   * 辞書から指定されたタイプの特定の名前を持つオブジェクト（プロトタイプ）を取得します。
   * @param type オブジェクトの種類
   * @param name オブジェクトの名前
   * @return 見つかったBaseオブジェクト。見つからない場合はnull。
   */
  public Base get(String type, String name) {
    try {
      // 内部的には、HashMapのgetを2回呼び出している
      // 1. 最初のget(type)で、その種類のHashMapを取得
      // 2. 次のget(name)で、目的のBaseオブジェクトを取得
      return this.get(type).get(name);
    }catch(Exception ex) {
      // オブジェクトが見つからない場合などに発生する例外を握りつぶし、スタックトレースを出力
      ex.printStackTrace();
    }
    return null;
  }

}
