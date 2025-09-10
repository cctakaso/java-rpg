package education.tutorials.restaurant.c_objectOriented;

// 会計担当クラス
// 料金の計算と支払いの受付を行う従業員
class Cashier {
    private String name;  // 会計担当者の名前

    public Cashier(String name) {
        this.name = name;
    }

    // オーダー用紙から請求金額を計算するメソッド
    public int calculateBill(OrderSheet orderSheet) {
        System.out.println(name + " がオーダー用紙 (" + orderSheet.getMenuItem() + ") から金額を計算します。");
        return orderSheet.getPrice();
    }

    // 顧客からの支払いを処理するメソッド
    public void processPayment(Customer customer, OrderSheet orderSheet) {
        int amount = calculateBill(orderSheet);
        System.out.println(customer.getName() + " へ、お会計は " + amount + " 円です。");
        // 支払いを受け取るシミュレーション
        System.out.println(customer.getName() + " から " + amount + " 円を受け取りました。");
        System.out.println("支払いが完了しました。");
    }
}