package com.virtuslab.internship.product;

import java.math.BigDecimal;

public record Product(
        Integer id,
        String name,
        Type type,
        BigDecimal price
) {
    public enum Type {
        DAIRY, FRUITS, VEGETABLES, MEAT, GRAINS
    }
}
