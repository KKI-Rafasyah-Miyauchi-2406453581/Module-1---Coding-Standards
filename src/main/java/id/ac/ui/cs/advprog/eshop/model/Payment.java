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
        this.status = "REJECTED"; // Default

        if (method.equals("VOUCHER")) {
            if (validateVoucher(paymentData.get("voucherCode"))) {
                this.status = "SUCCESS";
            }
        } else if (method.equals("CASH_ON_DELIVERY")) {
            if (validateCod(paymentData.get("address"), paymentData.get("deliveryFee"))) {
                this.status = "SUCCESS";
            }
        }
    }

    private boolean validateVoucher(String code) {
        if (code == null || code.length() != 16 || !code.startsWith("ESHOP")) {
            return false;
        }
        int numCount = 0;
        for (char c : code.toCharArray()) {
            if (Character.isDigit(c)) {
                numCount++;
            }
        }
        return numCount == 8;
    }

    private boolean validateCod(String address, String deliveryFee) {
        return address != null && !address.isEmpty() && deliveryFee != null && !deliveryFee.isEmpty();
    }
}