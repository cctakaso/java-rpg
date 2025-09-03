package rpg;

import java.util.*;
import java.io.File;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;

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
  // ゲーム・リソースバンドル
  public static ResourceBundle resouce = ResourceBundle.getBundle("game");
  // メッセージ・リソースバンドル
  public static ResourceBundle messages;

  // ゲーム全体で共有されるデータ辞書（キャラクター、アイテム等のマスターデータ）
  static Dic dic = new Dic();
  // 現在の冒険で使われるマップデータ
  static Dic map = new Dic();
  private String title; // 冒険のタイトル
  private Fields fields; // ゲームのフィールド（マップ）を保持する
  private Party party; // ゲームのパーティ情報を保持する
  private Fields nowField; // プレイヤーが現在いるフィールド
  private Pt nowPt; // イベントが発生した元の座標

  /**
   * ゲームのデータ辞書（キャラクター、アイテムなど）を初期化します。
   * @param path マップデータのパス
   */
  public Adventure() {
    // メッセージリソースバンドルの設定
    String language = Adventure.resouce.getString("language");
    try {
        Path messages_path = Path.of(App.class.getClassLoader().getResource("messages").toURI());
        URL urls[] = new URL[]{messages_path.toUri().toURL()};
        URLClassLoader messagesLoader = new URLClassLoader(urls);
        Locale locale = Locale.of(language);
        Adventure.messages = ResourceBundle.getBundle("message", locale, messagesLoader);
    } catch (Exception e) {
        System.err.println("設定ディレクトリのURLが不正です: " + e.getMessage());
        return;
    }

    setTitle(Adventure.messages.getString("title")); // 冒険のタイトルを設定
    dic.load(Adventure.resouce.getString("dictionary")); // データ辞書を初期化
    map.load(Adventure.resouce.getString("path")); // データ辞書を初期化

    // マップデータからプレイヤーのパーティーとフィールド情報を取得
    setFields((Fields)map.get("Fields", "adventure map"));
    setParty((Party)map.get("Parties", "Hero Party"));
  }

  /**
   * 冒険のタイトルを取得します。
   * @return 冒険のタイトル
   */
  public String getTitle() {
    return title;
  }

  /**
   * ゲーム全体のフィールドを取得します
   * @return フィールド
   */
  public Fields getFields() {
    return fields;
  }

  /**
   * ゲームの主役パーティを取得します
   * @return 主役パーティ
   */
  public Party getParty() {
    return party;
  }

  /**
   * 現在フィールドを取得します。
   * @return 現在のフィールド
   */
  public Fields getNowField() {
    return nowField; // 現在のフィールドを取得
  }

  /**
   * 現在の座標を取得します。
   * @return 現在の座標
   */
  public Pt getNowPt() {
    return nowPt; // 現在の座標を取得
  }

  /**
   * 冒険のタイトルを設定します。
   * @param title 冒険のタイトル
   */
  public void setTitle(String title) {
    this.title = title; // 冒険のタイトルを設定
  }

  /**
   * ゲーム全体のフィールドを設定します
   * @param fields フィールド
   */
  public void setFields(Fields fields) {
    this.fields = fields; // 現在のフィールドを設定
  }


  /**
   * ゲームの主役パーティを設定します
   * @param 主役パーティ
   */
  public void setParty(Party party) {
    this.party = party; // 主役パーティを設定
  }

  /**
   * 現在のフィールドを設定します。
   * @param nowField 現在のフィールド
   */
  public void setNowField(Fields nowField) {
    this.nowField = nowField; // 現在のフィールドを設定
  }

  /**
   * 現在の座標を設定します。
   * @param nowPt 現在の座標
   */
  public void setNowPt(Pt nowPt) {
    this.nowPt = nowPt; // 現在の座標を設定
  }

  /**
   * データ辞書から、指定されたタイプのオブジェクトのクローンを単体で取得します。
   * @param type オブジェクトのタイプ（例: "Characters", "Items"）
   * @param name オブジェクトの名前
   * @return 生成されたオブジェクト
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
}