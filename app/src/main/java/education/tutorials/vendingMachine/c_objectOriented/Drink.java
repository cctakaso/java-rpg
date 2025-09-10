package education.tutorials.vendingMachine.c_objectOriented;

// 個々のドリンク情報を保持するクラス
public class Drink {
    // フィールド（外部から直接変更されたくないのでprivateにする）
    private String name;
    private int price;
    private int stock;

    // コンストラクタ（インスタンス生成時に値を設定する）
    public Drink(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 在庫を1つ減らすメソッド
    public void decreaseStock() {
        if (this.stock > 0) {
            this.stock--;
        }
    }

    // 在庫があるかどうかを返すメソッド
    public boolean hasStock() {
        return this.stock > 0;
    }
    
    // 各フィールドの値を取得するためのメソッド（ゲッター）
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}