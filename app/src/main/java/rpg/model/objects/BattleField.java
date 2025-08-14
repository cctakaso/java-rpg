package rpg.model.objects;
import java.util.*;

import rpg.App;
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
   * 戦闘フィールドのコンストラクタ。
   * <p>
   * 味方パーティと敵パーティを指定して、戦闘フィールドを初期化します。
   * </p>
   * @param allyParty 味方パーティ
   * @param enemyParty 敵パーティ
   */
  public BattleField(Party allyParty, Party enemyParty) {
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
  public BattleField(Party allyParty, Character enemyCharacter) {
    this(allyParty, new Party(enemyCharacter));
  }

  /**
   * 戦闘フィールドの内部状態を初期化します。
   * <p>
   * キャラクターリストの構築、敏捷性に基づく行動順序の計算、タイマーの初期化などを行います。
   * </p>
   */
  private void initialize() {
    this.characters = buildCharacters();
    this.agility = new ArrayList<Integer>(this.characters.size());
    this.agilityCounter = new ArrayList<Integer>(this.characters.size());
    int max = this.characters.get(0).charStatus.getConditions().get(ConditionType.Agility.id)*this.characters.get(0).charStatus.getConditionsRate().get(ConditionType.Agility.id);
    this.timer = 0;

    for (int i=0; i<this.characters.size(); i++) {
      // 各キャラクターの敏捷性に基づいて行動順序を計算し、設定
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
   * 指定されたキャラクターが味方パーティに属しているかどうかを判定します。
   * @param character 判定対象のキャラクター
   * @return 味方パーティに属していればtrue、そうでなければfalse
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
   private Character getNextFighter() {
    int index = -1;
    int delta = 10000;
    // 敏捷性に基づいて次のキャラクターを決定し、タイマーと行動回数を更新
    for (int i=0; i<this.agility.size(); i++) {
      int time = this.agility.get(i)*this.agilityCounter.get(i);
      if (time >= this.timer && time - this.timer < delta) {
        delta = time - this.timer;
        index = i;
      }
    }
    this.timer = this.agility.get(index)*this.agilityCounter.get(index);
    this.agilityCounter.set(index, this.agilityCounter.get(index)+1);
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
    for (int index=0; index<this.characters.size(); index++) {
      Character one = this.characters.get(index);
      if (one == character) {
        this.characters.remove(index);
        this.agility.remove(index);
        this.agilityCounter.remove(index);
        break;
      }
    }
    return isOneParyGone();
  }

  /**
   * いずれかのパーティのキャラクターが全て倒れたかどうかを判定します。
   * @return いずれかのパーティのキャラクターが全て倒れた場合はtrue、そうでなければfalse
   */
  private boolean isOneParyGone() {
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
    ArrayList<Character> protected_charcters = new ArrayList<Character>();
    boolean isLoop = true;
    while(isLoop) {
      Scanner scan;
      Character attacker = getNextFighter();
      if (attacker == this.allyParty.characters.getList().get(0)) {
        scan = scanner;
      }else{
        scan = null; //自動ランダム選択
      }
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
        App.view.printMessage(anser.getLabel());
      }else if (attack.isHeal()) {
        Party reciverParty = isAllyCharacter(attacker) ? allyParty:enemyParty;
        if (attack.isAffectParty()) {
          for(Character reciver: (ArrayList<Character>)reciverParty.characters.getList()) {
            anser = reciver.haveAttack(attack, attacker);
            App.view.printMessage(anser.getLabel());
          }
        }else{
          App.view.printMessage(attacker.getName()+"が、"+attack.getName()+"を使用します。");
          anser = reciverParty.selectCharacter(scan, false);
          Character reciver = (Character)anser.getValue();
          anser = reciver.haveAttack(attack, attacker);
          App.view.printMessage(anser.getLabel());
        }
      }else if (attack.isOffence()) {
        Party reciverParty = isAllyCharacter(attacker) ? enemyParty:allyParty;
        if (attack.isAffectParty()) {
          for(Character reciver: (ArrayList<Character>)reciverParty.characters.getList()) {
            anser = reciver.haveAttack(attack, attacker);
            App.view.printMessage(anser.getLabel());
            int reciverHp = (int)anser.getValue();
            if (reciverHp==0) {
              App.view.printMessage(reciver.getName()+"のHPがなくなり倒れました！");
              if (reciver.getName().equals("勇者")) {
                App.view.printMessage("勇者が倒れたため、ゲームオーバーです。");
                return false; //ゲームオーバー
              }
              if (removeFighter(reciver)) {
                this.addAveExperience();
                App.view.printMessage();
                boolean isEnamy = reciver.type.isEnemyCharacter();
                App.view.printMessage(isEnamy ? "敵を全て倒しました！":"仲間が全員やられました！");
                isLoop = false;
                break;
              }
            }
          }
        }else{
          anser = reciverParty.selectCharacter(scan, false);
          Character reciver = (Character)anser.getValue();
          anser = reciver.haveAttack(attack, attacker);
          App.view.printMessage(anser.getLabel());
          int reciverHp = (int)anser.getValue();
          if (reciverHp==0) {
            App.view.printMessage(reciver.getName()+"のHPがなくなり倒れました！");
            if (reciver.getName().equals("勇者")) {
              App.view.printMessage("勇者が倒れたため、ゲームオーバーです。");
              return false; //ゲームオーバー
            }
            if (removeFighter(reciver)) {
              this.addAveExperience();
              App.view.printMessage();
              boolean isEnamy = reciver.type.isEnemyCharacter();
              App.view.printMessage(isEnamy ? "敵を全て倒しました！":"仲間が全員やられました！");
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