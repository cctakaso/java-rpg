package rpg.objects;

import java.util.*;

import rpg.types.FieldType;
import rpg.utils.*;
import rpg.types.*;

public class Fields extends Lists{
  protected FieldType type;
  protected Size size;
  protected Items items;
  protected Characters characters;
  protected Parties parties;
  protected Talks talks;
  protected ArrayList<Fields> children;
  protected DicList<Size> sizes;
  protected DicList<Pt> pts;

  protected static int UNIT = 10;

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

  public Talks getTalks() {
    return this.talks;
  }

  protected Lists getNewInstance() {
    return new Fields();
  }
  public Size getSize() {
    return this.size;
  }

  public List<?> getList() {
    return children;
  }

  protected void setList(List<?> children) {
    this.children = (ArrayList<Fields>)children;
  }

  public void toPrinting(Party myParty) {
    char[][] area = new char[this.size.y/UNIT][this.size.x/UNIT];
    area = toPrinting(myParty, new Pt(), area);
    area[myParty.pt.y/UNIT][myParty.pt.x/UNIT] = 'M';
    String mapStr = "";
    for(int y=0; y<area.length; y++) {
      mapStr += new String(area[y]) + "\n";
    }
    System.out.println(mapStr);
    System.out.println("M:勇者パーティ、i:アイテム、c:キャラクター、n:NPキャラクター、e:モンスター、E:モンスターパーティ");
  }

  public char[][] toPrinting(Party myParty, Pt org, char[][] area) {
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
      fields.toPrinting(myParty, org, area);
    }
    return area;
  }

  /* 
  public void SetFromDic() {
    super.SetFromDic();
    List<Base> list = (List<Base>)getList();
    if (list.size()==0) {
      return;
    }
    int index = 0;
    for (Base one: list) {
      if (pts!=null) {
        ((Fields)one).pt = pts.get(index);
      }
      if (sizes!=null) {
        ((Fields)one).size = sizes.get(index);
      }
      index++;
    }
  }
  */

  public Fields clone() {
    return this.clone(0, null);
  }

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
      Map.entry("toPrinting", hitField.name + "("+orginPt.x+","+orginPt.y+")-("+(orginPt.x+hitField.size.x)+","+(orginPt.y+hitField.size.y)+")")
    );
  }


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

  private Pt getAbsolutePt(Pt orginPt) {
    return this.pt.getAbsolutePt(orginPt);
  }

  private boolean isHitPt(Pt pt, Pt parent) {
    if (parent != null) {
      pt = new Pt(pt.x - parent.x, pt.y - parent.y);
    }
    return this.pt.x <= pt.x && pt.x < (this.pt.x + this.size.x) &&
          this.pt.y <= pt.y && pt.y < (this.pt.y + this.size.y);
  }

}
