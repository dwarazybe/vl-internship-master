package com.virtuslab.internship.receipt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record Receipt(
        List<ReceiptEntry> entries,
        List<String> discounts,
        BigDecimal totalPrice) {

    public Receipt(List<ReceiptEntry> entries) {
        this(entries,
                new ArrayList<String>(), // this param cannot be null - should be new list instead
                entries.stream()
                        .map(ReceiptEntry::totalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    public List<ReceiptEntry> getEntries() {
        return entries;
    }

    public List<String> getDiscounts() {
        return discounts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
