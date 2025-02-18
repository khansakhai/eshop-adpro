package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        verify(productRepository).create(product);
        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals(product.getProductName(), result.getProductName());
        assertEquals(product.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("1");
        Product product2 = new Product();
        product2.setProductId("2");
        productList.add(product1);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        verify(productRepository).findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1.getProductId(), result.get(0).getProductId());
        assertEquals(product2.getProductId(), result.get(1).getProductId());
    }

    @Test
    void testFindById() {
        String productId = "test-id";
        Product product = new Product();
        product.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(product);

        Product result = productService.findById(productId);

        verify(productRepository).findById(productId);
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
    }

    @Test
    void testEdit() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Updated Name");
        product.setProductQuantity(200);

        when(productRepository.edit(product)).thenReturn(product);

        Product result = productService.edit(product);

        verify(productRepository).edit(product);
        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals(product.getProductName(), result.getProductName());
        assertEquals(product.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void testDelete() {
        String productId = "test-id";
        when(productRepository.delete(productId)).thenReturn(true);

        boolean result = productService.delete(productId);

        verify(productRepository).delete(productId);
        assertTrue(result);
    }

    @Test
    void testDelete_Failure() {
        String productId = "non-existent-id";
        when(productRepository.delete(productId)).thenReturn(false);

        boolean result = productService.delete(productId);

        verify(productRepository).delete(productId);
        assertFalse(result);
    }
}