package com.learn.ex05.pyrmont.valve;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * Created by huyan on 2016/9/8.
 */
public class ClientIpLoggerValve implements Valve, Contained {

    private Container container;


    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {

        context.invokeNext(request, response);

        System.out.println("client Ip Logger Valve");
        ServletRequest servletRequest = request.getRequest();
        System.out.println("host:"+servletRequest.getRemoteAddr());

        System.out.println("--------------------------------------");
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
