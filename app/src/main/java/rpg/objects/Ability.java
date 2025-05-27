package rpg.objects;
import rpg.types.AbilityType;
public class Ability {
  //比率初期値
  static int INITIAL_RATE = 10;

  public AbilityType type;
  //能力値（１〜10）
  public int point;
  //能力値の一時的低下増大比率（10がデフォルト）
  public int rate;   //筋力率

}
