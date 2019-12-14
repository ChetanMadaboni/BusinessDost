package com.example.testapp;

public class Purchaseslist {
    private String PurchasedProductName;
    private String PurchasedDate;
    private  String PurchasedPrice;
    private  String PurchasedQuantity;
    private  String PurchasedVendorName;
    public Purchaseslist()
    {

    }

    public Purchaseslist(String purchasedProductName, String purchasedDate, String purchasedPrice, String purchasedQuantity) {
        PurchasedProductName = purchasedProductName;
        PurchasedDate = purchasedDate;
        PurchasedPrice = purchasedPrice;
        PurchasedQuantity = purchasedQuantity;
    }

    public String getPurchasedProductName() {
        return PurchasedProductName;
    }

    public void setPurchasedProductName(String purchasedProductName) {
        PurchasedProductName = purchasedProductName;
    }

    public String getPurchasedDate() {
        return PurchasedDate;
    }

    public void setPurchasedDate(String purchasedDate) {
        PurchasedDate = purchasedDate;
    }

    public String getPurchasedPrice() {
        return PurchasedPrice;
    }

    public void setPurchasedPrice(String purchasedPrice) {
        PurchasedPrice = purchasedPrice;
    }

    public String getPurchasedQuantity() {
        return PurchasedQuantity;
    }

    public void setPurchasedQuantity(String purchasedQuantity) {
        PurchasedQuantity = purchasedQuantity;
    }

    public Purchaseslist(String purchasedVendorName) {
        PurchasedVendorName = purchasedVendorName;
    }

    public String getPurchasedVendorName() {
        return PurchasedVendorName;
    }

    public void setPurchasedVendorName(String purchasedVendorName) {
        PurchasedVendorName = purchasedVendorName;
    }
}
