package education.tutorials.restaurant.c_objectOriented;

// オーダー用紙クラス
// 注文内容を保持するためのクラス
public class OrderSheet {
    private String customerName;  // 顧客名
    private String seat;          // 席番号
    private String menuItem;      // 注文したメニュー名
    private int price;            // メニューの価格

    // コンストラクタ
    public OrderSheet(String customerName, String seat, String menuItem, int price) {
        this.customerName = customerName;
        this.seat = seat;
        this.menuItem = menuItem;
        this.price = price;
    }

    // ゲッター
    public String getCustomerName() { return customerName; }
    public String getSeat() { return seat; }
    public String getMenuItem() { return menuItem; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return "OrderSheet [客名=" + customerName + ", 席=" + seat + ", メニュー=" + menuItem + ", 価格=" + price + "円]";
    }
}
