package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;

public class GrainProductsDiscount {

    public static String NAME = "GrainProductsDiscount";

    /* applies a discount */
    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    /* checks if there are at least 3 grain products */
    private boolean shouldApply(Receipt receipt) {   
        int grainProductsCounter = 0;
        for(ReceiptEntry entry : receipt.entries()) {
            if(entry.product().type().equals(Product.Type.GRAINS)) {
                grainProductsCounter += entry.quantity();
            }
        }
        return grainProductsCounter >= 3;
    }
}
