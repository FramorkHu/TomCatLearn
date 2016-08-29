package com.learn.ex02.pyrmont;

import javax.servlet.Servlet;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by huyan on 2016/8/29.
 */
public class ServletProcessor2 {


    public void process(Request request, Response response){

        String uri = request.getUri();

        String servletName = uri.substring(uri.lastIndexOf("/")+1);

        URLClassLoader loader = null;

        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.WEB_ROOT);

            String respository = new URL("file", null, classPath.getCanonicalPath()+ File.separator).toString();
            urls[0] = new URL(null, respository, streamHandler);
            loader = new URLClassLoader(urls);


        } catch (Exception e){
            e.printStackTrace();
        }

        Class myClass = null;

        try{
            myClass = loader.loadClass( servletName);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Servlet servlet = null;
        try{
            servlet = (Servlet) myClass.newInstance();
            servlet.service( new RequestFacade(request), new ResponseFacade(response));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
