package com.learn.ex15.pyrmont.digestertest;

import org.apache.commons.digester.Digester;

import java.io.File;
import java.util.List;

/**
 * Created by huyan on 2016/9/29.
 */
public class Test03 {

    public static void main(String[] args) {

        String path = System.getProperty("user.dir")+ File.separator+"etc"+File.separator+"employee2.xml";

        File file = new File(path);

        Digester digester = new Digester();
        digester.addRuleSet(new EmployeeRuleSet());

        try {

            Employee employee = (Employee)digester.parse(file);

            List<Office> offices = employee.getOffices();

            System.out.println("-----------------------------------------");
            for (Office office : offices){
                System.out.println(office.getDescription());
                Address address = office.getAddress();
                System.out.println("Address:"+ address.getStreetName()+" "+address.getStreetNumber());

                System.out.println("-----------------------------------------");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
