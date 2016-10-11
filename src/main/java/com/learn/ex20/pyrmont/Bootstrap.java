package com.learn.ex20.pyrmont;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.*;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.HostConfig;

/**
 * Created by huyan on 2016/9/27.
 */
public class Bootstrap {

    public static void main(String[] args) {

        System.setProperty("catalina.base", System.getProperty("user.dir"));

        Connector connector = new HttpConnector();
        Loader loader = new WebappLoader();

        Engine engine = new StandardEngine();
        engine.setName("Catalina");
        engine.setDefaultHost("localhost");
        engine.setLoader(loader);

        Host host = new StandardHost();
        host.setName("localhost");
        host.setAppBase("webapp");
        ((Lifecycle)host).addLifecycleListener(new HostConfig());

        //host.setLoader(loader);

        engine.addChild(host);

        Server server = new StandardServer();
        Service service = new StandardService();
        service.addConnector(connector);
        service.setContainer(engine);

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
