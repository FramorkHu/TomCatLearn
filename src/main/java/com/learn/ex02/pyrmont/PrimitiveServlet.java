package com.learn.ex02.pyrmont;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by huyan on 2016/8/29.
 */
public class PrimitiveServlet implements Servlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

        System.out.println("init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        System.out.println("from Service" );

        PrintWriter out = res.getWriter();
        out.println("Hello Roses are red");
        out.print("Violets are blue");

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

        System.out.println("destory");
    }
}
