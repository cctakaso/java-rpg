package rpg.objects;
import java.util.*;

import rpg.objects.*;
import rpg.types.*;
import rpg.utils.*;

public class Party extends Base{
  protected Characters characters;
  protected ArrayList<Integer> numbers;
  protected Items items;
  protected CharStatus charStatus;
  protected Talks talks;

  public Party(Character character) {
    this();
    addCharacter(character);
  }

  public Party() {
    super();
    this.characters = new Characters();
    this.numbers = new ArrayList<Integer>();
    this.items = new Items();
    this.charStatus = new CharStatus();
    this.talks = new Talks();
  }

  public int size() {
    return this.characters.getList().size();
  }
  public void addItem(Item item) {
    if (item.isMoney()) {
      this.charStatus.addMoney(item.getStatuses().getPoint(StatusType.Money));
    }else{
      this.items.add(item);
    }
  }

  public void addCharacter(Character character) {
    this.characters.add(character);
    this.charStatus.merge(character.charStatus);
    this.items.transfer(character.items);
  }

  public Party clone() {
    return this.clone(0, null);
  }

  public Party clone(int num, Pt randomPt) {
    Party copy = null;
    try {
      copy = (Party)super.clone(num, randomPt);
      copy.characters = (Characters)this.characters.clone();
      copy.numbers = (ArrayList<Integer>)numbers.clone();
      copy.items = (Items)this.items.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public String getLeaderName() {
    return ((Base)this.characters.getList().get(0)).getName();
  }

  public String toPrinting(boolean isDetail) {
    String strCharaString = "";
    if (isDetail) {
      for (Character one: (ArrayList<Character>)this.characters.getList()) {
        strCharaString += one.toPrinting(true);
      }
    }
    return this.name+"("+this.pt.x+","+this.pt.y+")" +
    "Money:"+this.charStatus.getMoney() + (isDetail ? "\nメンバー：\n" + strCharaString:"");
  }

  public Anser selectCharacter(Scanner scan, boolean excludeLord) {
    if (this.characters.getList().size()==1) {
      return new Anser(null, this.characters.getList().get(0));
    }
    Ansers ansers = this.characters.toAnsers(excludeLord);
    return ansers.printChoice(scan, null, false);
  }

  public boolean event(Scanner scan, Event event) {
    ArrayList<?> removeList;
    int size;
    switch (event.getType()) {
      case EventType.ChangeField:
        System.out.println(event.getField().name+"に入りました。");
        break;
      case EventType.HitItems:
        size = event.getItems().size();
        for(int index=size-1; index>=0; index--) {
          Item item = (Item)event.getItems().getList().get(index);
          System.out.println(item.name+"を見つけました。");
          System.out.println("1:拾う  0:捨てる");
          while(true) {
            try{
              System.out.print(this.getLeaderName()+">");
              int num = scan.nextInt();
              if (num == 1) {
                addItem(item);
                System.out.println(this.items.toPrinting());
                break;
              }else if (num==0) {
                break;
              }
            }catch (Exception e){}
            System.out.println("正しい番号を入力して下さい");
          }
        }
        break;
      case EventType.HitCharacters:
        size = event.getCharacters().size();
        for(int index=size-1; index>=0; index--) {
          Character character = (Character)event.getCharacters().getList().get(index);
          doCharacterEvent(scan, character);
          System.out.println();
        }
        break;
      case EventType.HitParties:
        size = event.getParties().size();
        for(int index=size-1; index>=0; index--) {
          Party party = (Party)event.getParties().getList().get(index);
          doCharacterEvent(scan, party);
          System.out.println();
        }
        break;
      default:
        break;
    }

    return true;
  }

  public boolean isEnemyParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    // TODO:リーダーが敵なら敵パーティとしておく
    Character character = this.characters.children.get(0);
    return character.type.isEnemyCharacter();
  }

  public boolean isCrewParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    Character character = this.characters.children.get(0);
    return character.type.isCrewCharacter();
  }

  public boolean isNpcParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    Character character = this.characters.children.get(0);
    return character.type.isNpcCharacter();
  }

  private boolean doCharacterEvent(Scanner scan, Party party) {
    System.out.println("パーティ："+party.name+"が現れました。");
    for (Character character: (ArrayList<Character>)party.characters.getList()) {
      System.out.println(character.toPrinting());
      System.out.println(character.charStatus.toPrinting());
    }
    Character xcharacter = new EnemyCharacter(((ArrayList<Character>)party.characters.getList()).get(0));
    if (party.isEnemyParty()) {
      if (xcharacter.meet(scan, this)) {
        BattleField battleField = new BattleField(this, party);
        battleField.start(scan);
      }
    }else{
      return doCharacterEvent(scan, xcharacter);
    }
    return true;
  }

  private boolean doCharacterEvent(Scanner scan, Character character) {
    Character xcharacter;
    if (character.type.isCrewCharacter()) {
      xcharacter = new CrewCharacter(character);
      xcharacter.meet(scan, this);
    }else if (character.type.isEnemyCharacter()) {
      xcharacter = new EnemyCharacter(character);
      System.out.println(xcharacter.name+"が現れました。");
      System.out.println(this.charStatus.toPrinting());
      if (xcharacter.meet(scan, this)) {
        BattleField battleField = new BattleField(this, xcharacter);
        battleField.start(scan);
      }
    }else if (character.type.isNpcCharacter()) {
      xcharacter = new NonPlayerCharacter(character);
      xcharacter.meet(scan, this);
    }else{
      return false;
    }
    return true;
  }

  public Pt walk(Scanner scan, Pt parentPt, Size parentSize) {
    Pt pt;
    Defs.Key key;
    System.out.println("どちらへ行きますか？");
    while(true) {
      System.out.println("4:西  8:北  6:東  2:南  0:終了");
      while(true) {
        try{
          System.out.print(this.getLeaderName()+">");
          int num = scan.nextInt();
          key = Defs.getDirection(num);
          if (key != Defs.Key.Error) {
            break;
          }
        }catch(Exception ex) {
          ex.printStackTrace();
          System.err.println(ex.toString());
        }
        System.out.println("正しい番号を入力して下さい");
      }
      pt = this.pt.clone().moveKey(key);
      if (!parentSize.isWithinArea(parentPt, pt)) {
        System.out.println("範囲外です。");
        continue;
      }
      if (key == Defs.Key.End) {
        return null;
      }
      break;
    }

    return pt;
  }

  public int getTotalExperience() {
    int ex = 0;
    ArrayList<Character>lst = (ArrayList<Character>)this.characters.getList();
    for (Character one: lst) {
      ex += one.getTotalExpericence();
    }
    return ex;
  }

  public void addAveExperience(int ex) {
    for (Character one: (ArrayList<Character>)this.characters.getList()) {
      one.addExperience(ex);
    }
  }
}
