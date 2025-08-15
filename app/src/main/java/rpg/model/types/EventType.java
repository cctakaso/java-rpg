package rpg.model.types;

/**
 * マップ上で発生するイベントの種類を定義する列挙型(Enum)。
 */
public enum EventType {
  ChangeField(0),   // フィールドの変更イベント
  HitItems(1),      // アイテムの発見イベント
  HitCharacters(2), // キャラクターの遭遇イベント
  HitParties(3);    // 敵パーティーの遭遇イベント

  /**
   * イベントタイプに対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id イベントタイプに割り当てるID
   */
  private EventType(int id) { // コンストラクタの定義
    this.id = id;
  }
}
