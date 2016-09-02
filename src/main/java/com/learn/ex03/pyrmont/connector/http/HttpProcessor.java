package com.learn.ex03.pyrmont.connector.http;


import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by huyan on 16/8/29.
 */
public class HttpProcessor {

    private HttpRequestLine requestLine = new HttpRequestLine();
    private HttpConnector connector;
    private HttpRequest request;

    public HttpProcessor(HttpConnector connector){
        this.connector = connector;
    }

    public void process(Socket socket){
        SocketInputStream input = null;
        OutputStream out = null;

        try {

            input = new SocketInputStream(socket.getInputStream(), 1024);
            out = socket.getOutputStream();

        } catch (Exception e){

        }
    }


    private void parseRequest(SocketInputStream input, OutputStream out) throws IOException, ServletException {

        input.readRequestLine(requestLine);

        String method = new String(requestLine.method, 0, requestLine.methodEnd);
        String uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        if (method.length()<1){
            throw new ServletException("Miss HTTP Request method");
        }
        if (uri.length()<1){
            throw new ServletException("Miss HTTP Request URI");
        }

        int question = requestLine.indexOf("?");
        if (question >=0){
            request
        }

    }
}
