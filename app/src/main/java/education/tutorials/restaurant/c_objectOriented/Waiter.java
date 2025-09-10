package education.tutorials.restaurant.c_objectOriented;

// ウェイタークラス
// 顧客の案内、注文受付、料理提供を行う従業員
class Waiter {
    private String name;  // ウェイターの名前

    // コンストラクタ
    public Waiter(String name) {
        this.name = name;
    }

    // 顧客を席に案内するメソッド
    public void guideToSeat(Customer customer, String seat) {
        System.out.println(name + " が " + customer.getName() + " を " + seat + " に案内します。");
        customer.setSeat(seat);
    }

    // 顧客から注文を受け取り、オーダー用紙を作成して返すメソッド
    public OrderSheet takeOrder(Customer customer, String menuItem) {
        System.out.println(name + " が " + customer.getName() + " から " + menuItem + " の注文を受け付けます。");
        // メニューの価格を仮定
        int price = 0;
        if (menuItem.equals("ラーメン")) {
            price = 800;
        } else if (menuItem.equals("カレー")) {
            price = 700;
        }
        OrderSheet orderSheet = new OrderSheet(customer.getName(), customer.getSeat(), menuItem, price);
        System.out.println(name + " がオーダー用紙を作成しました: " + orderSheet);
        return orderSheet;
    }

    // コックに注文を伝えるメソッド
    public void passOrderToCook(Cook cook, OrderSheet orderSheet) {
        System.out.println(name + " がコック " + cook.getName() + " に注文 (" + orderSheet.getMenuItem() + ") を伝えます。");
        cook.receiveOrder(orderSheet);
    }

    // 顧客に料理を提供するメソッド
    public void serveMeal(Customer customer, String meal) {
        System.out.println(name + " が " + meal + " を " + customer.getName() + " の席 (" + customer.getSeat() + ") に届けます。");
        customer.setMeal(meal);
    }
}