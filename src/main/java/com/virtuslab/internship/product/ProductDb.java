package com.virtuslab.internship.product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductDb {

    private final Set<Product> products;

    public ProductDb() {
        products = Stream.of(
                new Product(1, "Apple", Product.Type.FRUITS, new BigDecimal(2)),
                new Product(2, "Orange", Product.Type.FRUITS, new BigDecimal(5)),
                new Product(3, "Banana", Product.Type.FRUITS, new BigDecimal("4.4")),
                new Product(4, "Potato", Product.Type.VEGETABLES, new BigDecimal("1.2")),
                new Product(5, "Tomato", Product.Type.VEGETABLES, new BigDecimal(7)),
                new Product(6, "Onion", Product.Type.VEGETABLES, new BigDecimal("1.7")),
                new Product(7, "Milk", Product.Type.DAIRY, new BigDecimal("2.7")),
                new Product(8, "Cheese", Product.Type.DAIRY, new BigDecimal("20.5")),
                new Product(9, "Butter", Product.Type.DAIRY, new BigDecimal(7)),
                new Product(10, "Pork", Product.Type.MEAT, new BigDecimal(16)),
                new Product(11, "Steak", Product.Type.MEAT, new BigDecimal(50)),
                new Product(12, "Bread", Product.Type.GRAINS, new BigDecimal(5)),
                new Product(13, "Cereals", Product.Type.GRAINS,new BigDecimal(8))
        ).collect(Collectors.toSet());
    }

    public Product getProductByName(String productName) {
        return products.stream()
                .filter(product -> productName.equals(product.name()))
                .findFirst()
                .get();
    }

    public Product getProductById(Integer productId) {
        return products.stream()
                .filter(product -> productId.equals(product.id()))
                .findFirst()
                .get();
    }
}
