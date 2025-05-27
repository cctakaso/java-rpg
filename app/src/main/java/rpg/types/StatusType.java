package rpg.types;

import javax.swing.GroupLayout.Alignment;

public enum StatusType {
  Money(0),
  Weight(1),
  HealthPoint(2),   //健康ポイント
  MagicPoint(3),   //魔法ポイント
  //Strength(1),      //筋力
  //Durability(3),    //耐久性
  Attack(4),     //(ATK)：攻撃力
  Defense(5),    //(DEF)：防御力
  MagicAttack(6),//(MATK)：魔法攻撃力
  MagicDefense(7),//(MDEF)：魔法防御力

  //
  Agility(8),     //(AGI）：敏捷性

  //  Judgment(5),      //判断力
  //  Intelligence(4),  //知力
  //  Charm(6);      //魅力・運
  Dexterity(9),   //(DEX）：命中率
  Evansion(10),    //EVA）：回避率

  //  Charm(6);      //魅力・運
  Critical(11),    //クリティカル

  Poisoned(12),
  Blind(13),
  Asleep(14),
  Confused(15),
  Stunned(16),    //気絶
  Paralyzed(17),  //麻痺
  Charmed(18),
  Stoned(19),
  Silenced(20);

  public int id; // フィールドの定義
  private StatusType(int id) { // コンストラクタの定義
    this.id = id;
  }
  public static StatusType valueOf(int id) {
    StatusType[] values = StatusType.values();
    return values[id];
  }
}
