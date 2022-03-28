package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public class TotalDiscount {
    
    public Receipt apply(Receipt receipt) {
        var grainDiscount = new GrainProductsDiscount();
        var tenPercentDiscount = new TenPercentDiscount();
        
        return tenPercentDiscount.apply(grainDiscount.apply(receipt));
    }
}
