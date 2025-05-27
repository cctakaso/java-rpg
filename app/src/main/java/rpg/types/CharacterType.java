package rpg.types;

public enum CharacterType {
  CrewMin(1),
  Gladiator(1),  //剣術士
  Pubilist(2),   //拳闘士
  Archer(3),     //弓使い
  Wizard(4),     //魔法使い
  Bishop(5),     //司教
  CrewMac(5),

  EnemyMin(10),
  Alien(10),
  Bigfoot(11),
  Demon(12),
  Dragon(13),
  Ghost(14),
  Goblin(15),
  Hydra(16),
  Medusa(17),
  Mummy(18),  //ミイラ
  Ogre(19),
  Siren(20),
  Slime(21),
  Troll(22),
  Vampire(23),
  Zombie(24),
  EnemyMac(24),

  //NonPlayerCharacter(),
  NpcMin(30),
  Traveler(30),
  Townspeple(31),
  Merchant(32), //商人
  Boniface(33), //宿屋の主人
  Priest(34), //神父
  Blacksmith(35), //鍛冶屋
  NpcMac(35);



  public int id; // フィールドの定義
  private CharacterType(int id) { // コンストラクタの定義
    this.id = id;
  }

  public boolean isCrewCharacter() {
    return CrewMin.id <= this.id && this.id <= CrewMac.id;
  }
  public boolean isEnemyCharacter() {
    return EnemyMin.id <= this.id && this.id <= EnemyMac.id;
  }
  public boolean isNpcCharacter() {
    return NpcMin.id <= this.id && this.id <= NpcMac.id;
  }
}
