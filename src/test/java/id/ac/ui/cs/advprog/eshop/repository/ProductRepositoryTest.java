package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateAndEdit() {
        Product product = new Product();
        product.setProductId("abcdefghijk1234567890");
        product.setProductName("Bunga Lily");
        product.setProductQuantity(75);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("abcdefghijk1234567890");
        updatedProduct.setProductName("Bunga Lily Updated");
        updatedProduct.setProductQuantity(150);
        productRepository.edit(updatedProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        Product savedProduct = productIterator.next();
        assertEquals(updatedProduct.getProductName(), savedProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEditNonExistentProduct() {
        Product product = new Product();
        product.setProductId("1234567890abcdefghijk");
        product.setProductName("Buku Novel");
        product.setProductQuantity(170);

        Product editedProduct = productRepository.edit(product);
        assertNull(editedProduct);
    }

    @Test
    void testEditProductWithNullId() {
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("Benang Rajut");
        product.setProductQuantity(10);

        Product editedProduct = productRepository.edit(product);
        assertNull(editedProduct);
    }

    @Test
    void testCreateAndDelete() {
        Product product = new Product();
        product.setProductId("qwertyuiop123");
        product.setProductName("Lilin Aromaterapi");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        productRepository.delete("qwertyuiop123");
        productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonexistentProduct() {
        Product product = new Product();
        product.setProductId("123qwertyuiop");
        product.setProductName("Perangko");
        product.setProductQuantity(65);

        boolean isDeleted = productRepository.delete(product.getProductId());
        assertFalse(isDeleted);
    }

    @Test
    void testEditAndDeleteProduct() {
        Product product = new Product();
        product.setProductId("asdfghjkl12345");
        product.setProductName("Buah Stroberi");
        product.setProductQuantity(30);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("asdfghjkl12345");
        updatedProduct.setProductName("Buah Stroberi Edited");
        updatedProduct.setProductQuantity(60);
        productRepository.edit(updatedProduct);

        boolean isDeleted = productRepository.delete("asdfghjkl12345");
        assertTrue(isDeleted);
    }

    @Test
    void testCreateProductWithNullId() {
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        Product savedProduct = productRepository.create(product);
        assertNotNull(savedProduct.getProductId());
    }

    @Test
    void testCreateProductWithNullName() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName(null);
        product.setProductQuantity(10);

        Product savedProduct = productRepository.create(product);
        assertEquals("Product name is empty", savedProduct.getProductName());
    }

    @Test
    void testCreateProductWithEmptyName() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("   ");
        product.setProductQuantity(10);

        Product savedProduct = productRepository.create(product);
        assertEquals("Product name is empty", savedProduct.getProductName());
    }

    @Test
    void testCreateProductWithNegativeQuantity() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(-5);

        Product savedProduct = productRepository.create(product);
        assertEquals(0, savedProduct.getProductQuantity());
    }

    @Test
    void testFindByIdWithExistingProduct() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("test-id");
        assertNotNull(foundProduct);
        assertEquals("test-id", foundProduct.getProductId());
    }

    @Test
    void testFindByIdWithNonExistingProduct() {
        Product foundProduct = productRepository.findById("non-existent-id");
        assertNull(foundProduct);
    }

    @Test
    void testEditProductWithNullName() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product editedProduct = new Product();
        editedProduct.setProductId("test-id");
        editedProduct.setProductName(null);
        editedProduct.setProductQuantity(10);

        Product result = productRepository.edit(editedProduct);
        assertEquals("Product name is empty", result.getProductName());
    }

    @Test
    void testEditProductWithEmptyName() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product editedProduct = new Product();
        editedProduct.setProductId("test-id");
        editedProduct.setProductName("   ");
        editedProduct.setProductQuantity(10);

        Product result = productRepository.edit(editedProduct);
        assertEquals("Product name is empty", result.getProductName());
    }

    @Test
    void testEditProductWithNegativeQuantity() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product editedProduct = new Product();
        editedProduct.setProductId("test-id");
        editedProduct.setProductName("Test Product");
        editedProduct.setProductQuantity(-5);

        Product result = productRepository.edit(editedProduct);
        assertEquals(0, result.getProductQuantity());
    }

    @Test
    void testEditProductEmptyList() {
        Product editedProduct = new Product();
        editedProduct.setProductId("test-id");
        editedProduct.setProductName("Test Product");
        editedProduct.setProductQuantity(10);

        Product result = productRepository.edit(editedProduct);
        assertNull(result);
    }

    @Test
    void testEditProductDifferentId() {
        Product product = new Product();
        product.setProductId("test-id-1");
        product.setProductName("Original Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product editedProduct = new Product();
        editedProduct.setProductId("test-id-2");
        editedProduct.setProductName("Updated Product");
        editedProduct.setProductQuantity(20);

        Product result = productRepository.edit(editedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductEmptyList() {
        boolean result = productRepository.delete("any-id");
        assertFalse(result);
    }

    @Test
    void testDeleteProductIteratorComplete() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        boolean result = productRepository.delete("non-existent-id");
        assertFalse(result);

        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());
        Product remainingProduct = iterator.next();
        assertEquals("test-id", remainingProduct.getProductId());
    }
}
