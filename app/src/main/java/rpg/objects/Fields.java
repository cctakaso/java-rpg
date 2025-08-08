package rpg.objects;

import java.util.*;
import rpg.utils.*;
import rpg.types.*;

/**
 * フィールドを表すクラス。
 * <p>
 * フィールドは、ゲーム内のマップやエリアを表し、フィールドの種類（FieldType）、
 * サイズ（Size）、アイテム、キャラクター、パーティなどの情報を保持します。
 * </p>
 */
public class Fields extends Lists{
  protected FieldType type; // フィールドの種類
  protected Size size;      // フィールドのサイズ
  protected Items items;      // フィールドに存在するアイテムのリスト
  protected Characters characters;  // フィールドに存在するキャラクターのリスト
  protected Parties parties;    // フィールドに存在するパーティのリスト
  protected Talks talks;        // フィールドに関連する会話のリスト
  protected ArrayList<Fields> children;   // フィールドの子フィールドのリスト
  protected DicList<Size> sizes;          // フィールドのサイズの辞書リスト
  protected DicList<Pt> pts;      // フィールドの座標の辞書リスト

  protected static int UNIT = 10; // フィールドの単位サイズ

  /**
   * デフォルトコンストラクタ。
   * <p>
   * フィールドのプロパティを初期化します。
   * </p>
   */
  public Fields() {
    super();
    this.size = new Size();
    this.items = new Items();
    this.characters = new Characters();
    this.parties = new Parties();
    this.talks = new Talks();
    this.children = new ArrayList<Fields>();
    this.sizes = new DicList<Size>();
    this.pts = new DicList<Pt>();
  }

  /**
   * 新たにフィールズインスタンスを取得します。
   * <p>
   * フィールド群の取得。
   * </p>
   * @return フィールズインスタンス
   */

  protected Lists getNewInstance() {
    return new Fields();
  }

  /**
   * フィールドのサイズを取得します。
   * <p>
   * フィールドのサイズは、フィールドの幅と高さを表します。
   * </p>
   * @return フィールドのサイズ
   */
  public Size getSize() {
    return this.size;
  }

  /**
   * フィールドのリストを取得します。
   * <p>
   * フィールドのリストは、フィールドオブジェクトのArrayListとして保持されます。
   * </p>
   * @return フィールドのリスト
   */
  @Override
  public List<?> getList() {
    return children;
  }

  /**
   * フィールドのリストを設定します。
   * <p>
   * 引数として渡されたリストを、フィールドのリストとして設定します。
   * </p>
   * @param children フィールドのリスト
   */
  @Override
  protected void setList(List<?> children) {
    this.children = (ArrayList<Fields>)children;
  }

  /**
   * フィールドの種類を取得します。
   * @return フィールドの種類
   */
  public FieldType getType() {
    return this.type;
  }

  /**
   * フィールドの会話を取得します。
   * @return フィールドの会話
   */
  public Talks getTalks() {
    return this.talks;
  }

  /**
   * フィールドのマップを表示します。
   * <p>
   * フィールドを文字列として表示します。
   * </p>
   */
  public void toString(Party myParty) {
    char[][] area = new char[this.size.y/UNIT][this.size.x/UNIT];
    area = toString(myParty, new Pt(), area);
    area[myParty.pt.y/UNIT][myParty.pt.x/UNIT] = 'M';
    String mapStr = "";
    for(int y=0; y<area.length; y++) {
      mapStr += new String(area[y]) + "\n";
    }
    System.out.print(mapStr);
    System.out.println("M:勇者パーティ、i:アイテム、c:キャラクター、n:NPキャラクター、e:モンスター、E:モンスターパーティ");
  }

  /**
   * フィールドのマップを文字列として取得します。
   * <p>
   * フィールドを文字列として表現し、指定されたパーティの位置を考慮します。
   * </p>
   * @param myParty プレイヤーのパーティ
   * @param org フィールドの原点座標
   * @param area マップの文字列配列
   * @return マップの文字列配列
   */
  public char[][] toString(Party myParty, Pt org, char[][] area) {
    org = this.pt.getAbsolutePt(org);
    for(int y=org.y/UNIT; y<(org.y+this.size.y)/UNIT; y++) {
      for(int x=org.x/UNIT; x<(org.x+this.size.x)/UNIT; x++) {
        if (y==org.y/UNIT || y==(org.y+this.size.y)/UNIT-1) {
          area[y][x] = (x==org.x/UNIT || x==(org.x+this.size.x)/UNIT-1) ? '*':'-';
        }else if (x==org.x/UNIT || x==(org.x+this.size.x)/UNIT-1) {
          area[y][x] = '|';
        }else{
          area[y][x] = ' ';
        }
      }
    }

    for(Item item: (ArrayList<Item>)this.items.getList()) {
      area[(item.pt.y+org.y)/UNIT][(item.pt.x+org.x)/UNIT] = 'i';
    }

    for(Character character: (ArrayList<Character>)this.characters.getList()) {
      char ch = ' ';
      if (character.type==null)
        break;
      if (character.type.isCrewCharacter()) {
        ch = 'c';
      }else if (character.type.isEnemyCharacter()) {
        ch = 'e';
      }else if (character.type.isNpcCharacter()) {
        ch = 'n';
      }
      area[(character.pt.y+org.y)/UNIT][(character.pt.x+org.x)/UNIT] = ch;
    }

    for(Party party: (ArrayList<Party>)this.parties.getList()) {
      char ch = ' ';
      if (party == myParty) {
        ch = 'M';
      }else if (party.isCrewParty()) {
        ch = 'C';
      }else if (party.isEnemyParty()) {
        ch = 'E';
      }else if (party.isNpcParty()) {
        ch = 'N';
      }
      area[(party.pt.y+org.y)/UNIT][(party.pt.x+org.x)/UNIT] = ch;
    }

    for(Fields fields:this.children) {
      fields.toString(myParty, org, area);
    }
    return area;
  }


  /**
   * フィールドのクローンを作成します。
   * <p>
   * フィールドクローンの識別番号とランダムポイントを持つ新しいFieldsオブジェクトを返します。
   * </p>
   * @return 新しいFieldsオブジェクト
   */
  public Fields clone() {
    return this.clone(0, null);
  }

  /**
   * フィールドのクローンを作成します。
   * <p>
   * フィールドの種類、サイズ、アイテム、キャラクター、パーティ、会話などを
   * 保持した新しいFieldsオブジェクトを返します。
   * </p>
   * @param num クローンの番号（識別用）
   * @param randomPt ランダムな位置（座標）
   * @return 新しいFieldsオブジェクト
   */
  public Fields clone(int num, Pt randomPt) {
    Fields copy = null;
    try {
      copy = (Fields)super.clone(num, randomPt);
      copy.type = this.type;
      copy.size = this.size.clone();
      copy.talks = (Talks)this.talks.clone();
      copy.items = (Items)this.items.clone();
      copy.characters = (Characters)this.characters.clone();
      copy.parties = (Parties)this.parties.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * フィールドのヒット判定を行います。
   * <p>
   * 指定された座標がフィールド内に存在するかどうかを判定します。
   * </p>
   * @param pt 判定する座標
   * @return ヒットしたフィールドと原点座標、文字列表現を含むマップ
   */
  public Map<String, Object>  hitField(Pt pt) {
    Fields hitField = this;
    Pt orginPt = new Pt(0,0);
    if (!hitField.isHitPt(pt, orginPt)) {
      return null;
    }
    ArrayList<Fields> lst = children;
    while(lst!=null || lst.size()>0) {
      for(Fields one : lst) {
        if (one.isHitPt(pt, orginPt)) {
          hitField = one;
          orginPt = one.getAbsolutePt(orginPt);
          lst = one.children;
          break;
        }
      }
      break;
    }
    //Java 9
    return Map.ofEntries(
      Map.entry("hitField", hitField),
      Map.entry("orginPt", orginPt),
      Map.entry("toString", hitField.name + "("+orginPt.x+","+orginPt.y+")-("+(orginPt.x+hitField.size.x)+","+(orginPt.y+hitField.size.y)+")")
    );
  }

  /**
   * イベントをフィールドから削除します。
   * <p>
   * 指定された座標と原点座標に基づいて、イベントをフィールドから削除します。
   * </p>
   * @param pt イベントが発生した座標
   * @param orginPt イベントの原点座標
   * @param event 削除するイベント
   */
  public void removeEvent(Pt pt, Pt orginPt, Event event) {
    Pt localPt = pt.getLocalPt(orginPt);
    switch (event.getType()) {
      case EventType.ChangeField:
        break;
      case EventType.HitItems:
        this.items.removeHitInstance(event.getItems());
        break;
      case EventType.HitCharacters:
        this.characters.removeHitInstance(event.getCharacters());
        break;
      case EventType.HitParties:
        this.parties.removeHitInstance(event.getParties());
        break;
      default:
        break;
    }

  }

  /**
   * フィールドのヒットイベントを取得します。
   * <p>
   * 指定された座標と原点座標に基づいて、フィールド内のヒットイベントを取得します。
   * </p>
   * @param mParty プレイヤーのパーティ
   * @param pt ヒット判定する座標
   * @param orginPt イベントの原点座標
   * @return ヒットしたイベントのリスト
   */
  public List<Event> getHitEvents(Party mParty, Pt pt, Pt orginPt) {
    List<Event> eventLst = new ArrayList<Event>();
    Pt localPt = pt.getLocalPt(orginPt);
    //if (!isHitPt(localPt, null)) {
    //  eventLst.add(Event.newEventChangeField(hitField(pt)));
    //  return eventLst;
    //}

    Lists lists;
    if (this.items != null) {
      lists = this.items.getHitInstance(localPt);
      if (lists != null && lists.size()>0) {
        eventLst.add(Event.newEventHitItems((Items)lists));
      }
    }

    if (this.characters != null) {
      lists = this.characters.getHitInstance(localPt);
      if (lists != null && lists.size()>0) {
        eventLst.add(Event.newEventHitCharacters((Characters)lists));
      }
    }

    if (this.parties != null) {
      lists = this.parties.getHitInstance(mParty, localPt);
      if (lists != null && lists.size()>0) {
        eventLst.add(Event.newEventHitParties((Parties)lists));
      }
    }

    return eventLst.size()>0 ? eventLst:null;
  }

  /**
   * フィールドの原点座標を取得します。
   * <p>
   * フィールドの原点座標は、フィールドの位置を示す座標です。
   * </p>
   * @param orginPt 原点座標
   * @return フィールドの原点座標
   */
  private Pt getAbsolutePt(Pt orginPt) {
    return this.pt.getAbsolutePt(orginPt);
  }

  /**
   * フィールドのヒット判定を行います。
   * <p>
   * 指定された座標がフィールド内に存在するかどうかを判定します。
   * </p>
   * @param pt 判定する座標
   * @param parent 親フィールドの座標
   * @return ヒットした場合はtrue、そうでない場合はfalse
   */
  private boolean isHitPt(Pt pt, Pt parent) {
    if (parent != null) {
      pt = new Pt(pt.x - parent.x, pt.y - parent.y);
    }
    return this.pt.x <= pt.x && pt.x < (this.pt.x + this.size.x) &&
          this.pt.y <= pt.y && pt.y < (this.pt.y + this.size.y);
  }

}
