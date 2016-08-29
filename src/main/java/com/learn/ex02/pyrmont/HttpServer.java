package com.learn.ex02.pyrmont;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by huyan on 2016/8/29.
 */
public class HttpServer {

    private static final int PORT = 9999;

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shuntDown = false;

    public static void main(String[] args) throws IOException {

        new HttpServer().await();
    }

    public void await() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        InputStream inputStream;
        OutputStream outputStream;
        Socket socket;
        while (!shuntDown){
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            Request request = new Request(inputStream);
            request.parse();

            Response response = new Response(outputStream);
            response.setRequest( request);

            if (request.getUri().startsWith("/servlet")){
                ServletProcessor1 processor1 = new ServletProcessor1();
                processor1.process(request, response);
            } else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
            }

            socket.close();

            shuntDown = request.getUri().equals(SHUTDOWN_COMMAND);
        }



    }
}
