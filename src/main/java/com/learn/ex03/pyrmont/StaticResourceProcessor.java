package com.learn.ex03.pyrmont;

import com.learn.ex03.pyrmont.connector.http.HttpRequest;
import com.learn.ex03.pyrmont.connector.http.HttpResponse;

import java.io.IOException;

/**
 * Created by huyan on 16/9/6.
 */
public class StaticResourceProcessor {

    public void process(HttpRequest request, HttpResponse response) throws IOException {

        response.sendStaticResource();
    }
}
