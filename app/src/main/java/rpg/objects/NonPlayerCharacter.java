package rpg.objects;

import java.util.*;
import java.util.regex.Pattern;
import rpg.utils.*;

/**
 * NonPlayerCharacter（NPC）を表すクラスです。
 * Characterクラスを継承し、プレイヤーとの対話や取引などの機能を提供します。
 */
public class NonPlayerCharacter extends Character{
  /**
   * 既存のCharacterオブジェクトをコピーして新しいNonPlayerCharacterインスタンスを生成します。
   * @param character コピー元のCharacterオブジェクト
   */
  public NonPlayerCharacter(Character character) {
    this();
    super.copy(character);
  }
  /**
   * デフォルトコンストラクタ
   */
  public NonPlayerCharacter() {
  }

  /**
   * プレイヤーのパーティと遭遇した際の対話処理を実行します。
   * @param scan ユーザー入力用のScannerオブジェクト
   * @param myParty プレイヤーのパーティ
   * @return 対話が正常に終了した場合はtrue
   */
  public boolean meet(Scanner scan, Party myParty) {
    Answer<?> anser;
    while(true) {
      System.out.println(this.name+":");
      // NPCとの会話を表示し、プレイヤーの応答を取得
      anser = talks.print(scan, myParty, this);
      if (anser!=null) {
        String info = anser.getInfo();
        Item item = (Item)anser.getValue();
        // アイテム購入処理
        if (info.equals(":buy.gears") || info.equals(":buy.items")) {
          if (item.getPrice() > myParty.charStatus.getMoney()) {
            System.out.println("お金が足りません："+ item.getPrice() +">"+ myParty.charStatus.getMoney());
            continue;
          }
          myParty.addItem(item);
          this.items.decItem(item, 1);
          myParty.charStatus.decMoney(item.getPrice());
        // アイテム売却処理
        }else if (info.equals(":sale.gears") || info.equals(":sale.items")) {
          this.items.add(item);
          myParty.items.decItem(item, 1);
          myParty.charStatus.addMoney(item.getPrice());
        // その他のコマンド処理
        }else if (info.indexOf(':')==0) {
          String[] split = info.split(":");
          for(String str: split) {
            if (str.length()==0) {
              continue;
            }
            String[] ssplit = str.split(Pattern.quote("."));
            if (ssplit.length == 2) {
              //支払い処理
              if (ssplit[0].equals("pay")) {
                if (ssplit[1].equals("potion")) {
                  if (myParty.items.decItem("ポーション", 1)==null) {
                    System.out.println("水（ポーション）がありません！");
                    break;
                  }
                }else{
                  int money = Util.valueOf(ssplit[1]);
                  if (money>0) { //money?
                    if (money > myParty.charStatus.getMoney()) {
                      System.out.println("お金が足りません："+ money +">"+ myParty.charStatus.getMoney());
                      continue;
                    }
                    myParty.charStatus.decMoney(money);
                  }
                }
              // アイテムやお金の取得処理
              }else if (ssplit[0].equals("get")) {
                if (ssplit[1].equals("reset")) {
                  myParty.characters.setReset();
                } else if (ssplit[1].equals("item")) {
                  if (this.items.getList().size()>0) {
                    Random rand = new Random();
                    Item gettenItem = (Item)this.items.getList().remove(rand.nextInt(this.items.getList().size()));
                    myParty.addItem(gettenItem);
                    this.items.decItem(gettenItem, 1);
                    System.out.println(gettenItem.toString());
                  }
                } else{
                  int money = Util.valueOf(ssplit[1]);
                  if (money>0) {
                    myParty.charStatus.addMoney(money);
                  }
                }
              }
            }
          }
          break;
        }
      }
      break;
    }
    return true;
  }
}
