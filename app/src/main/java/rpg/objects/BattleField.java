package rpg.objects;
import java.util.*;

import rpg.objects.BattleField.CharacterComparator;
import rpg.types.AttackType;
import rpg.types.StatusType;
import rpg.utils.*;

public class BattleField {
  private Party allyParty;
  private Party enemyParty;
  private ArrayList<Character> characters;
  private ArrayList<Integer> agility;
  private ArrayList<Integer> agilityCounter;
  private int timer;
  private int enemyAveExperience;

  public void addAveExperience() {
    allyParty.addAveExperience(enemyAveExperience);
  }

  public class CharacterComparator implements Comparator<Character> {
    @Override
    public int compare(Character p1, Character p2) {
      int agility1 = p1.charStatus.getStatues().get(StatusType.Agility.id)*p1.charStatus.getStatuesRate().get(StatusType.Agility.id);
      int agility2 = p2.charStatus.getStatues().get(StatusType.Agility.id)*p2.charStatus.getStatuesRate().get(StatusType.Agility.id);
      return agility1 > agility2 ? -1 : 1;
    }
  }


  BattleField(Party allyParty, Party enemyParty) {
    this.allyParty = allyParty;
    this.enemyParty = enemyParty;
    initialize();
  }
  BattleField(Party allyParty, Character enemyCharacter) {
    this(allyParty, new Party(enemyCharacter));
  }

  private void initialize() {
    this.characters = new ArrayList<Character>();
    this.characters.addAll((ArrayList<Character>)this.allyParty.characters.getList());
    this.characters.addAll((ArrayList<Character>)this.enemyParty.characters.getList());
    Collections.sort(this.characters, new CharacterComparator());
    this.agility = new ArrayList<Integer>(this.characters.size());
    this.agilityCounter = new ArrayList<Integer>(this.characters.size());
    int max = this.characters.get(0).charStatus.getStatues().get(StatusType.Agility.id)*this.characters.get(0).charStatus.getStatuesRate().get(StatusType.Agility.id);
    this.timer = 0;
    for (int i=0; i<this.characters.size(); i++) {
      Character character = this.characters.get(i);
      int one = character.charStatus.getStatues().get(StatusType.Agility.id)*character.charStatus.getStatuesRate().get(StatusType.Agility.id);
      this.agility.add(max/one);
      this.agilityCounter.add(1);
    }
    this.enemyAveExperience = enemyParty.getTotalExperience()/allyParty.size();
  }

  private boolean isAllyCharacter(Character character) {
    return allyParty.characters.getList().contains(character);
  }
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

  private boolean removeFighter(Character character) {
    boolean isEnamy = character.type.isEnemyCharacter();
    if (isEnamy) {
      ArrayList<Character> list = (ArrayList<Character>)this.enemyParty.characters.getList();
      list.remove(character);
      if (list.size()==0) {
        return false; //End Fight
      }
    }
    for (int index=0; index<this.characters.size(); index++) {
      Character one = this.characters.get(index);
      if (one == character) {
        this.characters.remove(index);
        this.agility.remove(index);
        this.agilityCounter.remove(index);
        break;
      }
    }
    return true; //continue Fight
  }


  public boolean start(Scanner scan) {
    ArrayList<Character> garded_charcters = new ArrayList<Character>();
    boolean isLoop = true;
    while(isLoop) {
      Scanner scanner;
      Character attacker = getNextFighter();
      if (attacker == this.allyParty.characters.getList().get(0)) {
        scanner = scan;
      }else{
        scanner = null; //自動ランダム選択
      }
      System.err.println();
      Anser anser = attacker.selectAttack(scanner);
      Attack attack = (Attack)anser.object;
      if (garded_charcters.contains(attacker)) {
        attacker.charStatus.doGard(false);
      }
      if (attack==null) {
        attacker.charStatus.doGard(true);
        garded_charcters.add(attacker);
      }else if (attack.isHeal()) {
        Party reciverParty = isAllyCharacter(attacker) ? allyParty:enemyParty;
        if (attack.isMulti()) {
          for(Character reciver: (ArrayList<Character>)reciverParty.characters.getList()) {
            anser = reciver.haveAttack(attack, attacker);
            System.out.println(anser.printing);
          }
        }else{
          anser = reciverParty.selectCharacter(scanner, false);
          Character reciver = (Character)anser.object;
          anser = reciver.haveAttack(attack, attacker);
          System.out.println(anser.printing);
        }
      }else if (attack.isOffence()) {
        Party reciverParty = isAllyCharacter(attacker) ? enemyParty:allyParty;
        if (attack.isMulti()) {
          for(Character reciver: (ArrayList<Character>)reciverParty.characters.getList()) {
            anser = reciver.haveAttack(attack, attacker);
            System.out.println(anser.printing);
            int reciverHp = (int)anser.object;
            if (reciverHp==0) {
              System.out.println(reciver.getName()+"のHPがなくなり倒れました！");
              boolean isEnamy = reciver.type.isEnemyCharacter();
              if (!removeFighter(reciver)) {
                this.addAveExperience();
                System.err.println();
                System.out.println(isEnamy ? "敵を全て倒しました！":"仲間が全員やられました！");
                isLoop = false;
                break;
              }
            }
          }
        }else{
          anser = reciverParty.selectCharacter(scanner, false);
          Character reciver = (Character)anser.object;
          anser = reciver.haveAttack(attack, attacker);
          System.out.println(anser.printing);
          int reciverHp = (int)anser.object;
          if (reciverHp==0) {
            System.out.println(reciver.getName()+"のHPがなくなり倒れました！");
            boolean isEnamy = reciver.type.isEnemyCharacter();
            if (!removeFighter(reciver)) {
              this.addAveExperience();
              System.err.println();
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
