package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalDiscountTest {

    @Test
    void shouldApplyBothDiscounts() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProductByName("Bread");
        var cereals = productDb.getProductByName("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 7));
        receiptEntries.add(new ReceiptEntry(cereals, 4));
        
        var receipt = new Receipt(receiptEntries);
        var discount = new TotalDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(7)).add(cereals.price().multiply(BigDecimal.valueOf(4))).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discount.apply(receipt);
        
        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }
    
    @Test
    void shouldApplyOneDiscountWhenThereAreNotEnoughGrainProducts() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProductByName("Bread");
        var cereals = productDb.getProductByName("Cereals");
        var banana = productDb.getProductByName("Banana");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(banana, 20));
        
        var receipt = new Receipt(receiptEntries);
        var discount = new TotalDiscount();
        var expectedTotalPrice = bread.price().add(cereals.price()).add(banana.price().multiply(BigDecimal.valueOf(20))).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discount.apply(receipt);
        
        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
    
    @Test
    void shouldApplyOneDiscountWhenPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProductByName("Bread");
        var cereals = productDb.getProductByName("Cereals");
        var banana = productDb.getProductByName("Banana");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(banana, 1));
        
        var receipt = new Receipt(receiptEntries);
        var discount = new TotalDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(2)).add(cereals.price()).add(banana.price()).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discount.apply(receipt);
        
        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
    
    @Test
    void shouldNotApplyBothDiscountsWhenThereAreNotEnoughGrainProductsAndPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProductByName("Bread");
        var butter = productDb.getProductByName("Butter");
        var banana = productDb.getProductByName("Banana");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(butter, 1));
        receiptEntries.add(new ReceiptEntry(banana, 1));
        
        var receipt = new Receipt(receiptEntries);
        var discount = new TotalDiscount();
        var expectedTotalPrice = bread.price().add(butter.price()).add(banana.price());

        // When
        var receiptAfterDiscount = discount.apply(receipt);
        
        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}
