package education.tutorials.restaurant.a_monolithic;

public class AppStep1 {
    public static void main(String[] args) {
        // フィールド（変数）の定義
        boolean isCustomerInStore = false;  // 来店しているか
        String customerSeat = null;         // お客の席
        String orderMenu = null;            // 注文されたメニュー
        String orderSheet = null;           // オーダー用紙 (例: "A-1:ラーメン")
        boolean isOrderPassedToCook = false;  // コックに注文が伝達されたか
        boolean isMealCooked = false;       // 料理が完成したか
        boolean isMealServed = false;       // 料理が配膳されたか
        boolean isCustomerEating = false;   // お客が食事中か
        int billAmount = 0;                 // 請求金額
        boolean isPaid = false;             // 支払いが完了したか

        System.out.println("---- レストランシミュレーション開始 (Step 1) ----");

        // 1. お客の来店
        System.out.println("お客が来店しました。");
        isCustomerInStore = true;

        // 2. ウェイターは来店客を席に誘導する
        System.out.println("ウェイターがお客を席に案内します。");
        customerSeat = "A-1";
        System.out.println("お客は " + customerSeat + " に着席しました。");

        // 3. ウェイターはメニューを提示して注文を受け付けオーダー用紙に書き込む
        System.out.println("ウェイターがメニューを提示し、注文を受け付けます。");
        orderMenu = "ラーメン"; // お客がラーメンを注文したと仮定
        orderSheet = customerSeat + ":" + orderMenu; // オーダー用紙を作成
        System.out.println("オーダー用紙: " + orderSheet + " を作成しました。");

        // 4. ウェイターはコックにオーダー用紙と共に注文を伝える
        System.out.println("ウェイターがコックに注文を伝えます。");
        isOrderPassedToCook = true;
        System.out.println("コックに注文が伝達されました。");

        // 5. コックはオーダー用紙に基づき順番に料理を行う
        System.out.println("コックがオーダー用紙に基づいて料理を開始します。");
        // 調理時間をシミュレート
        System.out.println(orderMenu + " の調理が完了しました。");
        isMealCooked = true;

        // 6. 料理が出来たら、ウェイターは料理とそのオーダー用紙を、注文したお客の席に正しく届ける
        if (isMealCooked) {
            System.out.println("ウェイターが料理 (" + orderMenu + ") を " + customerSeat + " のお客に届けます。");
            isMealServed = true;
            isCustomerEating = true;
            System.out.println("お客は食事を開始しました。");
        }

        // お客が食べ終わるのを待つ
        System.out.println("お客が食事を終えました。");
        isCustomerEating = false;

        // 7. お客は食べ終わったらオーダー用紙を持って会計場所に持っていく
        System.out.println("お客がオーダー用紙を持って会計場所に向かいます。");

        // 8. 会計場所で、会計担当はお客が持ってきたオーダー用紙から金額を計算してお客に伝える
        System.out.println("会計担当者がオーダー用紙 (" + orderSheet + ") から金額を計算します。");
        // 簡易的な金額計算（ラーメンは800円と仮定）
        if (orderMenu.equals("ラーメン")) {
            billAmount = 800;
        } else {
            billAmount = 0; // その他のメニュー
        }
        System.out.println("お会計は " + billAmount + " 円です。");

        // 9. お客はお金を支払い出店する
        System.out.println("お客がお金を支払い、退店します。");
        isPaid = true;
        isCustomerInStore = false; // 退店

        System.out.println("---- レストランシミュレーション終了 (Step 1) ----");
    }
}
