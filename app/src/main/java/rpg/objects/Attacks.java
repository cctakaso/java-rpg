package rpg.objects;

import java.util.*;
import rpg.utils.*;

public class Attacks extends Lists{
  ArrayList<Attack> children;

  public Attacks() {
    super();
    this.children = new ArrayList<Attack>();
  }

  protected Lists getNewInstance() {
    return new Attacks();
  }

  public List<?> getList() {
    return children;
  }

  protected void setList(List<?> children) {
    this.children = (ArrayList<Attack>)children;
  }

  public Ansers toAnsers(Character character) {
    Ansers list = new Ansers();
    list.add(new Anser("防御する", null));
    for (Attack one: children) {
      if (one.isAvailable(character)) {
        list.add(new Anser(one.toPrinting(), one));
      }
    }
    return list;
  }
}
