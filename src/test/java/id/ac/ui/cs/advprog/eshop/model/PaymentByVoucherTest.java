package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentByVoucherTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<String, String>();
    }

    @Test
    void testEmptyPaymentData() {
        PaymentByVoucher payment = new PaymentByVoucher("id-payment-testing-12345-abcde", PaymentMethod.BY_VOUCHER.getValue(), this.paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setPaymentData(this.paymentData));
    }

    @Test
    void testSetValidPaymentData() {
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
        PaymentByVoucher payment = new PaymentByVoucher("id-payment-testing-12345-abcde", PaymentMethod.BY_VOUCHER.getValue(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetShortPaymentData() {
        this.paymentData.put("voucherCode", "ESHOPDEFG12");
        PaymentByVoucher payment = new PaymentByVoucher("id-payment-testing-12345-abcde", PaymentMethod.BY_VOUCHER.getValue(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testNoPrefixPaymentData() {
        this.paymentData.put("voucherCode", "7654ABC321");
        PaymentByVoucher payment = new PaymentByVoucher("id-payment-testing-12345-abcde", PaymentMethod.BY_VOUCHER.getValue(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testNoEightNumberPaymentData() {
        this.paymentData.put("voucherCode", "ESHOPQWERTYUIOPA");
        PaymentByVoucher payment = new PaymentByVoucher("id-payment-testing-12345-abcde", PaymentMethod.BY_VOUCHER.getValue(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}