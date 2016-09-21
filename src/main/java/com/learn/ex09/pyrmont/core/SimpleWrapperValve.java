package com.learn.ex09.pyrmont.core;

import org.apache.catalina.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huyan on 2016/9/21.
 */
public class SimpleWrapperValve implements Valve, Contained {

    protected Container container;

    public SimpleWrapperValve(Container container){
        this.container = container;
    }
    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        SimpleWrapper wrapper = (SimpleWrapper) getContainer();
        ServletRequest sreq = request.getRequest();
        ServletResponse sres = response.getResponse();
        Servlet servlet = null;
        HttpServletRequest hreq = null;
        if (sreq instanceof HttpServletRequest)
            hreq = (HttpServletRequest) sreq;
        HttpServletResponse hres = null;
        if (sres instanceof HttpServletResponse)
            hres = (HttpServletResponse) sres;

        //-- new addition -----------------------------------
        //设置context容器，用于request获取session时访问session管理器
        Context context = (Context) wrapper.getParent();
        request.setContext(context);

        try {
            servlet = wrapper.allocate();
            if (hres!=null && hreq!=null) {
                servlet.service(hreq, hres);
            }
            else {
                servlet.service(sreq, sres);
            }
        }
        catch (ServletException e) {
        }
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
