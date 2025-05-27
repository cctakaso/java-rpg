package rpg.objects;

import java.util.*;
import rpg.types.*;
import rpg.utils.*;

public class Event {
  private EventType type;
  private Party myParty;

  private Pt orginPt;
  private Fields field;
  private Items items;
  private Characters characters;
  private Parties parties;

  public EventType getType() {
    return this.type;
  }
  public Fields getField() {
    return this.field;
  }
  public Items getItems() {
    return this.items;
  }
  public Characters getCharacters() {
    return this.characters;
  }
  public Parties getParties() {
    return this.parties;
  }


  public static Event newEventChangeField(Map<String, Object> map) {
    Event one = new Event();
    one.type = EventType.ChangeField;
    one.orginPt = (Pt)map.get("orginPt");
    one.field = (Fields)map.get("hitField");
    return one;
  }

  public static Event newEventHitItems(Items items) {
    Event one = new Event();
    one.type = EventType.HitItems;
    one.items = items;
    return one;
  }

  public static Event newEventHitCharacters(Characters characters) {
    Event one = new Event();
    one.type = EventType.HitCharacters;
    one.characters = characters;
    return one;
  }

  public static Event newEventHitParties(Parties parties) {
    Event one = new Event();
    one.type = EventType.HitParties;
    one.parties = parties;
    return one;
  }
}
