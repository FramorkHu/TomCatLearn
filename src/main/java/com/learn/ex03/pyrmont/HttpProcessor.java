package com.learn.ex03.pyrmont;

import com.learn.ex03.pyrmont.connector.http.SocketInputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by huyan on 16/8/29.
 */
public class HttpProcessor {


    public HttpProcessor(HttpConnector connector){

    }

    public void process(Socket socket){
        InputStream input = null;
        OutputStream out = null;

        try {

        } catch (Exception e){

        }
    }

    private void parseRequest(SocketInputStream input, OutputStream out){


    }
}