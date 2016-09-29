package com.learn.ex15.pyrmont.digestertest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyan on 2016/9/29.
 */
public class Employee {

    private String firstName;
    private String lastName;
    private List<Office> offices = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public void addOffice(Office office){
        System.out.println("add office");
        offices.add(office);
    }

    public void printName(){
        System.out.println("My Name is "+firstName+" "+lastName);
    }
}
