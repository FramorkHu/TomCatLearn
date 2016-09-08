package com.learn.ex05.pyrmont.core;

import org.apache.catalina.Container;
import org.apache.catalina.Mapper;
import org.apache.catalina.Request;

/**
 * Created by huyan on 2016/9/8.
 * 一个映射器支持一个请求协议
 */
public class SimpleContextMapper implements Mapper {
    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public void setProtocol(String protocol) {

    }

    @Override
    public Container map(Request request, boolean update) {
        return null;
    }
}
