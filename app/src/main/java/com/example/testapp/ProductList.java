package com.example.testapp;

public class ProductList {
    private String ProductName;
    private String ProductDescription;
    private  String Image;
    public ProductList()
    {

    }

    public ProductList(String productname, String productDescription, String image) {
        ProductName = productname;
        ProductDescription = productDescription;
        Image = image;
    }

    public String getProductname() {
        return ProductName;
    }

    public void setProductname(String productname) {
        ProductName = productname;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
