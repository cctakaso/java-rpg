package education.tutorials.restaurant.c_objectOriented;

// 顧客クラス
// 来店、注文、食事、会計、退店を行う
class Customer {
    private String name;  // 顧客の名前
    private String seat;  // 座っている席
    private OrderSheet orderSheet;  // 持っているオーダー用紙
    private String meal; // 持っている料理

    // コンストラクタ
    public Customer(String name) {
        this.name = name;
    }

    // ゲッターとセッター
    public String getName() { return name; }
    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }
    public OrderSheet getOrderSheet() { return orderSheet; }
    public void setOrderSheet(OrderSheet orderSheet) { this.orderSheet = orderSheet; }
    public String getMeal() { return meal; }
    public void setMeal(String meal) { this.meal = meal; }

    // 来店メソッド
    public void arrive() {
        System.out.println(name + " が来店しました。");
    }

    // 注文メソッド
    public void order(Waiter waiter, String menuItem) {
        System.out.println(name + " は " + menuItem + " を注文します。");
        this.orderSheet = waiter.takeOrder(this, menuItem);
    }

    // 食事メソッド
    public void eat() {
        if (meal != null) {
            System.out.println(name + " は " + meal + " を食べ始めました。");
            // 食事時間シミュレーション
            System.out.println(name + " は食事を終えました。");
            this.meal = null; // 食べ終わった
        } else {
            System.out.println(name + " は食べるものがありません。");
        }
    }

    // 会計メソッド
    public void goToCashier() {
        System.out.println(name + " はオーダー用紙を持って会計場所に向かいます。");
    }

    // 支払いメソッド
    public void pay(Cashier cashier) {
        if (orderSheet != null) {
            cashier.processPayment(this, orderSheet);
            this.orderSheet = null; // 支払い済み
        } else {
            System.out.println(name + " は支払うものがありません。");
        }
    }

    // 退店メソッド
    public void exit() {
        System.out.println(name + " が退店しました。");
    }
}