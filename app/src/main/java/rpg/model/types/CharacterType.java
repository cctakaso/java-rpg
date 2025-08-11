package rpg.model.types;

/**
 * ゲームに登場するキャラクターの種別を網羅的に定義する列挙型(Enum)。
 * <p>
 * プレイヤーが操作可能なキャラクター（Crew）、敵（Enemy）、街の人（NPC）などの
 * 大分類を、ID範囲を用いて管理しています。
 * </p>
 */
public enum CharacterType {
  // --- プレイヤー・仲間キャラクターの範囲 --- (職業やクラスに相当)
  CrewMin(1),    // 仲間キャラクターの最小ID
  Gladiator(1),  //剣術士
  Pubilist(2),   //拳闘士 (Pugilistの誤記か)
  Archer(3),     //弓使い
  Wizard(4),     //魔法使い
  Bishop(5),     //司教
  CrewMac(5),    //仲間キャラクターIDの最大値を示すマーカー

  // --- 敵キャラクターの範囲 ---
  EnemyMin(10), // 敵キャラクターの最小ID
  Alien(10),    // エイリアン
  Bigfoot(11),  // ビッグフット
  Demon(12),    // 悪魔
  Dragon(13),   // ドラゴン
  Ghost(14),    // 幽霊
  Goblin(15),   // ゴブリン
  Hydra(16),    // ヒドラ
  Medusa(17),   // メデューサ
  Mummy(18),    //ミイラ
  Ogre(19),     // オーガ
  Siren(20),    // セイレーン
  Slime(21),    // スライム
  Troll(22),    // トロール
  Vampire(23),  // 吸血鬼
  Zombie(24),   // ゾンビ
  EnemyMac(24), // 敵キャラクターIDの最大値を示すマーカー

  // --- NPC (ノンプレイヤーキャラクター) の範囲 ---
  NpcMin(30),
  Traveler(30),   //旅人
  Townspeple(31), //街の人 (Towns-peopleの誤記か)
  Merchant(32),   //商人
  Boniface(33),   //宿屋の主人
  Priest(34),     //神父
  Blacksmith(35), //鍛冶屋
  NpcMac(35);     // NpcMaxの誤記か。NPCのIDの最大値を示すマーカー

  /**
   * キャラクター種別に対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id キャラクター種別に割り当てるID
   */
  private CharacterType(int id) { // コンストラクタの定義
    this.id = id;
  }

  /**
   * このキャラクターが仲間（プレイヤー操作可能）かどうかを判定します。
   * @return 仲間であればtrue、そうでなければfalse
   */
  public boolean isCrewCharacter() {
    return CrewMin.id <= this.id && this.id <= CrewMac.id;
  }

  /**
   * このキャラクターが敵かどうかを判定します。
   * @return 敵であればtrue、そうでなければfalse
   */
  public boolean isEnemyCharacter() {
    return EnemyMin.id <= this.id && this.id <= EnemyMac.id;
  }

  /**
   * このキャラクターがNPC（ノンプレイヤーキャラクター）かどうかを判定します。
   * @return NPCであればtrue、そうでなければfalse
   */
  public boolean isNpcCharacter() {
    return NpcMin.id <= this.id && this.id <= NpcMac.id;
  }
}
