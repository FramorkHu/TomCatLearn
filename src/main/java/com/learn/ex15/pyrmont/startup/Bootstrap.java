package com.learn.ex15.pyrmont.startup;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.core.StandardService;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.ContextConfig;

/**
 * Created by huyan on 2016/9/27.
 */
public class Bootstrap {

    public static void main(String[] args) {

        System.setProperty("catalina.base", System.getProperty("user.dir"));

        Connector connector = new HttpConnector();
        Loader loader = new WebappLoader();

        Context context = new StandardContext();
        LifecycleListener contextConfig = new ContextConfig();
        ((Lifecycle)context).addLifecycleListener(contextConfig);
        context.setPath("/app1");
        context.setDocBase("app1");
        context.setLoader(loader);

        Host host = new StandardHost();
        host.setName("localhost");
        host.setAppBase("webapp");

        Server server = new StandardServer();
        Service service = new StandardService();
        service.addConnector(connector);
        service.setContainer(host);

        server.addService(service);

        try {
            server.initialize();
            ((Lifecycle)server).start();
            server.await();

            ((Lifecycle) server).stop();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

}
