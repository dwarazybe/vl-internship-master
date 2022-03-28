package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        basket.getProducts().forEach(product -> {   // adds products to receipt entries and counts each product appearing frequency 
            receiptEntries.add(new ReceiptEntry(product, Collections.frequency(basket.getProducts(), product)));
        });
        return new Receipt(receiptEntries.stream().distinct().collect(Collectors.toList())); // removes duplicate products 
    }
}
