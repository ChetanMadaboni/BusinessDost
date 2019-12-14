package com.example.testapp;

public class SalesList {
    private String CustomerName;
    private String ProductName;
    private String SaleDate;
    private String SalePrice;
    private String SaleQuantity;
    public SalesList()
    {

    }

    public SalesList(String customerName, String productName, String saleDate, String salePrice, String saleQuantity) {
        CustomerName = customerName;
        ProductName = productName;
        SaleDate = saleDate;
        SalePrice = salePrice;
        SaleQuantity = saleQuantity;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSaleDate() {
        return SaleDate;
    }

    public void setSaleDate(String saleDate) {
        SaleDate = saleDate;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public String getSaleQuantity() {
        return SaleQuantity;
    }

    public void setSaleQuantity(String saleQuantity) {
        SaleQuantity = saleQuantity;
    }
}
