package com.learn.ex02.pyrmont;

import java.io.IOException;

/**
 * Created by huyan on 2016/8/29.
 */
public class StaticResourceProcessor {

    public void process(Request request, Response response){

        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
