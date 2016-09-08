package com.learn.ex05.pyrmont.startup;

import com.learn.ex05.pyrmont.core.SimpleLoader;
import com.learn.ex05.pyrmont.core.SimpleWrapper;
import com.learn.ex05.pyrmont.valve.ClientIpLoggerValve;
import com.learn.ex05.pyrmont.valve.HeaderLoggerValve;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Loader;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * Created by huyan on 2016/9/8.
 */
public class Bootstrap {

    public static void main(String[] args) throws LifecycleException, IOException {

        HttpConnector connector = new HttpConnector();
        SimpleWrapper simpleWrapper = new SimpleWrapper();
        connector.setContainer(simpleWrapper);

        Loader loader = new SimpleLoader();
        simpleWrapper.setLoader(loader);
        simpleWrapper.setServletClass("PrimitiveServlet");
        Valve ipValve = new ClientIpLoggerValve();
        Valve headerValve = new HeaderLoggerValve();

        Pipeline pipeline = simpleWrapper.getPipeline();
        pipeline.addValve(ipValve);
        pipeline.addValve(headerValve);

        connector.initialize();

        connector.start();

        System.in.read();
    }
}
