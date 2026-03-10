package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment("1", "VOUCHER", "SUCCESS", data);
    }

    @Test
    void testSave() {
        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(paymentRepository.findById("non-existent-id"));
    }

    @Test
    void testSaveUpdate() {
        paymentRepository.save(payment);
        Map<String, String> newData = new HashMap<>();
        newData.put("address", "Jakarta");
        Payment updatedPayment = new Payment("1", "CASH", "REJECTED", newData);

        paymentRepository.save(updatedPayment);
        Payment result = paymentRepository.findById("1");

        assertEquals("REJECTED", result.getStatus());
        assertEquals("CASH", result.getMethod());
    }
}