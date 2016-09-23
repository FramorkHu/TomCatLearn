package com.learn.ex09.pyrmont.startup;

import com.learn.ex08.pyrmont.core.SimpleContextConfig;
import com.learn.ex09.pyrmont.core.SimpleWrapper;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.catalina.loader.WebappLoader;
import org.apache.naming.resources.ProxyDirContext;

/**
 * Created by huyan on 2016/9/21.
 */
public class Bootstrap {


    public static void main(String[] args) {

        System.setProperty("catalina.base", System.getProperty("user.dir"));

        Connector connector = new HttpConnector();
        Wrapper modernWrapper = new SimpleWrapper();
        modernWrapper.setName("modern");
        modernWrapper.setServletClass("ModernServlet");

        Wrapper primitiveWrapper = new SimpleWrapper();
        primitiveWrapper.setName("primitive");
        primitiveWrapper.setServletClass("PrimitiveServlet");

        Wrapper sessionWrapper = new SimpleWrapper();
        sessionWrapper.setName("session");
        sessionWrapper.setServletClass("SessionServlet");

        Loader loader = new WebappLoader();

        Context context = new StandardContext();

        context.setPath("/myApp");
        context.setDocBase("myApp");
        context.addChild(modernWrapper);
        context.addChild(primitiveWrapper);
        context.addChild(sessionWrapper);

        context.addServletMapping("/ModernServlet", modernWrapper.getName());
        context.addServletMapping("/PrimitiveServlet", primitiveWrapper.getName());
        context.addServletMapping("/myApp/SessionServlet", sessionWrapper.getName());
        context.setLoader(loader);


        LifecycleListener contextConfig = new SimpleContextConfig();
        ((Lifecycle)context).addLifecycleListener(contextConfig);

        connector.setContainer(context);

        try {

            connector.initialize();

            ((Lifecycle)connector).start();
            ((Lifecycle)context ).start();

            /*WebappClassLoader classLoader = (WebappClassLoader)loader.getClassLoader();

            System.out.println("Resource docBase "+ ((ProxyDirContext)classLoader.getResources()).getDocBase());
            String[] repositories =  classLoader.findRepositories();
            for (String repos : repositories){
                System.out.println("repository:" + repos);
            }
*/
            System.in.read();

            ((Lifecycle)context ).stop();
        } catch (Exception e){

        }
    }
}
