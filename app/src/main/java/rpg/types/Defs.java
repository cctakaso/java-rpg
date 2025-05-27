package rpg.types;

public class Defs {
  public enum Key {
    West(4),
    North(8),
    East(6),
    South(2),
    End(0),
    Error(-1);
    public int id; // フィールドの定義
    private Key(int id) { // コンストラクタの定義
      this.id = id;
    }
  };

  public static Key getDirection(int num) {
    if (num == Key.West.id) {
      return Key.West;
    }else if (num == Key.West.id) {
      return Key.West;
    }else if (num == Key.North.id) {
      return Key.North;
    }else if (num == Key.East.id) {
      return Key.East;
    }else if (num == Key.South.id) {
      return Key.South;
    }else if (num == Key.End.id) {
      return Key.End;
    }
    return Key.Error;
  }

}
