package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = "REJECTED";

        if (method.equals("VOUCHER")) {
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP")) {
                int numCount = 0;
                for (char c : voucherCode.toCharArray()) {
                    if (Character.isDigit(c)) numCount++;
                }
                if (numCount == 8) status = "SUCCESS";
            }
        }

        Payment payment = new Payment(order.getId(), method, paymentData);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) { return null; }

    @Override
    public Payment getPayment(String paymentId) { return null; }

    @Override
    public List<Payment> getAllPayments() { return null; }
}