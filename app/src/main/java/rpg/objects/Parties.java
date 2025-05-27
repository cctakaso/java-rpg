package rpg.objects;

import java.util.*;

import rpg.utils.Pt;

public class Parties extends Lists{
  public ArrayList<Party> children;

  public Parties() {
    super();
    children = new ArrayList<Party>();
  }

  protected Lists getNewInstance() {
    return new Parties();
  }

  public List<?> getList() {
    return children;
  }

  protected void setList(List<?> children) {
    this.children = (ArrayList<Party>)children;
  }

  public Lists getHitInstance(Party mParty, Pt pt) {
    Class<?> cls = this.getClass();
    try {
      Lists lsts = getNewInstance();
      List<Base> lst = (List<Base>)getList();
      for(Base one: lst) {
        if (one != mParty && one.pt.isEquals(pt)) {
          lsts.add(one);
        }
      }
    return lst.size()> 0 ? lsts:null;
    }catch(Exception ex) {
    }
    return null;
  }
}
