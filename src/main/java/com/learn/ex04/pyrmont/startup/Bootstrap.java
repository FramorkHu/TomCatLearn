package com.learn.ex04.pyrmont.startup;

import com.learn.ex04.pyrmont.core.SimpleContainer;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by huyan on 2016/9/7.
 */
public class Bootstrap {

    public static void main(String[] args) throws LifecycleException, IOException {

        Locale.setDefault(Locale.ENGLISH);
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.setContainer(new SimpleContainer());
        httpConnector.setPort(8888);
        httpConnector.initialize();
        httpConnector.start();


        System.out.println((char)System.in.read());

    }

}
