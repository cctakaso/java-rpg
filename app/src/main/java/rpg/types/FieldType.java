package rpg.types;

public enum FieldType {
  Town(1),
  City(2),
  Prairie(3), //草原
  Mountain(4),
  Cave(5),
  Mix(6),
  max(7);



  public int id; // フィールドの定義
  private FieldType(int id) { // コンストラクタの定義
    this.id = id;
  }
}
