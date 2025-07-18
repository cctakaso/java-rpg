package rpg.objects;
import java.util.*;

import rpg.utils.Answer;

public class Talks extends Lists{
  private ArrayList<Talk> children;

  public Talks() {
    super();
    children = new ArrayList<Talk>();
  }

  protected Lists getNewInstance() {
    return new Talks();
  }

  public List<?> getList() {
    return children;
  }

  protected void setList(List<?> children) {
    this.children = (ArrayList<Talk>)children;
  }
  public Answer<Item> print(Scanner scan, Party allyParty, Character otherCharacter) {
    Answer<Item> rtn = null;
    for(Talk talk: children) {
      rtn = talk.print(scan, allyParty, otherCharacter);
      break;
    }
    return rtn;
  }
}
