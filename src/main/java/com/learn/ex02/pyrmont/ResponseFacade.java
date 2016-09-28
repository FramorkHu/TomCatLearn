package com.learn.ex02.pyrmont;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Created by huyan on 2016/8/29.
 */
public class ResponseFacade implements ServletResponse {

    private Response response;

    public ResponseFacade(Response response){
        this.response  = response;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    /*@Override
    public String getContentType() {
        return null;
    }*/

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    /*@Override
    public void setCharacterEncoding(String charset) {

    }*/

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
