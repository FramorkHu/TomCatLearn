package com.learn.ex03.pyrmont.connector;

import com.learn.ex03.pyrmont.connector.http.HttpResponse;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by huyan on 2016/9/6.
 */
public class ResponseStream extends ServletOutputStream {


    protected boolean closed = false;

    //调用flush的时候是否提交请求
    protected boolean commit = false;

    //当前写入已经stream中的字节长度
    protected int count = 0;
    //内容写入长度限制，-1没有限制
    protected int length = -1;

    //当前输入流所关联的response
    protected HttpResponse response;

    //写数据的输出流
    protected OutputStream stream;

    public ResponseStream(HttpResponse response){
        super();
        this.response = response;
        closed = false;
        commit = false;
        count = 0;

    }

    public boolean getCommit() {

        return (this.commit);

    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    @Override
    public void write(int b) throws IOException {

        if (closed)
            throw new IOException("responseStream.write.closed");

        if ((length > 0) && (count >= length))
            throw new IOException("responseStream.write.count");

        response.write(b);
        count++;
    }

    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);

    }

    public void write(byte b[], int off, int len) throws IOException {

        if (closed)
            throw new IOException("responseStream.write.closed");

        int actual = len;
        if ((length>0) && (count + len) >= length){
            actual = length - count;
        }

        response.write(b, off, actual);

        count += actual;
        if (actual < len){
            throw new IOException("responseStream.write.count");
        }
    }

    public void flush() throws IOException {

        if (closed){
            throw new IOException("responseStream.flush.closed");
        }
        if (commit){
            response.flushBuffer();
        }
    }

    public void close() throws IOException {
        if (closed)
            throw new IOException("responseStream.close.closed");
        response.flushBuffer();
        closed = true;
    }

    public void reset() {

        count = 0;

    }

    public boolean closed() {
        return (this.closed);

    }

}
