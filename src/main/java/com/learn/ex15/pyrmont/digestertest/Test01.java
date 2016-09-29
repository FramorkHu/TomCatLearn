package com.learn.ex15.pyrmont.digestertest;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * Created by huyan on 2016/9/29.
 */
public class Test01 {

    public static void main(String[] args) {

        String path = System.getProperty("user.dir")+ File.separator+"etc"+File.separator+"employee.xml";

        File file = new File(path);
        Digester digester = new Digester();

        digester.addObjectCreate("employee", "com.learn.ex15.pyrmont.digestertest.Employee");
        digester.addSetProperties("employee");
        digester.addCallMethod("employee", "printName");

        try {
            Employee employee = (Employee)digester.parse(file);
            System.out.println("firstName:"+employee.getFirstName()+" last Name:"+ employee.getLastName());

        } catch (Exception e){
            e.printStackTrace();
        }



    }
}
