package com.example.testapp;

public class VendorList {
    private String VendorName;
    private String VendorPhoneNo;
    private String VendorAddress;
    private String VendorAvProduct;
    public VendorList()
    {

    }

    public VendorList(String vendorName, String vendorPhoneNo, String vendorAddress, String vendorAvProduct) {
        VendorName = vendorName;
        VendorPhoneNo = vendorPhoneNo;
        VendorAddress = vendorAddress;
        VendorAvProduct = vendorAvProduct;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getVendorPhoneNo() {
        return VendorPhoneNo;
    }

    public void setVendorPhoneNo(String vendorPhoneNo) {
        VendorPhoneNo = vendorPhoneNo;
    }

    public String getVendorAddress() {
        return VendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        VendorAddress = vendorAddress;
    }

    public String getVendorAvProduct() {
        return VendorAvProduct;
    }

    public void setVendorAvProduct(String vendorAvProduct) {
        VendorAvProduct = vendorAvProduct;
    }
}
