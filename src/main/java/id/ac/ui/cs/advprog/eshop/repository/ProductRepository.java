package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID().toString());
        }

        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            product.setProductName("Product name is empty");
        }

        if (product.getProductQuantity() < 0) {
            product.setProductQuantity(0);
        }

        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Product edit(Product editedProduct) {
        if (editedProduct.getProductId() == null) return null;

        if (editedProduct.getProductName() == null || editedProduct.getProductName().trim().isEmpty()) {
            editedProduct.setProductName("Product name is empty");
        }

        if (editedProduct.getProductQuantity() < 0) {
            editedProduct.setProductQuantity(0);
        }

        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(editedProduct.getProductId())) {
                productData.set(i, editedProduct);
                return editedProduct;
            }
        }
        return null;
    }
}