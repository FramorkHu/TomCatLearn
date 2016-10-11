package com.learn.ex14.pyrmont.startup;

import com.learn.ex13.pyrmont.core.SimpleContextConfig;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.*;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.EngineConfig;

import java.io.File;
import java.io.IOException;

/**
 * Created by huyan on 2016/9/23.
 */
public class Bootstrap {
    
    public static void main(String[] args) throws IOException {
        System.setProperty("catalina.base", System.getProperty("user.dir"));

        File file = new File(System.getProperty("catalina.base"),"webapp");
        String filePaht = file.getAbsolutePath();//.getCanonicalPath();
        String[] datas = file.list();

        System.setProperty("catalina.base", System.getProperty("user.dir"));
        Connector connector = new HttpConnector();

        Wrapper wrapper1 = new StandardWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new StandardWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context context = new StandardContext();
        // StandardContext's start method adds a default mapper
        context.setPath("/app1");
        context.setDocBase("app1");

        context.addChild(wrapper1);
        context.addChild(wrapper2);

        LifecycleListener listener = new SimpleContextConfig();
        ((Lifecycle) context).addLifecycleListener(listener);

        Host host = new StandardHost();
        host.addChild(context);
        host.setName("aaa");
        host.setAppBase("webapp");

        Loader loader = new WebappLoader();
        context.setLoader(loader);

        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");

        Server server = new StandardServer();
        Service service = new StandardService();
        service.setName("Stand-alone Service");
        service.addConnector(connector);
        service.setContainer(host);

        server.addService(service);
        Runtime.getRuntime().addShutdownHook(new ShutDownThread());

        try {
            server.initialize();
            ((Lifecycle)server).start();
            server.await();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ((Lifecycle)server).stop();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}

class ShutDownThread extends Thread{

    @Override
    public void run() {
        System.out.println("exit");
    }
}