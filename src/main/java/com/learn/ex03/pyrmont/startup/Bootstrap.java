package com.learn.ex03.pyrmont.startup;

import com.learn.ex03.pyrmont.connector.http.HttpConnector;

/**
 * Created by huyan on 2016/9/7.
 */
public class Bootstrap {

    public static void main(String args[]){
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
