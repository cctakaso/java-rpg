package rpg.types;

public enum EventType {
  ChangeField(0),
  HitItems(1),
  HitCharacters(2),
  HitParties(3);

  public int id; // フィールドの定義
  private EventType(int id) { // コンストラクタの定義
    this.id = id;
  }
}
