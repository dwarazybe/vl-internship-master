package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.TotalDiscount;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class BasketController {

    private ProductDb productDb = new ProductDb();

    /* creates cart for session */
    @RequestMapping("/")
    public String index(HttpSession session) {
        session.setAttribute("cart", new Basket());
        return "Cart has been created successfully";
    }

    /* adds product specified by id and multiplied by its quantity to the cart */
    @PostMapping("/cart/add/{id}/{quantity}")
    public String addProductToCart(HttpSession session, @PathVariable("id") Integer id,
                                   @PathVariable("quantity") Integer quantity) {
        var product = productDb.getProductById(id);
        Basket cart = (Basket) session.getAttribute("cart");
        for(Integer i = 0; i < quantity; i++) cart.addProduct(product);
        return quantity + "x " + product.name() + " added to cart";
    }

    /* displays all products stored in cart */
    @PostMapping("/cart/display")
    public String displayCartProducts(HttpSession session) {
        Basket cart = (Basket) session.getAttribute("cart");
        return cart.getProducts().toString();
    }

    /* returns the receipt data */
    @PostMapping("/cart/receipt")
    public Receipt printReceipt(HttpSession session) {
        Basket cart = (Basket) session.getAttribute("cart");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for(Product product : cart.getProducts()) {
            if(!receiptEntries.stream().anyMatch(p -> product.id().equals(p.product().id())))
                receiptEntries.add(new ReceiptEntry(product, Collections.frequency(cart.getProducts(), product)));
        }
        var receipt = new Receipt(receiptEntries);
        var discount = new TotalDiscount();
        var receiptAfterDiscount = discount.apply(receipt);

        // System.out.println("Entries: " + receiptAfterDiscount.getEntries());
        // System.out.println("Discounts (" + receiptAfterDiscount.getDiscounts().size() + "): " + receiptAfterDiscount.getDiscounts());
        // System.out.println("Total price: " + receiptAfterDiscount.getTotalPrice());

        return receiptAfterDiscount;
    }
}
