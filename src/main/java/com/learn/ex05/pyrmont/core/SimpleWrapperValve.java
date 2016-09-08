package com.learn.ex05.pyrmont.core;

import org.apache.catalina.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huyan on 2016/9/8.
 */
public class SimpleWrapperValve implements Valve {

    private Container container;

    public SimpleWrapperValve(Container container){
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {

        SimpleWrapper simpleWrapper = (SimpleWrapper)getContainer();

        ServletRequest servletRequest = request.getRequest();
        ServletResponse servletResponse = response.getResponse();

        Servlet servlet;

        HttpServletRequest hreq = null;
        HttpServletResponse hres = null;

        if (servletRequest instanceof HttpServletRequest){
            hreq = (HttpServletRequest) servletRequest;
        }
        if (servletResponse instanceof HttpServletResponse){
            hres = (HttpServletResponse) servletResponse;
        }

        servlet = simpleWrapper.allocate();

        System.out.println("service is run ---------------");
        if (hreq != null &&
                hres != null){
            servlet.service(hreq, hres);
        } else {
            servlet.service(servletRequest, servletResponse);
        }

    }

    public Container getContainer() {
        return container;
    }
}
