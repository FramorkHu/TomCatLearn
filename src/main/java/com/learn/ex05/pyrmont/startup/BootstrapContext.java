package com.learn.ex05.pyrmont.startup;

import com.learn.ex05.pyrmont.core.SimpleContext;
import com.learn.ex05.pyrmont.core.SimpleContextMapper;
import com.learn.ex05.pyrmont.core.SimpleLoader;
import com.learn.ex05.pyrmont.core.SimpleWrapper;
import com.learn.ex05.pyrmont.valve.ClientIpLoggerValve;
import com.learn.ex05.pyrmont.valve.HeaderLoggerValve;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * Created by huyan on 2016/9/9.
 */
public class BootstrapContext {

    public static void main(String[] args) throws LifecycleException, IOException {
        HttpConnector httpConnector = new HttpConnector();

        Wrapper modernWrapper = new SimpleWrapper();
        modernWrapper.setName("modern");
        modernWrapper.setServletClass("ModernServlet");

        Wrapper primitiveWrapper = new SimpleWrapper();
        primitiveWrapper.setName("primitive");
        primitiveWrapper.setServletClass("PrimitiveServlet");

        Context context = new SimpleContext();

        Valve ipValve = new ClientIpLoggerValve();
        Valve headerValve = new HeaderLoggerValve();
        ((Pipeline)context).addValve(ipValve);
        ((Pipeline)context).addValve(headerValve);


        Loader loader = new SimpleLoader();

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("HTTP/1.1");


        context.addChild(modernWrapper);
        context.addChild(primitiveWrapper);
        context.addServletMapping("/ModernServlet", modernWrapper.getName());
        context.addServletMapping("/PrimitiveServlet", primitiveWrapper.getName());
        context.addMapper(mapper);
        context.setLoader(loader);

        httpConnector.setContainer(context);


        httpConnector.initialize();
        httpConnector.start();

        System.in.read();



    }
}
