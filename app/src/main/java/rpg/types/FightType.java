package rpg.types;

public enum FightType {
  Attack(0),      //戦う
  Magic(1),      //魔法を唱える
  Defence(2),    //防御する
  UseTools(3),   //道具を使う
  Escape(4);      //逃げる

  public int id; // フィールドの定義
  private FightType(int id) { // コンストラクタの定義
    this.id = id;
  }
}
