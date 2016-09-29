package com.learn.ex15.pyrmont.digestertest;

/**
 * Created by huyan on 2016/9/29.
 */
public class Address {

    private String streetName;
    private String streetNumber;

    public Address(){
        System.out.println("create Address");
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
