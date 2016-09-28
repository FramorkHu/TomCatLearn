package com.learn.ex14.pyrmont.startup;

import org.apache.catalina.startup.ContextConfig;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by huyan on 2016/9/27.
 */
public class Stopper {

    public static void main(String[] args) {

        int port = 8005;
        try {
            Socket socket = new Socket("localhost", port);
            OutputStream stream = socket.getOutputStream();
            String shutDown = "SHUTDOWN";
            stream.write(shutDown.getBytes());
            stream.flush();
            stream.close();
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
