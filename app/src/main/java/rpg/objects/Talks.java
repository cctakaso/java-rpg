package rpg.objects;
import java.util.*;

import rpg.utils.Anser;

public class Talks extends Lists{
  public ArrayList<Talk> children;

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
  public Anser print(Scanner scan, Party allyParty, Character otherCharacter) {
    Anser rtn = null;
    for(Talk talk: children) {
      rtn = talk.print(scan, allyParty, otherCharacter);
      break;
    }
    return rtn;
  }
}
