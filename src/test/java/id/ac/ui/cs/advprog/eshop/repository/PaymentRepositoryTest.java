package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        this.paymentRepository = new PaymentRepository();
        this.payments = new ArrayList<>();
        Map<String, String> validVoucherData = new HashMap<String,String>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("id-payment-1-testing-12345-abcde", PaymentMethod.BY_VOUCHER.getValue(), PaymentStatus.SUCCESS.getValue(), validVoucherData);
        payments.add(payment1);

        Map<String, String> invalidVoucherData = new HashMap<String,String>();
        invalidVoucherData.put("voucherCode", "X7J4KPQZ9L2MFJ8W72NDK5XL");
        Payment payment2 = new Payment("id-payment-2-testing-12345-abcde", PaymentMethod.BY_VOUCHER.getValue(), PaymentStatus.REJECTED.getValue(), invalidVoucherData);
        payments.add(payment2);

        Map<String, String> validBankData = new HashMap<String,String>();
        validBankData.put("bankName", "Bank Adpro");
        validBankData.put("referenceCode", "12345");
        Payment payment3 = new Payment("id-payment-3-testing-12345-abcde", PaymentMethod.BY_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), validBankData);
        payments.add(payment3);
    }

    @Test
    void testSaveFromCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);

        Payment paymentResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertEquals(payment.getStatus(), paymentResult.getStatus());
        assertEquals(payment.getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testSaveFromEdit() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);
        Payment modifiedPayment = new Payment(payment.getId(), payment.getMethod(), PaymentStatus.SUCCESS.getValue(), payment.getPaymentData());
        Payment result = paymentRepository.save(modifiedPayment);

        Payment paymentResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), paymentResult.getStatus());
        assertEquals(payment.getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testFindByValidId() {
        for (Payment payment : payments){
            paymentRepository.save(payment);
        }

        Payment paymentResult = paymentRepository.findById(payments.get(2).getId());
        assertEquals(payments.get(2).getId(), paymentResult.getId());
        assertEquals(payments.get(2).getMethod(), paymentResult.getMethod());
        assertEquals(payments.get(2).getStatus(), paymentResult.getStatus());
        assertEquals(payments.get(2).getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testFindByNonexistentID() {
        for (Payment payment : payments){
            paymentRepository.save(payment);
        }

        Payment paymentResult = paymentRepository.findById("aowkaokawokawokawokwa");
        assertNull(paymentResult);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments){
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.findAll();
        assertEquals(paymentList.size(), payments.size());
    }
}