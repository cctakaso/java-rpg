package education.tutorials.restaurant.c_objectOriented;

// コッククラス
// オーダーに基づき料理を作る従業員
class Cook {
    private String name;  // コックの名前

    // コンストラクタ
    public Cook(String name) {
        this.name = name;
    }

    // ゲッター
    public String getName() { return name; }

    // オーダー用紙を見て料理を作り、完成した料理名を返す
    public String cook(OrderSheet orderSheet) {
        System.out.println(name + " がオーダー用紙 (" + orderSheet.getMenuItem() + ") に基づいて調理を開始します。");
        // 調理時間シミュレーション
        System.out.println(orderSheet.getMenuItem() + " の調理が完了しました。");
        return orderSheet.getMenuItem(); // 完成した料理の名前を返す
    }

    // ウェイターから注文を受け取るメソッド
    public void receiveOrder(OrderSheet orderSheet) {
        System.out.println(name + " が注文 (" + orderSheet.getMenuItem() + ") を受け取りました。");
    }
}
