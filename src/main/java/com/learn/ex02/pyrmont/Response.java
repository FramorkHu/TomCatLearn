package com.learn.ex02.pyrmont;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * Created by huyan on 2016/8/29.
 */
public class Response implements ServletResponse {

    private static final int BUFFER_SIZE = 1024;

    private Request request;
    private OutputStream outputStream;
    private PrintWriter writer;


    public Response(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    //静态页面服务
    public void sendStaticResource() throws IOException{

        byte[] bytes =new byte[BUFFER_SIZE];
        FileInputStream fileInputStream = null;

        File file = new File(Constants.WEB_ROOT, request.getUri());
        try {
            fileInputStream = new FileInputStream(file);
            int ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);

            while (ch != -1){
                outputStream.write(bytes, 0, ch);
                ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

            String message = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>File Not Found</h1>";
            outputStream.write(message.getBytes());
        }finally {
            if (fileInputStream!=null){
                fileInputStream.close();
            }
        }

    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        writer = new PrintWriter(outputStream, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

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
