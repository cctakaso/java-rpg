package rpg.model.objects;
import java.util.*;

import rpg.model.types.ConditionType;
import rpg.utils.*;

/**
 * 戦闘フィールドを表すクラス。
 * <p>
 * 戦闘フィールドは、味方パーティと敵パーティのキャラクターを管理し、
 * 戦闘の進行やキャラクターの行動を制御します。
 * </p>
 */
public class BattleField {
  protected Party allyParty;  // 味方パーティ
  protected Party enemyParty; // 敵パーティ
  protected ArrayList<Character> characters;  // 戦闘に参加するキャラクターのリスト
  protected ArrayList<Integer> agility;       // 各キャラクターの敏捷性に基づく行動順序
  protected ArrayList<Integer> agilityCounter;  // 各キャラクターの行動回数カウンター
  protected int timer;          // 戦闘のタイマー
  protected int enemyAveExperience; // 敵の平均経験値

  /**
   * 味方パーティの平均経験値を敵パーティに追加します。
   * <p>
   * 戦闘終了後、味方パーティの平均経験値を敵パーティに加算します。
   * </p>
   */
  public void addAveExperience() {
    allyParty.addAveExperience(enemyAveExperience);
  }

  /**
   * キャラクターの比較を行うコンパレータ。
   * <p>
   * 敏捷性（Agility）に基づいてキャラクターを比較し、戦闘の行動順序を決定します。
   * </p>
   */
  public class CharacterComparator implements Comparator<Character> {
    @Override
    public int compare(Character p1, Character p2) {
      int agility1 = p1.charStatus.getConditions().get(ConditionType.Agility.id)*p1.charStatus.getConditionsRate().get(ConditionType.Agility.id);
      int agility2 = p2.charStatus.getConditions().get(ConditionType.Agility.id)*p2.charStatus.getConditionsRate().get(ConditionType.Agility.id);
      return agility1 > agility2 ? -1 : 1;
    }
  }

  /**
   * 戦闘フィールドのコンストラクタ。
   * <p>
   * 味方パーティと敵パーティを指定して、戦闘フィールドを初期化します。
   * </p>
   * @param allyParty 味方パーティ
   * @param enemyParty 敵パーティ
   */
  BattleField(Party allyParty, Party enemyParty) {
    this.allyParty = allyParty;
    this.enemyParty = enemyParty;
    initialize();
  }

  /**
   * 戦闘フィールドのコンストラクタ。
   * <p>
   * 味方パーティと敵キャラクターを指定して、戦闘フィールドを初期化します。
   * 敵キャラクターは敵パーティに追加されます。
   * </p>
   * @param allyParty 味方パーティ
   * @param enemyCharacter 敵キャラクター
   */
  BattleField(Party allyParty, Character enemyCharacter) {
    this(allyParty, new Party(enemyCharacter));
  }

  /**
   * 戦闘フィールドのコンストラクタ。
   * <p>
   * 味方パーティと敵キャラクターのリストを指定して、戦闘フィールドを初期化します。
   * 敵キャラクターは敵パーティに追加されます。
   * </p>
   * @param allyParty 味方パーティ
   * @param enemyCharacters 敵キャラクターのリスト
   */
  private void initialize() {
    this.characters = buildCharacters();
    this.agility = new ArrayList<Integer>(this.characters.size());
    this.agilityCounter = new ArrayList<Integer>(this.characters.size());
    int max = this.characters.get(0).charStatus.getConditions().get(ConditionType.Agility.id)*this.characters.get(0).charStatus.getConditionsRate().get(ConditionType.Agility.id);
    this.timer = 0;

    for (int i=0; i<this.characters.size(); i++) {
      // 各キャラクターの敏捷性に基づいて行動順序を計算
      // 敏捷性の逆数を計算し、行動順序を設定
      // 敏捷性が高いほど、行動順序が早くなるように設定
      // 敏捷性の最大値を基準に、行動順序を計算
      // 敏捷性の逆数を計算し、行動順序を設定
      // 敏捷性が高いほど、行動順序が早くなるように設定
      // 敏捷性の最大値
      Character character = this.characters.get(i);
      int one = character.charStatus.getConditions().get(ConditionType.Agility.id)*character.charStatus.getConditionsRate().get(ConditionType.Agility.id);
      this.agility.add(max/one);
      this.agilityCounter.add(1);
    }
    this.enemyAveExperience = enemyParty.getTotalExperience()/allyParty.size();
  }

  /**
   * 戦闘フィールドに参加するキャラクターのリストを取得します。
   * <p>
   * 戦闘フィールドに参加しているキャラクターのリストを返します。
   * </p>
   * @return 戦闘フィールドに参加するキャラクターのリスト
   */
  private ArrayList<Character> buildCharacters() {
    // 戦闘フィールドに参加するキャラクターを設定
    ArrayList<Character> characters = new ArrayList<Character>();
    ArrayList<Character> list = (ArrayList<Character>)this.allyParty.characters.getList();
    for(Character character: list) {
      //if (character.charStatus.getConditionsRate().get(ConditionType.HealthPoint.id) > 0) {
      if (character.getHealthPoint()>0){
        characters.add(character);
      }
    }
    list = (ArrayList<Character>)this.enemyParty.characters.getList();
    for(Character character: list) {
      //if (character.charStatus.getConditionsRate().get(ConditionType.HealthPoint.id) > 0) {
      if (character.getHealthPoint()>0){
        characters.add(character);
      }
    }
    Collections.sort(characters, new CharacterComparator());
    return characters;
  }



  /**
   * 戦闘フィールドに参加するキャラクターのリストを取得します。
   * <p>
   * 戦闘に参加するキャラクターのリストを返します。
   * </p>
   * @return 戦闘に参加するキャラクターのリスト
   */
  private boolean isAllyCharacter(Character character) {
    return allyParty.characters.getList().contains(character);
  }

  /**
   * 次の行動を行うキャラクターを取得します。
   * <p>
   * 敏捷性に基づいて、次に行動するキャラクターを決定します。
   * </p>
   * @return 次に行動するキャラクター
   */
  /*
  10, 15, 40, 45, 70
  700/10, 700/15, 700/40, 700/45, 700/70
  70, 46, 17, 15, 10
   0   0   0   0   0
   0   0   0   0   1
   0   0   0   1   1
   0   0   1   1   1
   0   0   1   1   2
   0   0   1   2   2
          1    2   2

  10  15  17  20  30
  10->15->17->10->15
  */
   private Character getNextFighter() {
    int index = -1;
    int delta = 10000;
    // 敏捷性に基づいて次のキャラクターを決定
    // 敏捷性の逆数を計算し、最小の値を持つキャラクターを選択
    // 敏捷性が高いほど、行動順序が早くなるように設定
    // 敏捷性の最大値を基準に、行動順序を計算
    // 敏捷性の逆数を計算し、最小の値を持つキャラクターを選択
    // 敏捷性が高いほど、行動順序が早くなるように設定
    // 敏捷性の最大値を基準に、行動順序を計算
    // 敏捷性の逆数を計算し, 最小の値を持つキャラクターを選択
    // 敏捷性が高いほど、行動順序が早くなるように設定
    // 敏捷性の最大値を基準に、行動順序を計算
    for (int i=0; i<this.agility.size(); i++) {
      int time = this.agility.get(i)*this.agilityCounter.get(i);
      if (time >= this.timer && time - this.timer < delta) {
        delta = time - this.timer;
        index = i;
      }
    }
    // 次のキャラクターを取得し、タイマーを更新
    this.timer = this.agility.get(index)*this.agilityCounter.get(index);
    // 行動回数を更新
    this.agilityCounter.set(index, this.agilityCounter.get(index)+1);
    // 次のキャラクターを返す
    return this.characters.get(index);
  }

  /**
   * 指定されたキャラクターを戦闘フィールドから削除します。
   * <p>
   * キャラクターが倒れた場合、戦闘フィールドから削除し、
   * 戦闘が続行可能かどうかを返します。
   * </p>
   * @param character 削除するキャラクター
   * @return 戦闘が続行可能であればtrue、そうでなければfalse
   */
  private boolean removeFighter(Character character) {
    // キャラクターが倒れた場合、戦闘フィールドから削除
    // 戦闘が続行可能かどうかを返す

    /*
    boolean isEnamy = character.type.isEnemyCharacter();

    if (isEnamy) {
      // 敵キャラクターが倒れた場合、敵パーティから削除
      // 敵パーティのキャラクターリストから削除
      ArrayList<Character> list = (ArrayList<Character>)this.enemyParty.characters.getList();
      list.remove(character);
      if (list.size()==0) {
        return false; //End Fight
      }
    }
    */
    // キャラクターが倒れた場合、パーティから削除
    for (int index=0; index<this.characters.size(); index++) {
      Character one = this.characters.get(index);
      if (one == character) {
        this.characters.remove(index);
        this.agility.remove(index);
        this.agilityCounter.remove(index);
        break;
      }
    }
    return isOneParyGone(); //continue Fight
  }

  private boolean isOneParyGone() {
    // 戦闘フィールドに参加しているパーティのキャラクターが全て倒れたかどうかを確認
    // 味方パーティのキャラクターが全て倒れた場合、trueを返す
    // 敵パーティのキャラクターが全て倒れた場合、falseを返す
    int allyCount = 0;
    int enemyCount = 0;
    for (int index=0; index<this.characters.size(); index++) {
      Character one = this.characters.get(index);
      if (one.type.isEnemyCharacter()) {
        enemyCount++;
      }else{
        allyCount++;
      }
    }
    return allyCount==0 || enemyCount==0;
  }

  /**
   * 戦闘を開始します。
   * <p>
   * 戦闘を開始し、キャラクターの行動を順次処理します。
   * </p>
   * @param scanner 入力を受け付けるスキャナー
   * @return 戦闘が終了した場合はtrue、それ以外はfalse
   */
  public boolean start(Scanner scanner) {
    // 戦闘を開始し、キャラクターの行動を順次処理
    // キャラクターの行動を順次処理
    ArrayList<Character> protected_charcters = new ArrayList<Character>();
    boolean isLoop = true;
    while(isLoop) {
      // 次のキャラクターを取得
      // 次のキャラクターを取得し、行動を選択
      Scanner scan;
      Character attacker = getNextFighter();
      if (attacker == this.allyParty.characters.getList().get(0)) {
        scan = scanner;
      }else{
        scan = null; //自動ランダム選択
      }
      //System.err.println();
      Answer<?> anser = attacker.selectAttack(scan);
      Attack attack = (Attack)anser.getValue();
      if (protected_charcters.contains(attacker)) {
        attacker.charStatus.doProtect(null);
        protected_charcters.remove(attacker);
      }
      if (attack.isPhysicalDefence()) {
        attacker.charStatus.doProtect(attack);
        protected_charcters.add(attacker);
        anser = attacker.haveAttack(attack, attacker);
        System.out.println(anser.getLabel());
      }else if (attack.isHeal()) {
        // 攻撃が回復系の場合、味方キャラクターを回復
        Party reciverParty = isAllyCharacter(attacker) ? allyParty:enemyParty;
        if (attack.isAffectParty()) {
          for(Character reciver: (ArrayList<Character>)reciverParty.characters.getList()) {
            anser = reciver.haveAttack(attack, attacker);
            System.out.println(anser.getLabel());
          }
        }else{
          // 単体攻撃の場合、回復対象のキャラクターを選択
          // 敵キャラクターを選択する場合、敵パーティから選択
          // 味方キャラクターを選択する場合、味方パーティから選択
          // 回復対象のキャラクターを選択し、回復処理を実行
          System.out.println(attacker.getName()+"が、"+attack.getName()+"を使用します。");
          anser = reciverParty.selectCharacter(scan, false);
          Character reciver = (Character)anser.getValue();
          anser = reciver.haveAttack(attack, attacker);
          System.out.println(anser.getLabel());
        }
      }else if (attack.isOffence()) {
        // 攻撃が攻撃系の場合、敵キャラクターを攻撃
        // 攻撃対象のキャラクターを選択
        // 敵キャラクターを選択する場合、敵パーティから選択
        // 味方キャラクターを選択する場合、味方パーティから選択
        // 攻撃対象のキャラクターを選択し、攻撃処理を実行
        Party reciverParty = isAllyCharacter(attacker) ? enemyParty:allyParty;
        if (attack.isAffectParty()) {
          for(Character reciver: (ArrayList<Character>)reciverParty.characters.getList()) {
            anser = reciver.haveAttack(attack, attacker);
            System.out.println(anser.getLabel());
            int reciverHp = (int)anser.getValue();
            if (reciverHp==0) {
              System.out.println(reciver.getName()+"のHPがなくなり倒れました！");
              if (reciver.getName().equals("勇者")) {
                System.out.println("勇者が倒れたため、ゲームオーバーです。");
                return false; //ゲームオーバー
              }
              if (removeFighter(reciver)) {
                this.addAveExperience();
                System.err.println();
                boolean isEnamy = reciver.type.isEnemyCharacter();
                System.out.println(isEnamy ? "敵を全て倒しました！":"仲間が全員やられました！");
                isLoop = false;
                break;
              }
            }
          }
        }else{
          anser = reciverParty.selectCharacter(scan, false);
          Character reciver = (Character)anser.getValue();
          anser = reciver.haveAttack(attack, attacker);
          System.out.println(anser.getLabel());
          int reciverHp = (int)anser.getValue();
          if (reciverHp==0) {
            System.out.println(reciver.getName()+"のHPがなくなり倒れました！");
            if (reciver.getName().equals("勇者")) {
              System.out.println("勇者が倒れたため、ゲームオーバーです。");
              return false; //ゲームオーバー
            }
            if (removeFighter(reciver)) {
              this.addAveExperience();
              System.err.println();
              boolean isEnamy = reciver.type.isEnemyCharacter();
              System.out.println(isEnamy ? "敵を全て倒しました！":"仲間が全員やられました！");
              isLoop = false;
              break;
            }
          }
        }
      }
    }
    return true;
  }

}
