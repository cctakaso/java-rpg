package education.tutorials.restaurant.b_structured;

public class AppStep2 {
    // フィールドをstatic変数として定義し、メソッド間で共有
    private static boolean isCustomerInStore = false;
    private static String customerSeat = null;
    private static String orderMenu = null;
    private static String orderSheet = null; // 例: "A-1:ラーメン"
    private static boolean isOrderPassedToCook = false;
    private static boolean isMealCooked = false;
    private static boolean isMealServed = false;
    private static boolean isCustomerEating = false;
    private static int billAmount = 0;
    private static boolean isPaid = false;

    public static void main(String[] args) {
        System.out.println("---- レストランシミュレーション開始 (Step 2) ----");

        // 1. 来店
        customerArrives();

        // 2. 席への案内
        guideCustomerToSeat();

        // 3. 注文受付とオーダーシート作成
        String orderedMenuItem = takeOrder();
        createOrderSheet(customerSeat, orderedMenuItem);

        // 4. コックへの注文伝達
        passOrderToCook(orderSheet);

        // 5. 調理
        cookMeal(orderSheet);

        // 6. 配膳
        if (isMealCooked) {
            serveMeal(customerSeat, orderMenu);
        }

        // お客の食事完了を待つシミュレーション
        customerFinishesEating();

        // 7. 会計
        int calculatedBill = calculateBill(orderSheet);
        processPayment(calculatedBill);

        // 8. 退店
        customerExits();

        System.out.println("---- レストランシミュレーション終了 (Step 2) ----");
    }

    // 各処理をメソッドとして定義
    private static void customerArrives() {
        System.out.println("お客が来店しました。");
        isCustomerInStore = true;
    }

    private static void guideCustomerToSeat() {
        System.out.println("ウェイターがお客を席に案内します。");
        customerSeat = "A-1";
        System.out.println("お客は " + customerSeat + " に着席しました。");
    }

    private static String takeOrder() {
        System.out.println("ウェイターがメニューを提示し、注文を受け付けます。");
        orderMenu = "ラーメン"; // お客がラーメンを注文したと仮定
        return orderMenu;
    }

    private static void createOrderSheet(String seat, String menu) {
        orderSheet = seat + ":" + menu; // オーダー用紙を作成
        System.out.println("オーダー用紙: " + orderSheet + " を作成しました。");
    }

    private static void passOrderToCook(String sheet) {
        System.out.println("ウェイターがコックに注文 (" + sheet + ") を伝えます。");
        isOrderPassedToCook = true;
        System.out.println("コックに注文が伝達されました。");
    }

    private static void cookMeal(String sheet) {
        System.out.println("コックがオーダー用紙 (" + sheet + ") に基づいて料理を開始します。");
        // 調理時間をシミュレート
        String[] parts = sheet.split(":");
        String menuName = parts[1];
        System.out.println(menuName + " の調理が完了しました。");
        isMealCooked = true;
    }

    private static void serveMeal(String seat, String menu) {
        if (isMealCooked) {
            System.out.println("ウェイターが料理 (" + menu + ") を " + seat + " のお客に届けます。");
            isMealServed = true;
            isCustomerEating = true;
            System.out.println("お客は食事を開始しました。");
        }
    }

    private static void customerFinishesEating() {
        System.out.println("お客が食事を終えました。");
        isCustomerEating = false;
    }

    private static int calculateBill(String sheet) {
        System.out.println("会計担当者がオーダー用紙 (" + sheet + ") から金額を計算します。");
        // 簡易的な金額計算（ラーメンは800円と仮定）
        String[] parts = sheet.split(":");
        String menuName = parts[1];
        if (menuName.equals("ラーメン")) {
            billAmount = 800;
        } else {
            billAmount = 0; // その他のメニュー
        }
        System.out.println("お会計は " + billAmount + " 円です。");
        return billAmount;
    }

    private static void processPayment(int amount) {
        System.out.println("お客がお金を支払い (" + amount + "円)、退店します。");
        isPaid = true;
    }

    private static void customerExits() {
        isCustomerInStore = false; // 退店
        System.out.println("お客が退店しました。");
    }
}
