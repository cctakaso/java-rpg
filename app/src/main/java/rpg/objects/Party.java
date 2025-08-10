package rpg.objects;
import java.util.*;

import rpg.types.*;
import rpg.utils.*;

/**
 * キャラクターの集まりである「パーティ」を表すクラスです。
 * パーティ全体の所持品、所持金、キャラクターの管理、およびイベント処理を担当します。
 */
public class Party extends Base{
  /**
   * パーティに所属するキャラクターのリスト
   */
  protected Characters characters;
  /**
   * パーティが所持するアイテムのリスト
   */
  protected Items items;
  /**
   * パーティ全体のステータス（所持金など）
   */
  protected CharStatus charStatus;
  /**
   * パーティに関連する会話のリスト
   */
  protected Talks talks;

  /**
   * 指定されたキャラクターで新しいパーティを生成します。
   * @param character 初期メンバーとなるキャラクター
   */
  public Party(Character character) {
    this();
    addCharacter(character);
  }

  /**
   * 空のパーティを生成します。
   */
  public Party() {
    super();
    this.characters = new Characters();
    this.items = new Items();
    this.charStatus = new CharStatus();
    this.talks = new Talks();
  }

  /**
   * パーティの人数を返します。
   * @return パーティに所属するキャラクターの数
   */
  public int size() {
    return this.characters.getList().size();
  }

  /**
   * パーティにアイテムを追加します。
   * アイテムが通貨の場合は所持金に加算し、それ以外はアイテムリストに追加します。
   * @param item 追加するアイテム
   */
  public void addItem(Item item) {
    if (item.isMoney()) {
      this.charStatus.addMoney(item.getStatuses().getPoint(StatusType.Money));
    }else{
      this.items.add(item);
    }
  }

  /**
   * パーティに新しいキャラクターを追加します。
   * キャラクターのステータスと所持品はパーティ全体のものに統合されます。
   * @param character 追加するキャラクター
   */
  public void addCharacter(Character character) {
    this.characters.add(character);
    this.charStatus.merge(character.charStatus);
    this.items.transfer(character.items);
  }

  /**
   * このパーティのクローンを作成します。
   * @return パーティの新しいインスタンス
   */
  public Party clone() {
    return this.clone(0, null);
  }

  /**
   * このパーティのクローンを、指定された番号とランダムな位置で作成します。
   * @param num クローン番号
   * @param randomPt ランダムな位置
   * @return パーティの新しいインスタンス
   */
  @SuppressWarnings("unchecked")
  public Party clone(int num, Pt randomPt) {
    Party copy = null;
    try {
      copy = (Party)super.clone(num, randomPt);
      copy.characters = (Characters)this.characters.clone();
      copy.items = (Items)this.items.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * パーティのリーダーの名前を取得します。
   * @return リーダーのキャラクター名
   */
  public String getLeaderName() {
    return ((Base)this.characters.getList().get(0)).getName();
  }

  /**
   * パーティの情報を文字列として返します。
   * @param isDetail 詳細情報を表示するかどうか
   * @return フォーマットされたパーティ情報
   */
  @SuppressWarnings("unchecked")
  public String toString(boolean isDetail) {
    String strCharaString = "";
    if (isDetail) {
      for (Character one: (ArrayList<Character>)this.characters.getList()) {
        strCharaString += one.toString(true);
      }
    }
    return this.name+"("+this.pt.x+","+this.pt.y+")" +
    " Money:"+this.charStatus.getMoney() + "\nItems:"+this.items.toString() + (isDetail ? "\nメンバー：\n" + strCharaString:"");
  }

  /**
   * パーティ内のキャラクターを選択させます。
   * @param scanner ユーザー入力用のScanner
   * @param excludeLord リーダーを除外するかどうか
   * @return 選択されたキャラクターを含むAnswerオブジェクト
   */
  public Answer<Character> selectCharacter(Scanner scanner, boolean excludeLord) {
    if (this.characters.getList().size()==1) {
      return new Answer<Character>(null, (Character)this.characters.getList().get(0));
    }
    Answers<Character> ansers = this.characters.toAnswers(excludeLord);
    return ansers.printChoice(scanner, null, false);
  }

  /**
   * イベントを処理します。
   * @param scanner ユーザー入力用のScanner
   * @param event 発生したイベント
   * @return イベント処理が正常に終了した場合はtrue
   */
  public boolean event(Scanner scanner, Event event) {
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
              int num = scanner.nextInt();
              if (num == 1) {
                addItem(item);
                System.out.println(this.items.toString());
                break;
              }else if (num==0) {
                break;
              }
            }catch (InputMismatchException ex){
              scanner.nextLine(); // 入力バッファをクリア
            } catch (NoSuchElementException e) {
              System.out.println("入力がありません。もう一度入力してください。");
              //scanner.next(); // 無効な入力をスキップ
              System.exit(-1);
            }catch(Exception ex) {
              ex.printStackTrace();
              System.err.println(ex.toString());
              System.exit(-1);
            }
            System.out.println("正しい番号を入力して下さい");
          }
        }
        break;
      case EventType.HitCharacters:
        size = event.getCharacters().size();
        for(int index=size-1; index>=0; index--) {
          Character character = (Character)event.getCharacters().getList().get(index);
          doCharacterEvent(scanner, character);
          System.out.println();
        }
        break;
      case EventType.HitParties:
        size = event.getParties().size();
        for(int index=size-1; index>=0; index--) {
          Party party = (Party)event.getParties().getList().get(index);
          doCharacterEvent(scanner, party);
          System.out.println();
        }
        break;
      default:
        break;
    }

    return true;
  }

  /**
   * このパーティが敵パーティかどうかを判定します。
   * @return リーダーが敵キャラクターの場合はtrue
   */
  public boolean isEnemyParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    // リーダーが敵なら敵パーティとみなす
    Character character = this.characters.children.get(0);
    return character.type.isEnemyCharacter();
  }

  /**
   * このパーティが仲間パーティかどうかを判定します。
   * @return リーダーが仲間キャラクターの場合はtrue
   */
  public boolean isCrewParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    Character character = this.characters.children.get(0);
    return character.type.isCrewCharacter();
  }

  /**
   * このパーティがNPCパーティかどうかを判定します。
   * @return リーダーがNPCキャラクターの場合はtrue
   */
  public boolean isNpcParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    Character character = this.characters.children.get(0);
    return character.type.isNpcCharacter();
  }

  /**
   * 他のパーティとの遭遇イベントを処理します。
   * @param scanner ユーザー入力用のScanner
   * @param party 遭遇したパーティ
   * @return イベント処理が正常に終了した場合はtrue
   */
  @SuppressWarnings("unchecked")
  private boolean doCharacterEvent(Scanner scanner, Party party) {
    System.out.println("パーティ："+party.name+"が現れました。");
    for (Character character: (ArrayList<Character>)party.characters.getList()) {
      System.out.println(character.toString());
      System.out.println(character.charStatus.toString());
    }
    Character xcharacter = new EnemyCharacter(((ArrayList<Character>)party.characters.getList()).get(0));
    // 敵パーティの場合、戦闘を開始
    if (party.isEnemyParty()) {
      if (xcharacter.meet(scanner, this)) {
        BattleField battleField = new BattleField(this, party);
        battleField.start(scanner);
      }
    }else{
      return doCharacterEvent(scanner, xcharacter);
    }
    return true;
  }

  /**
   * 他のキャラクターとの遭遇イベントを処理します。
   * @param scanner ユーザー入力用のScanner
   * @param character 遭遇したキャラクター
   * @return イベント処理が正常に終了した場合はtrue
   */
  private boolean doCharacterEvent(Scanner scanner, Character character) {
    Character xcharacter;
    // キャラクターのタイプに応じて処理を分岐
    if (character.type.isCrewCharacter()) {
      xcharacter = new CrewCharacter(character);
      xcharacter.meet(scanner, this);
    }else if (character.type.isEnemyCharacter()) {
      xcharacter = new EnemyCharacter(character);
      System.out.println(xcharacter.name+"が現れました。");
      System.out.println(this.charStatus.toString());
      if (xcharacter.meet(scanner, this)) {
        BattleField battleField = new BattleField(this, xcharacter);
        battleField.start(scanner);
      }
    }else if (character.type.isNpcCharacter()) {
      xcharacter = new NonPlayerCharacter(character);
      xcharacter.meet(scanner, this);
    }else{
      return false;
    }
    return true;
  }

  /**
   * パーティの移動処理を行います。
   * @param scanner ユーザー入力用のScanner
   * @param parentPt 親（フィールドなど）の原点座標
   * @param parentSize 親（フィールドなど）のサイズ
   * @return 移動後の座標。移動しない場合はnull。
   */
  public Pt walk(Scanner scanner, Pt parentPt, Size parentSize) {
    Pt pt;
    Defs.Key key;
    System.out.println("どちらへ行きますか？");
    while(true) {
      System.out.println("4:西  8:北  6:東  2:南  0:終了");
      while(true) {
        try{
          System.out.print(this.getLeaderName()+">");
          int num = scanner.nextInt();
          key = Defs.getDirection(num);
          if (key != Defs.Key.Error) {
            break;
          }
        }catch (InputMismatchException ex){
          scanner.nextLine(); // 入力バッファをクリア
        } catch (NoSuchElementException e) {
          System.out.println("入力がありません。もう一度入力してください。");
          //scanner.next(); // 無効な入力をスキップ
          System.exit(-1);
        }catch(Exception ex) {
          ex.printStackTrace();
          System.err.println(ex.toString());
          System.exit(-1);
        }
        System.out.println("正しい番号を入力して下さい");
      }
      pt = this.pt.clone().moveKey(key);
      // 移動先が範囲内かチェック
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

  /**
   * パーティ全体の合計経験値を取得します。
   * @return 合計経験値
   */
  @SuppressWarnings("unchecked")
  public int getTotalExperience() {
    int ex = 0;
    ArrayList<Character>lst = (ArrayList<Character>)this.characters.getList();
    for (Character one: lst) {
      ex += one.getTotalExpericence();
    }
    return ex;
  }

  /**
   * パーティの各メンバーに均等に経験値を加算します。
   * @param ex 加算する経験値
   */
  @SuppressWarnings("unchecked")
  public void addAveExperience(int ex) {
    for (Character one: (ArrayList<Character>)this.characters.getList()) {
      one.addExperience(ex);
    }
  }
}