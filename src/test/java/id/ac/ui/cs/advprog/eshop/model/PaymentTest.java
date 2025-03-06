package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Map<String,String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<String,String>();
    }

    @Test
    void testCreatePaymentValid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(
                "id-payment-testing-12345-abcde",
                "by-voucher",
                "SUCCESS",
                paymentData
        );
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "id-payment-testing-12345-abcde",
                    "by-voucher",
                    "GABISA",
                    paymentData
            );
        });
    }

    @Test
    void testCreatePaymentValidMethod() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(
                "id-payment-testing-12345-abcde",
                "by-voucher",
                "SUCCESS",
                paymentData
        );
        assertEquals("by-voucher", payment.getMethod());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        paymentData.put("debitCard", "081812345678");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "id-payment-testing-12345-abcde",
                    "debitCard",
                    "SUCCESS",
                    paymentData);
        });
    }

    @Test
    void testSetStatusInvalid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(
                "id-payment-testing-12345-abcde",
                "by-voucher",
                "SUCCESS",
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("HALO"));
    }

    @Test
    void testSetStatusToRejected() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(
                "id-payment-testing-12345-abcde",
                "by-voucher",
                "SUCCESS",
                paymentData
        );
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateAllValidArguments() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = Payment.builder()
                .id("id-payment-testing-12345-abcde")
                .method(PaymentMethod.BY_VOUCHER.getValue())
                .status(PaymentStatus.SUCCESS.getValue())
                .paymentData(paymentData).build();
        assertEquals("id-payment-testing-12345-abcde", payment.getId());
        assertEquals("by-voucher", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }
}