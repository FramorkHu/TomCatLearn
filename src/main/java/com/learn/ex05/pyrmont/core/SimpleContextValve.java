package com.learn.ex05.pyrmont.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huyan on 2016/9/8.
 */
public class SimpleContextValve implements Valve, Contained {

    private Container container;

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {

        ServletRequest servletRequest = request.getRequest();
        ServletResponse servletResponse = response.getResponse();

        if ( !(servletRequest instanceof HttpServletRequest) &&
                !(servletResponse instanceof HttpServletResponse)){

            return;
        }

        HttpServletRequest hreq = (HttpServletRequest)servletRequest;
        HttpServletResponse hres = (HttpServletResponse)servletResponse;

        String contextPath = hreq.getContextPath();
        String uri = ((HttpRequest)request).getDecodedRequestURI();
        String relativeURI = uri.substring(contextPath.length()).toUpperCase();

        Context context = (Context)getContainer();

        Wrapper wrapper = (Wrapper) context.map(request, true);

        if (wrapper ==null){

        }
        response.setContext(context);
        wrapper.invoke(request, response);


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
