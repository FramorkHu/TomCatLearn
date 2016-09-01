package com.learn.ex03.pyrmont.connector.http;

import java.io.EOFException;
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
    private static final byte COLON = ':';
    private static final int LC_OFFSET = 'A' - 'a';

    //数据缓冲区
    protected byte buf[];
    //缓冲区可用大小
    protected int count;
    //当前读取缓冲字节位置
    protected int pos;
    private InputStream inputStream;


    public SocketInputStream(InputStream inputStream, int bufferSize){
        this.inputStream = inputStream;
        buf = new byte[bufferSize];
    }

    public void readRequestLine(HttpRequestLine requestLine) throws IOException {

        if (requestLine.methodEnd !=0){
            requestLine.recycle();
        }

        int chr;
        do {

            try {

                chr = read();
            } catch (IOException e) {
                chr = -1;
            }
        } while ( (chr == CR)|| (chr ==  LF));

        if ( chr == -1){
            throw new EOFException("requestStream.readline.error");
        }
        pos -- ;

        //读取方法
        requestLine.methodEnd = readPart(requestLine.method, HttpRequestLine.MAX_METHOD_SIZE);

        //读取uri
        requestLine.uriEnd = readPart(requestLine.uri, HttpRequestLine.MAX_URI_SIZE);

        //读取协议
        requestLine.protocolEnd = readPart(requestLine.protocol, HttpRequestLine.MAX_PROTOCOL_SIZE);

    }

    private int readPart(char[] partBuffer, int maxPartSize) throws IOException {
        int maxRead = partBuffer.length;
        int readCount = 0;

        boolean readEnd = false;

        while (!readEnd){

            //缓冲区满了时扩展它， 扩展一倍
            if ( readCount>= maxRead){

                if ( (2*maxRead) <= maxPartSize){
                    char[] newBuffer = new char[2*maxRead];
                    System.arraycopy( partBuffer, 0, newBuffer, 0, maxRead);
                    partBuffer = newBuffer;
                    maxRead = partBuffer.length;
                } else {
                    throw new IOException("requestStream.readline.toolong");
                }
            }

            if (pos >= count){
                int val = read();
                if (val == -1){
                    throw new IOException("requestStream.readline.error");
                }
                pos = 0;
            }

            if (buf[pos] == CR){
                //跳过 \r
            } else if (buf[pos] == SP || (buf[pos] == LF)){
                readEnd = true;
            } else {
                partBuffer[readCount] = (char)buf[pos];
                readCount ++;

            }
            pos++;
        }

        return readCount;
    }

    @Override
    public int read() throws IOException {

        if (pos >= count){
            //缓冲区读完了重新填充
            fill();
            if (pos >= count){
                //读完了
                return -1;
            }
        }

        return buf[pos++] & 0xff; // byte转换为char
    }

    protected void fill() throws IOException {

        pos =0;
        count = 0;
        int nRead = inputStream.read(buf, 0, buf.length );
        if (nRead >0){
            count = nRead;
        }
    }
}
