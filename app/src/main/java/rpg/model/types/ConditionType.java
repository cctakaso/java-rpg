package rpg.model.types;

/**
 * ステータスの種類を定義する列挙型(Enum)。
 */
public enum ConditionType {
  Money(0),         //お金
  Weight(1),        //重量
  HealthPoint(2),   //健康ポイント
  MagicPoint(3),    //魔法ポイント
  //Strength(1),       //筋力
  //Durability(3),     //耐久性
  Attack(4),        //(ATK)：攻撃力
  Defence(5),       //(DEF)：防御力
  MagicAttack(6),   //(MATK)：魔法攻撃力
  MagicDefence(7),  //(MDEF)：魔法防御力
  Agility(8),       //(AGI）：敏捷性
  //  Judgment(5),      //判断力
  //  Intelligence(4),  //知力
  //  Charm(6);      //魅力・運
  Dexterity(9),     //(DEX）：命中率
  Evansion(10),     //EVA）：回避率

  //  Charm(6);        //魅力・運
  Critical(11),     //クリティカル

  Poisoned(12),     //毒状態
  Blind(13),        //盲目状態
  Asleep(14),       //睡眠状態
  Confused(15),     //混乱状態
  Stunned(16),      //気絶状態
  Paralyzed(17),    //麻痺状態
  Charmed(18),      //魅了状態
  Stoned(19),       //石化状態
  Silenced(20);     //沈黙状態

  /**
   * フィールドタイプに対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id フィールドタイプに割り当てるID
   */
  private ConditionType(int id) { // コンストラクタの定義
    this.id = id;
  }

  /**
   * ステータスの種類をIDから取得します。
   * @param id ステータスのID
   * @return 対応するConditionType
   */
  public static ConditionType valueOf(int id) {
    ConditionType[] values = ConditionType.values();
    return values[id];
  }
}
