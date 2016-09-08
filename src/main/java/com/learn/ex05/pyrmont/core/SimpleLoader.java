package com.learn.ex05.pyrmont.core;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Loader;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by huyan on 2016/9/8.
 * 用于servlet容器中载入servlet相关类
 */
public class SimpleLoader implements Loader {

    public static final String WEB_ROOT = System.getProperty("user.dir")+ File.separator+"webroot";
    private ClassLoader classLoader;
    Container container;

    public SimpleLoader(){

        try {
            URL[] urls = new URL[1];
            File classPath = new File(WEB_ROOT);
            String respository = new URL("file", null, classPath.getCanonicalPath()+ File.separator).toString();

            //为了区分构造函数
            URLStreamHandler streamHandler = null;
            urls[0] = new URL(null, respository, streamHandler);
            classLoader = new URLClassLoader(urls);
        } catch (Exception e){

            e.printStackTrace();
        }

    }
    @Override
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {

    }

    @Override
    public DefaultContext getDefaultContext() {
        return null;
    }

    @Override
    public void setDefaultContext(DefaultContext defaultContext) {

    }

    @Override
    public boolean getDelegate() {
        return false;
    }

    @Override
    public void setDelegate(boolean delegate) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public boolean getReloadable() {
        return false;
    }

    @Override
    public void setReloadable(boolean reloadable) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void addRepository(String repository) {

    }

    @Override
    public String[] findRepositories() {
        return new String[0];
    }

    @Override
    public boolean modified() {
        return false;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
