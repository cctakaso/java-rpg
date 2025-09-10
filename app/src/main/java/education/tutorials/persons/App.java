package education.tutorials.persons;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello Persons!");
        Parson taro = new Parson("Taro", 25);
        System.out.println(taro.getName() + "さんは" + taro.getAge() + "歳です。");
        Parson hanako = new Parson("Hanako", 20);
        System.out.println(hanako.getName() + "さんは" + hanako.getAge() + "歳です。");
    }
}
