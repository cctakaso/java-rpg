package rpg.controller.console;

import java.util.*;

import rpg.Adventure;
import rpg.App;
import rpg.model.objects.*;
import rpg.model.types.*;
import rpg.model.objects.Character;
import rpg.utils.*;

/**
 * コンソールベースのRPGゲームにおけるユーザー入力とゲームロジックの制御を担当するクラス。
 * <p>
 * ユーザーからのコマンドを受け取り、ゲームの状態を更新し、適切なビューに表示を指示します。
 * </p>
 */
public class Controller {
  private static Controller singleton = new Controller();
  private Adventure adventure;  // ゲームの冒険情報を保持する

  /**
   * シングルトンインスタンスを取得するメソッド。
   * @return コントローラーのインスタンス
   */
  public static Controller get(){
      return singleton;
  }

  public void initialize(Adventure adventure) {
      this.adventure = adventure; // ゲームの冒険情報を設定
  }


  /**
   * ゲームを開始するメソッド。
   * <p>
   * ユーザーからの入力を受け取り、ゲームの状態を更新し、ビューに表示を指示します。
   * </p>
   */
  public void startGame() {
    // ゲーム開始のロジック
    App.view.printMessage("lets_start!");
    try (Scanner scan = new Scanner(System.in)) {
      App.view.printMessage("start_adventure", adventure.getTitle());

      boolean isGaming = true; // ゲームが進行中かどうかのフラグ

      // ゲームループを開始
      while (isGaming) {
        // ゲームのメインループ。プレイヤーが終了を選択するまで続く。
        App.view.printMessage();
        // 現在位置を含めてマップ全体を描画
        App.view.whereYouGoing();

        // プレイヤーからの移動入力を待つ
        // プレイヤーの座標を更新
        adventure.getParty().setPt(walk(scan));

        // もし移動先がnullなら、プレイヤーがゲーム終了を選択したと判断
        if (adventure.getParty().getPt() == null) {
          break; // ループを抜ける
        }

        // 現在のフィールドと座標で発生するイベントを取得
        List<Event> events = getEvents(scan);

        if (events != null) {
          for (Event event: events) {
            App.view.printMessage();
            // イベントを実行し、もしイベントが消費されるタイプならマップから削除
            if (handleEvent(scan, event)) {
              adventure.getNowField().removeEvent(adventure.getParty().getPt(), adventure.getNowPt(), event);
            }else {
              isGaming = false; // ゲーム終了フラグを立てる
              break; // イベント処理を中断
            }
          }
        }
      }

      //handleInput(input);
      //updateGameState();
    }

    App.view.printMessage("end_adventure_message");
  }

  /**
   * イベントを処理します。
   * @param scanner ユーザー入力用のScanner
   * @param event 発生したイベント
   * @return イベント処理が正常に終了した場合はtrue
   */
  public boolean handleEvent(Scanner scanner, Event event) {
    int size;
    switch (event.getType()) {
      case EventType.ChangeField:
        App.view.printMessage("entered_field", event.getField());
        break;
      case EventType.HitItems:
        size = event.getItems().size();
        for(int index=size-1; index>=0; index--) {
          Item item = (Item)event.getItems().getList().get(index);
          App.view.printMessage("found_item", item);
          App.view.printMessage("choice_pickup_or_discard");
          while(true) {
            try{
              System.out.print(adventure.getParty().getLeaderName()+">");
              int num = scanner.nextInt();
              if (num == 1) {
                adventure.getParty().addItem(item);
                App.view.printMessage(adventure.getParty().getItems().toString());
                break;
              }else if (num==0) {
                break;
              }
            }catch (InputMismatchException ex){
              scanner.nextLine(); // 入力バッファをクリア
            } catch (NoSuchElementException e) {
              App.view.printMessage("no_input_try_again");
              //scanner.next(); // 無効な入力をスキップ
              System.exit(-1);
            }catch(Exception ex) {
              ex.printStackTrace();
              System.err.println(ex.toString());
              System.exit(-1);
            }
            App.view.printMessage("enter_correct_number");
          }
        }
        break;
      case EventType.HitCharacters:
        if (event.getCharacters().size()>0) {
          Character character = (Character)event.getCharacters().getList().get(0);
          return doCharacterEvent(scanner, character);
        }
        break;
      case EventType.HitParties:
        size = event.getParties().size();
        for(int index=size-1; index>=0; index--) {
          Party party = (Party)event.getParties().getList().get(index);
          if (!doCharacterEvent(scanner, party)) {
            return false; // ゲームオーバー
          }
        }
        break;
      default:
        break;
    }

    return true;
  }

  private List<Event> getEvents(Scanner scan) {
    // 現在のフィールドを取得
    getField(scan);
    // 指定されたフィールドと座標で発生するイベントを取得
    return adventure.getNowField().getHitEvents(adventure.getParty(), adventure.getParty().getPt(), adventure.getNowPt());
  }


  /**
   * 他のパーティとの遭遇イベントを処理します。
   * @param scanner ユーザー入力用のScanner
   * @param party 遭遇したパーティ
   * @return イベント処理が正常に終了した場合はtrue
   */
  private boolean doCharacterEvent(Scanner scanner, Party party) {
    App.view.printMessage("party_appeared", party);
    for (Character character: (ArrayList<Character>)party.getCharactersList()) {
      App.view.printMessage(character.toString());
      App.view.printMessage(character.getCharStatus().toString());
    }
    Character xcharacter = new EnemyCharacter(((ArrayList<Character>)party.getCharactersList()).get(0));
    // 敵パーティの場合、戦闘を開始
    if (party.isEnemyParty()) {
      if (xcharacter.meet(scanner, adventure.getParty())) {
        BattleField battleField = new BattleField(adventure.getParty(), party);
        return battleField.start(scanner);
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
    if (character.getType().isCrewCharacter()) {
      xcharacter = new CrewCharacter(character);
      xcharacter.meet(scanner, adventure.getParty());
    }else if (character.getType().isEnemyCharacter()) {
      xcharacter = new EnemyCharacter(character);
      App.view.printMessage("character_appeared", xcharacter);
      App.view.printMessage(adventure.getParty().getCharStatus().toString());
      if (xcharacter.meet(scanner, adventure.getParty())) {
        BattleField battleField = new BattleField(adventure.getParty(), xcharacter);
        return battleField.start(scanner);
      }
    }else if (character.getType().isNpcCharacter()) {
      xcharacter = new NonPlayerCharacter(character);
      xcharacter.meet(scanner, adventure.getParty());
    }else{
      throw new IllegalArgumentException("Unknown character type: " + character.getType());
    }
    return true;
  }

  private void getField(Scanner scan) {
    // プレイヤーの現在座標がどのフィールドに属しているか判定
    Map<String, Object> hitField = adventure.getFields().hitField(adventure.getParty().getPt());
    Fields tmpField = (Fields)hitField.get("hitField");

    // 現在座標で発生するイベントを取得・実行
    adventure.setNowPt((Pt)hitField.get("hitPt"));

    // もし、前のターンと違うフィールドに侵入した場合
    if (tmpField != adventure.getNowField()) {
      // 前のフィールドに離脱時のイベントがあれば実行
      if (adventure.getNowField() != null) {
        ArrayList<Talk> talks = (ArrayList<Talk>)adventure.getNowField().getTalks().getList();
        if (talks!=null && talks.size()>0) {
          talks.get(0).printAfter(scan);
        }
      }
      adventure.setNowField(tmpField); // 現在のフィールドを更新
      // 新しいフィールドに侵入時のイベントがあれば実行
      ArrayList<Talk> talks = (ArrayList<Talk>)adventure.getNowField().getTalks().getList();
      if (talks!=null && talks.size()>0) {
        talks.get(0).printBefore(scan);
      }
    }

    // フィールドの情報を表示
    App.view.printMessage((String)hitField.get("toString"));
  }


  /**
   * パーティの移動処理を行います。
   * @param scan ユーザー入力用のScanner
   * @return 移動後の座標。移動しない場合はnull。
   */
  private Pt walk(Scanner scan) {
    Pt parentPt = adventure.getFields().getPt();
    Size parentSize = adventure.getFields().getSize();

    Pt pt;
    Defs.Key key;
    App.view.printMessage("where_to_go");
    while(true) {
      App.view.printMessage("directions_choice");
      while(true) {
        try{
          System.out.print(adventure.getParty().getLeaderName()+">");
          int num = scan.nextInt();
          key = Defs.getDirection(num);
          if (key != Defs.Key.Error) {
            break;
          }
        }catch (InputMismatchException ex){
          scan.nextLine(); // 入力バッファをクリア
        } catch (NoSuchElementException e) {
          App.view.printMessage("no_input_try_again");
          //scan.next(); // 無効な入力をスキップ
          System.exit(-1);
        }catch(Exception ex) {
          ex.printStackTrace();
          System.err.println(ex.toString());
          System.exit(-1);
        }
        App.view.printMessage("enter_correct_number");
      }
      pt = adventure.getParty().getPt().clone().moveKey(key);
      // 移動先が範囲内かチェック
      if (!parentSize.isWithinArea(parentPt, pt)) {
        App.view.printMessage("out_of_range");
        continue;
      }
      if (key == Defs.Key.End) {
        return null;
      }
      break;
    }

    return pt;
  }

}
