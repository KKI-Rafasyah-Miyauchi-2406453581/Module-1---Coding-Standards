package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "REJECTED";

        if (method.equals("VOUCHER")) {
            String code = paymentData.get("voucherCode");
            if (code != null && code.length() == 16 && code.startsWith("ESHOP")) {
                int numCount = 0;
                for (char c : code.toCharArray()) {
                    if (Character.isDigit(c)) {
                        numCount++;
                    }
                }
                if (numCount == 8) {
                    this.status = "SUCCESS";
                }
            }
        } else if (method.equals("CASH_ON_DELIVERY")) {
            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");

            if (address != null && !address.isEmpty() && deliveryFee != null && !deliveryFee.isEmpty()) {
                this.status = "SUCCESS";
            }
        }
    }
}