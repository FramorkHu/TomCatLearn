package com.learn.ex15.pyrmont.digestertest;

/**
 * Created by huyan on 2016/9/29.
 */
public class Office {


    Address address;
    String description;

    public Office(){
        System.out.println("create office");
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
