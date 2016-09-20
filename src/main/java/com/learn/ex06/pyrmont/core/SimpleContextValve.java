package com.learn.ex06.pyrmont.core;

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

    public SimpleContextValve(Context container){
        this.container = container;
    }
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

        String requestURI = ((HttpRequest) request).getDecodedRequestURI();

        Context context = (Context)getContainer();

        Wrapper wrapper = (Wrapper) context.map(request, true);

        if (wrapper ==null){
            notFound(requestURI, (HttpServletResponse) response.getResponse());
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


    private void badRequest(String requestURI, HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, requestURI);
        }
        catch (IllegalStateException e) {
            ;
        }
        catch (IOException e) {
            ;
        }
    }

    private void notFound(String requestURI, HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, requestURI);
        }
        catch (IllegalStateException e) {
            ;
        }
        catch (IOException e) {
            ;
        }
    }
}
