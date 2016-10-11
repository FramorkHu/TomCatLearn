package com.learn.ex15.pyrmont.digestertest;

import org.apache.catalina.Context;
import org.apache.catalina.startup.ContextRuleSet;
import org.apache.commons.digester.Digester;

import java.io.File;
import java.util.List;

/**
 * Created by huyan on 2016/9/29.
 */
public class Test04 {


    public static void main(String[] args) {

        String path = System.getProperty("user.dir")+ File.separator+"webapp"+File.separator+"myadmin.xml";

        File file = new File(path);

        Digester digester = new Digester();

        /**
         * className默认类名
         */
        digester.addRuleSet(new ContextRuleSet(""));
        digester.addObjectCreate("Context",
                "org.apache.catalina.core.StandardContext",
                "className");
        digester.addSetProperties("Context");

        try {

            Context employee = (Context)digester.parse(file);

            System.out.println(employee.getDocBase());


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
