package com.learn.ex03.pyrmont.connector;

import com.learn.ex03.pyrmont.connector.http.HttpHeader;
import com.learn.ex03.pyrmont.connector.http.HttpRequestLine;
import com.learn.ex03.pyrmont.connector.http.SocketInputStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by huyan on 2016/8/29.
 */
public class HttpConnector implements Runnable {

    private boolean stopped;
    private String scheme = "http";

    private static final int PORT = 8888;

    public static void main(String[] args) {
        new HttpConnector().run();
    }
    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!stopped){
            Socket socket = null;
            try {
                socket = server.accept();
                socket.getInputStream();
                SocketInputStream inputStream = new SocketInputStream(socket.getInputStream(), 1024);
                HttpRequestLine requestLine = new HttpRequestLine();
                inputStream.readRequestLine(requestLine);
                System.out.println(String.valueOf(requestLine.method)+" " +String.valueOf(requestLine.uri));
                inputStream.readHeader(new HttpHeader());

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }


        }

    }
}
