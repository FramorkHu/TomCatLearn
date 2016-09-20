package com.learn.ex06.pyrmont.startup;

import com.learn.ex06.pyrmont.core.*;
import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Mapper;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

public final class Bootstrap {
  public static void main(String[] args) {
      Connector connector = new HttpConnector();
      Wrapper modernWrapper = new SimpleWrapper();
      modernWrapper.setName("modern");
      modernWrapper.setServletClass("ModernServlet");

      Wrapper primitiveWrapper = new SimpleWrapper();
      primitiveWrapper.setName("primitive");
      primitiveWrapper.setServletClass("PrimitiveServlet");

      Context context = new SimpleContext();
      context.addChild(modernWrapper);
      context.addChild(primitiveWrapper);

      Mapper mapper = new SimpleContextMapper();
      mapper.setProtocol("HTTP/1.1");
      LifecycleListener listener = new SimpleContextLifecycleListener();
      ((Lifecycle) context).addLifecycleListener(listener);

    context.addMapper(mapper);
    Loader loader = new SimpleLoader();
    context.setLoader(loader);

    context.addServletMapping("/PrimitiveServlet", primitiveWrapper.getName());
    context.addServletMapping("/ModernServlet", modernWrapper.getName());
    connector.setContainer(context);
    try {
      connector.initialize();
      ((Lifecycle) connector).start();
      ((Lifecycle) context).start();

      // make the application wait until we press a key.
      System.in.read();
      ((Lifecycle) context).stop();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}