package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Payment payment = new Payment("1", "VOUCHER", this.paymentData);
        assertEquals("1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherRejected() {
        Map<String, String> badData = new HashMap<>();
        badData.put("voucherCode", "INVALIDCODE");
        Payment payment = new Payment("1", "VOUCHER", badData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentEmptyData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("1", "VOUCHER", new HashMap<>());
        });
    }

    @Test
    void testCreatePaymentCodSuccess() {
        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jalan Margonda Raya");
        codData.put("deliveryFee", "15000");

        Payment payment = new Payment("2", "CASH_ON_DELIVERY", codData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCodRejectedEmptyAddress() {
        Map<String, String> codData = new HashMap<>();
        codData.put("address", "");
        codData.put("deliveryFee", "15000");

        Payment payment = new Payment("2", "CASH_ON_DELIVERY", codData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCodRejectedEmptyDeliveryFee() {
        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jalan Margonda Raya");
        codData.put("deliveryFee", "");

        Payment payment = new Payment("2", "CASH_ON_DELIVERY", codData);
        assertEquals("REJECTED", payment.getStatus());
    }
}