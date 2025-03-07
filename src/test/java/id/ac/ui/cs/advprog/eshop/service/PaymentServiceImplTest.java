package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        this.payments = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1709560000L, "Safira Sudrajat");

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
    void testAddPaymentIfAlreadySucceed() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).findById(order.getId());

        assertNull(paymentService.addPayment(this.order, payment.getMethod(), payment.getPaymentData()));
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    void testUpdateInvalidStatus() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "GAKBISA");
        });

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateNonexistentPayment() {
        Payment payment = new Payment("DAUN", PaymentMethod.BY_TRANSFER.getValue(), PaymentStatus.REJECTED.getValue(), new HashMap<String, String>());
        doReturn(null).when(paymentRepository).findById(payment.getId());

        assertThrows(NoSuchElementException.class, () -> {
            paymentService.setStatus(payment, "GAKBISA");
        });

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentById() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testGetPaymentByNonexistentId() {
        doReturn(null).when(paymentRepository).findById("unknown-id-payment-testing");
        assertNull(paymentService.getPayment("unknown-id-payment-testing"));
    }

    @Test

    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.getAllPayments();
        assertEquals(payments.size(), results.size());
    }
}