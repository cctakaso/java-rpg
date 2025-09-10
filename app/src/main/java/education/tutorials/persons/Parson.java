package education.tutorials.persons;

public class Parson {
  String name;  // 名前
  int age;      // 年齢

  // コンストラクタ
  public Parson(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // ゲッター
  public String getName() { return name; }
  public int getAge() { return age; }
}
