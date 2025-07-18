package rpg.objects;

import java.util.*;

import rpg.types.ItemType;
import rpg.utils.*;

public class Characters extends Lists{
  ArrayList<Character> children;
  protected Character leader;

  public Characters() {
    super();
    children = new ArrayList<Character>();
  }

  protected Lists getNewInstance() {
    return new Characters();
  }

  public List<?> getList() {
    return children;
  }

  protected void add(Base one) {
    super.add(one);
    if (size()==1){
      leader = (Character)one;
    }
  }

  protected void setList(List<?> children) {
    this.children = (ArrayList<Character>)children;
  }

  public void setReset() {
    for(Character one: children) {
      one.setReset();
    }
  }

  public Answers<Character> toAnswers(boolean excludeLord) {
    Answers<Character> list = new Answers<>();
    for(int index = excludeLord ? 1:0; index<children.size(); index++) {
      Character one = children.get(index);
      list.add(new Answer<Character>(one.toPrinting(), one));
    }
    return list;
  }

  public Answers<Character> toAnswers(Gear gear) {
    Answers<Character> list = new Answers<>();
    for(Character one: children) {
      if (gear.isFiting(one)) {
        list.add(new Answer<Character>(one.toWithGearPrinting(gear.getGearType()), one));
      }
    }
    return list;
  }
}
