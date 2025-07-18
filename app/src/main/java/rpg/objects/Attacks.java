package rpg.objects;

import java.util.*;
import rpg.utils.*;

public class Attacks extends Lists{
  private ArrayList<Attack> children;

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

  public Answers<Attack> toAnswers(Character character) {
    Answers<Attack> list = new Answers<>();
    list.add(new Answer<Attack>("防御する", null));
    for (Attack one: children) {
      if (one.isAvailable(character)) {
        list.add(new Answer<Attack>(one.toPrinting(), one));
      }
    }
    return list;
  }
}
