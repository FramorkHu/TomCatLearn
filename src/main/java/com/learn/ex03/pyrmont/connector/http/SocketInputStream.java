package com.learn.ex03.pyrmont.connector.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huyan on 16/8/29.
 */
public class SocketInputStream extends InputStream {

    private static final byte CR = '\r';
    private static final byte LF = '\n';
    private static final byte SP = ' ';
    private static final byte HT = '\t';
    private static final byte COLON = ';';
    private static final int LC_OFFSET = 'A' - 'a';

    protected byte buf[];
    protected int count;
    protected int pos;
    private InputStream inputStream;

    public SocketInputStream(InputStream inputStream, int bufferSize){
        this.inputStream = inputStream;
        buf = new byte[bufferSize];
    }

    public void readRequestLine(HttpRequestLine line){

        if (line.methodEnd !=0){
            line.recycle();
        }

    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
