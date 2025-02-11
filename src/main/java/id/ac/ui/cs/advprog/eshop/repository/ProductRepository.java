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
        product.setProductId(UUID.randomUUID().toString());
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

        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(editedProduct.getProductId())) {
                productData.set(i, editedProduct);
                return editedProduct;
            }
        }
        return null;
    }
}