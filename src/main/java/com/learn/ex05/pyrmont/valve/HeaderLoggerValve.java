package com.learn.ex05.pyrmont.valve;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by huyan on 2016/9/8.
 */
public class HeaderLoggerValve implements Valve, Contained {

    private Container container;

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {

        context.invokeNext(request, response);
        ServletRequest servletRequest = request.getRequest();

        HttpServletRequest hreq;
        if (servletRequest instanceof HttpServletRequest){
            hreq = (HttpServletRequest)servletRequest;

            Enumeration headerInfos = hreq.getHeaderNames();

            while (headerInfos.hasMoreElements()){
                String headerNames = headerInfos.nextElement().toString();
                String headerValue = hreq.getHeader(headerNames);
                System.out.println(headerNames+":"+headerValue);
            }
        } else {
            System.out.println("no http request header");
        }

        System.out.println("--------------------------------------");
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
