package com.learn.ex15.pyrmont.digestertest;

import org.apache.commons.digester.Digester;

import java.io.File;
import java.util.List;

/**
 * Created by huyan on 2016/9/29.
 */
public class Test02 {


    public static void main(String[] args) {

        String path = System.getProperty("user.dir")+ File.separator+"etc"+File.separator+"employee2.xml";

        File file = new File(path);

        Digester digester = new Digester();

        /**
         * className默认类名
         */
        digester.addObjectCreate("employee","com.learn.ex15.pyrmont.digestertest.Employee", "className");
        digester.addSetProperties("employee");

        digester.addObjectCreate("employee/office","com.learn.ex15.pyrmont.digestertest.Office");
        digester.addSetProperties("employee/office");
        digester.addSetNext("employee/office","addOffice");

        digester.addObjectCreate("employee/office/address","com.learn.ex15.pyrmont.digestertest.Address");
        digester.addSetProperties("employee/office/address");
        digester.addSetNext("employee/office/address","setAddress");

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
