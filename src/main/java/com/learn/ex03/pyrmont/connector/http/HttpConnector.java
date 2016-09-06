package com.learn.ex03.pyrmont.connector.http;

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
            Socket socket;
            try {
                socket = server.accept();

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            HttpProcessor processor = new HttpProcessor();

            processor.process(socket);

        }

    }

    public void start(){

        Thread connectThread = new Thread(this);
        connectThread.start();
    }




}
