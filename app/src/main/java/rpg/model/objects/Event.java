package rpg.model.objects;

import java.util.*;

import rpg.model.types.*;
import rpg.utils.*;

/**
 * イベントを表すクラス。
 * <p>
 * イベントは、ゲーム内で発生する特定のアクションや状態を表し、
 * イベントの種類（EventType）と関連する情報を保持します。
 * </p>
 */
public class Event {
  private EventType type; // イベントの種類
  private Fields field;   // イベントが発生したフィールド（マップ）
  private Items items;    // イベントが発生したアイテムのリスト
  private Characters characters;  // イベントが発生したキャラクターのリスト
  private Parties parties;  // イベントが発生したパーティのリスト

  private Pt orginPt; // イベントが発生した元の座標
  /**
   * デフォルトコンストラクタ。
   * <p>
   * イベントの種類を未設定にし、関連する情報を初期化します。
   * </p>
   */
  public EventType getType() {
    return this.type;
  }

  /**
   * イベントが発生したフィールドを取得します。
   * @return イベントが発生したフィールド
   */
  public Fields getField() {
    return this.field;
  }

  /**
   * イベントが発生したフィールドを設定します。
   * @param field イベントが発生したフィールド
   */
  public Items getItems() {
    return this.items;
  }

  /**
   * イベントが発生したキャラクターのリストを取得します。
   * @return イベントが発生したキャラクターのリスト
   */
  public Characters getCharacters() {
    return this.characters;
  }

  /**
   * イベントが発生したパーティのリストを取得します。
   * @return イベントが発生したパーティのリスト
   */
  public Parties getParties() {
    return this.parties;
  }


  /**
   * イベントがフィールドを変更するイベントを作成します。
   * <p>
   * フィールドの変更を伴うイベントを生成します。
   * </p>
   * @param map イベントの詳細情報を含むマップ
   * @return 新しいEventオブジェクト
   */
  public static Event newEventChangeField(Map<String, Object> map) {
    Event one = new Event();
    one.type = EventType.ChangeField;
    one.orginPt = (Pt)map.get("orginPt");
    one.field = (Fields)map.get("hitField");
    return one;
  }

  /**
   * アイテムをヒットするイベントを作成します。
   * <p>
   * 指定されたアイテムに関連するイベントを生成します。
   * </p>
   * @param items ヒットするアイテムのリスト
   * @return 新しいEventオブジェクト
   */
  public static Event newEventHitItems(Items items) {
    Event one = new Event();
    one.type = EventType.HitItems;
    one.items = items;
    return one;
  }

  /**
   * キャラクターをヒットするイベントを作成します。
   * <p>
   * 指定されたキャラクターに関連するイベントを生成します。
   * </p>
   * @param characters ヒットするキャラクターのリスト
   * @return 新しいEventオブジェクト
   */
  public static Event newEventHitCharacters(Characters characters) {
    Event one = new Event();
    one.type = EventType.HitCharacters;
    one.characters = characters;
    return one;
  }

  /**
   * 敵パーティーをヒットするイベントを作成します。
   * <p>
   * 指定された敵パーティーに関連するイベントを生成します。
   * </p>
   * @param parties ヒットする敵パーティーのリスト
   * @return 新しいEventオブジェクト
   */
  public static Event newEventHitParties(Parties parties) {
    Event one = new Event();
    one.type = EventType.HitParties;
    one.parties = parties;
    return one;
  }
}
