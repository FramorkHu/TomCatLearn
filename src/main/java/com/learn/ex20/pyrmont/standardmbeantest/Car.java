package com.learn.ex20.pyrmont.standardmbeantest;

/**
 * Created by huyan on 2016/10/11.
 */
public class Car implements CarMBean {

    private String color = "red";

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void drive(){
        System.out.println("you can drive my car");
    }
}
