package rpg.objects;

import java.util.ArrayList;
import java.util.Scanner;
import rpg.Adventure;
import rpg.types.ItemType;
import rpg.utils.*;

public class Talk extends Base{
  protected ArrayList<String> befores;
  protected ArrayList<String> choices;
  protected ArrayList<String> afters;

  public Talk(String [] befores, String [] choices, String [] afters) {
    this();
    for (String before: befores) {
      this.befores.add(before);
    }
    for (String choice: choices) {
      this.befores.add(choice);
    }
    for (String after: afters) {
      this.befores.add(after);
    }
  }

  public Talk() {
    super();
    this.befores = new ArrayList<String>();
    this.choices = new ArrayList<String>();
    this.afters = new ArrayList<String>();
  }

  public Talk clone() {
    return this.clone(0, null);
  }

  public Talk clone(int num, Pt randomPt) {
    Talk copy = null;
    try {
      copy = (Talk)super.clone(num, randomPt);
      copy.befores = (ArrayList<String>)befores.clone();
      copy.choices = (ArrayList<String>)choices.clone();
      copy.afters = (ArrayList<String>)afters.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public void printBefore(Scanner scan) {
    for (String before : befores) {
      System.out.println(before);
    }
  }

  public void printAfter(Scanner scan) {
    for (String after : afters) {
      System.out.println(after);
    }
  }

  public Ansers makeOptions(ArrayList<String> choices, Party allyParty, Character otherCharacter) {
    Ansers ansers = new Ansers();

    for (String choice : choices) {
      if (choice.indexOf(':')==0) {
        if (choice.equals(":buy.gears")) {
          ansers = otherCharacter.items.toAnsers(ItemType.Gear, choice);
        }else if (choice.equals(":buy.items")) {
          ansers = otherCharacter.items.toAnsers(ItemType.Item, choice);
        }else if (choice.equals(":sale.gears")) {
          ansers = allyParty.items.toAnsers(ItemType.Gear, choice);
        }else if (choice.equals(":sale.items")) {
          ansers = allyParty.items.toAnsers(ItemType.Item, choice);
        }else if (choice.indexOf('/')>0) {
          int n = choice.indexOf('/');
          ansers.add(new Anser(choice.substring(n+1), null, choice.substring(0, n)));
        }
      }else{
        ansers.add(new Anser(choice));
      }
    }
    return ansers;
  }

  public Anser print(Scanner scan, Party allyParty, Character otherCharacter) {
    Anser anser;

    printBefore(scan);
    Ansers ansers = makeOptions(choices, allyParty, otherCharacter);
    if (ansers.size()==0) {
      return null;
    }
    anser = ansers.printChoice(scan, null, true);

    if (afters.size()>0) {
      String after = afters.get(anser.index);
      if (after.charAt(0) == '>') {
        ArrayList<Base> list = (ArrayList<Base>)Adventure.getDicClones("Talks", after.substring(1));
        Talk talk = (Talk)list.get(0);
        anser = talk.print(scan, allyParty, otherCharacter);
      }else{
        System.out.println(after);
      }
    }
    return anser;
  }
}
