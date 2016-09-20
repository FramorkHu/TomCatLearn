package com.learn.ex06.pyrmont.core;


import org.apache.catalina.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huyan on 2016/9/8.
 * 一个映射器支持一个请求协议
 */
public class SimpleContextMapper implements Mapper {

    private SimpleContext context;
    private String protocol;

    @Override
    public Container getContainer() {
        return context;
    }

    @Override
    public void setContainer(Container container) {
        if ( !(container instanceof SimpleContext)){
            throw new IllegalArgumentException("container type is illegal");
        }
        this.context = (SimpleContext)container;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public Container map(Request request, boolean update) {

        String contextPath = ((HttpServletRequest)request).getContextPath();
        String uri = ((HttpRequest)request).getDecodedRequestURI();
        String relativeUrl = uri.substring( contextPath.length());


        String name = context.findServletMapping(relativeUrl);

        Wrapper wrapper = null;
        if (name!=null){
            wrapper = (Wrapper)context.findChild(name);

        }
        return wrapper;
    }
}
