package com.example.testapp;

public class CustomerList {


    private String CustName;
    private String CustAddress;
    private  String CustPhoneNo;
    public CustomerList()
    {

    }

    public CustomerList(String custName, String custAddress, String custPhoneNo) {
        CustName = custName;
        CustAddress = custAddress;
        CustPhoneNo = custPhoneNo;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
    }

    public String getCustPhoneNo() {
        return CustPhoneNo;
    }

    public void setCustPhoneNo(String custPhoneNo) {
        CustPhoneNo = custPhoneNo;
    }
}
