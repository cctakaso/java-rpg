package rpg;

import java.util.*;

import rpg.model.objects.*;
import rpg.utils.*;

/**
 * ゲーム全体の冒険の流れを管理するクラス。
 * <p>
 * ゲームの初期化、メインループの制御、プレイヤーのコマンドに応じた処理の分岐など、
 * ゲームセッション全体のライフサイクルを担当します。
 * MVCパターンにおけるコントローラーのような役割も担います。
 * </p>
 */
public class Adventure {
  // ゲーム全体で共有されるデータ辞書（キャラクター、アイテム等のマスターデータ）
  static Dic dic;
  // 現在の冒険で使われるマップデータ
  static Dic map;

  /**
   * ゲームのデータ辞書（キャラクター、アイテムなど）を初期化します。
   * @param path マップデータのパス
   */
  public static void initialize(String path) {
    dic = new Dic();
    map = new Dic();
    // マスターデータを読み込む
    Resource.load(dic, "/dic");
    // 指定されたシナリオのマップデータを読み込む
    Resource.load(map, path);
    System.out.println("initialize end");
  }

  /**
   * データ辞書から、指定されたタイプのオブジェクトのクローンを取得します。
   * @param type オブジェクトのタイプ（例: "Characters", "Items"）
   * @param name オブジェクトの名前
   * @return 生成されたオブジェクトのリスト
   */
  public static Base getDicClone(String type, String name) {
    return dic.getClone(type, name);
  }

  /**
   * データ辞書から、指定されたタイプのオブジェクトのクローンを取得します。
   * @param type オブジェクトのタイプ（例: "Characters", "Items"）
   * @param name オブジェクトの名前
   * @return 生成されたオブジェクトのリスト
   */
  public static List<Base> getDicClones(String type, String name) {
    return getDicClones(type, name, 0, null);
  }

  /**
   * データ辞書から、指定されたタイプのオブジェクトのクローンを複数取得します。
   * @param type オブジェクトのタイプ
   * @param name オブジェクトの名前
   * @param number 生成する数
   * @param randomPt ランダムな座標（オプション）
   * @return 生成されたオブジェクトのリスト
   */
  public static List<Base> getDicClones(String type, String name, int number, Pt randomPt) {
    return dic.getClones(type, name, number, randomPt);
  }

  /**
   * ゲームのメインループを開始します。
   * <p>
   * マップの表示、プレイヤーの移動、イベントの発生と処理など、
   * ゲームの主要なサイクルを制御します。
   * </p>
   * @param title ゲームのタイトル
   */
  @SuppressWarnings("unchecked")
  public static void start(String title) {
    System.out.println(title+"を始めます。");

    // マップデータからプレイヤーのパーティーとフィールド情報を取得
    Party myParty = (Party)map.get("Parties", "勇者パーティ");
    Fields allFields = (Fields)map.get("Fields", "全体マップ");

    Fields myField = null; // プレイヤーが現在いるフィールド


    //Scanner scan = new Scanner(System.in);
    try (Scanner scan = new Scanner(System.in)) {
      // ゲームのメインループ。プレイヤーが終了を選択するまで続く。
      do {
        System.out.println();
        // 現在位置を含めてマップ全体を描画
        allFields.toString(myParty);

        // プレイヤーの現在座標がどのフィールドに属しているか判定
        Map<String, Object> hitResult = allFields.hitField(myParty.getPt());
        Fields tmpField = (Fields)hitResult.get("hitField");

        // もし、前のターンと違うフィールドに侵入した場合
        if (tmpField != myField) {
          // 前のフィールドに離脱時のイベントがあれば実行
          if (myField != null) {
            ArrayList<Talk> talks = (ArrayList<Talk>)myField.getTalks().getList();
            if (talks!=null && talks.size()>0) {
              talks.get(0).printAfter(scan);
            }
          }
          myField = tmpField; // 現在のフィールドを更新
          // 新しいフィールドに侵入時のイベントがあれば実行
          ArrayList<Talk> talks = (ArrayList<Talk>)myField.getTalks().getList();
          if (talks!=null && talks.size()>0) {
            talks.get(0).printBefore(scan);
          }
        }

        // フィールドの情報を表示
        System.out.println((String)hitResult.get("toString"));

        // 現在座標で発生するイベントを取得・実行
        Pt orginPt = (Pt)hitResult.get("orginPt");
        List<Event> events = myField.getHitEvents(myParty, myParty.getPt(), orginPt);
        if (events != null) {
          for (Event event: events) {
            System.out.println();
            // イベントを実行し、もしイベントが消費されるタイプならマップから削除
            if (myParty.event(scan, event)) {
              myField.removeEvent(myParty.getPt(), orginPt, event);
            }
          }
        }

        // プレイヤーのステータスを表示
        System.out.println();
        System.out.println(myParty.toString(true));

        // プレイヤーからの移動入力を待つ
        Pt pt = myParty.walk(scan, allFields.getPt(), allFields.getSize());

        // もし移動先がnullなら、プレイヤーがゲーム終了を選択したと判断
        if (pt == null) {
          System.out.println("冒険を終了します。");
          break; // ループを抜ける
        }
        // プレイヤーの座標を更新
        myParty.setPt(pt);
      } while(true);
    }
  }
}