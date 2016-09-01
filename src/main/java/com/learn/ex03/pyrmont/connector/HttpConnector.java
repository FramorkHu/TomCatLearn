package com.learn.ex03.pyrmont.connector;

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
                inputStream.readRequestLine(new HttpRequestLine());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }


        }

    }
}
