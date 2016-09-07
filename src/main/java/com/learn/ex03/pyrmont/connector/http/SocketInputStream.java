package com.learn.ex03.pyrmont.connector.http;

import java.awt.event.HierarchyEvent;
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

    //当前读取的数据缓冲区
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

        /**
         * 对应socketInputStream，无法确定什么时候读完，这样的概念很模糊，因为套接字中数据的末尾并没有所谓的结束标记，会引起read阻塞。
         * 当TCP 通信连接的一方关闭了套接字时，read返回-1。
         * 对于解析http请求，如果一开始就读到/r/n ，则表明读取完毕
         */
        int chr;
        do {

            try {

                chr = read();
            } catch (IOException e) {
                chr = -1;
            }
        } while ( (chr == CR)|| (chr ==  LF));

        if ( chr == -1){
            //return;
            throw new EOFException("requestStream.readline.error");
        }
        pos -- ;

        //读取方法
        requestLine.methodEnd = readLinePart(requestLine.method, HttpRequestLine.MAX_METHOD_SIZE);

        //读取uri
        requestLine.uriEnd = readLinePart(requestLine.uri, HttpRequestLine.MAX_URI_SIZE);

        //读取协议
        requestLine.protocolEnd = readLinePart(requestLine.protocol, HttpRequestLine.MAX_PROTOCOL_SIZE);

    }

    /**
     * 处理http头请求
     * @param header
     * @throws IOException
     */
    public void readHeader(HttpHeader header) throws IOException {

        if (header.nameEnd != 0){
            header.recycle();
        }

        int chr = read();

        //换行的情况 /r/n 或 /n
        if ((chr == CR) || (chr == LF)){
            if (chr == CR){
                read(); //再往下读一个，即跳过LF
            }
            header.nameEnd = 0;
            header.valueEnd = 0;
            return;
        } else {
            pos --;
        }

        int maxRead = header.name.length;
        int readCount = 0;
        boolean colon = false;

        while (!colon){

            if (readCount>= maxRead){

                if ((2*readCount)<= HttpHeader.MAX_NAME_SIZE){

                    char[] newBuffer = new char[2*readCount];
                    System.arraycopy(header.name,0, newBuffer,0,maxRead);
                    header.name = newBuffer;
                    maxRead = header.name.length;
                } else {
                    throw new IOException("requestStream");
                }
            }

            if (pos>=count){
                int val = read();
                if (val == -1){
                    throw new IOException("requestStream.readline.error");
                }
                pos = 0;
            }

            char val = (char)buf[pos];

            if (val == COLON){
                colon = true;
            }else {
                //将大写转换为小写
                if ((val>'A') && (val<'Z')){
                    val = (char) (val- LC_OFFSET);
                }
                header.name[readCount] = val;
                readCount++;
                pos++;
            }

        }
        header.nameEnd = readCount;


        //读取value
        maxRead = header.value.length;
        readCount = 0;
        boolean eol = false;
        boolean validLine = true;
        while (validLine){

            boolean space = true;
            //删除开头的空格
            while (space){
                char val = (char)read();
                if ((val!= SP) && (val != HT)){
                    space = false;
                }
            }

            while (!eol){

                if (readCount >= maxRead) {
                    if ((2 * maxRead) <= HttpHeader.MAX_VALUE_SIZE) {
                        char[] newBuffer = new char[2 * maxRead];
                        System.arraycopy(header.value, 0, newBuffer, 0,
                                maxRead);
                        header.value = newBuffer;
                        maxRead = header.value.length;
                    } else {
                        throw new IOException("requestStream.readline.toolong");
                    }
                }

                if (pos >= count) {
                    // Copying part (or all) of the internal buffer to the line
                    // buffer
                    int val = read();
                    if (val == -1)
                        throw new IOException("requestStream.readline.error");
                    pos = 0;
                }
                if (buf[pos] == CR) {
                } else if (buf[pos] == LF) {
                    eol = true;
                } else {
                    // FIXME : Check if binary conversion is working fine
                    int ch = buf[pos] & 0xff;
                    header.value[readCount] = (char) ch;
                    readCount++;
                }
                pos++;
            }

            int nextChar = read();
            if ((nextChar!=SP) && (nextChar!= HT)){

                validLine = false;
                pos--;
            } else {
                eol = false;
                if (readCount >= maxRead){
                    if ((2 * maxRead) <= HttpHeader.MAX_VALUE_SIZE) {
                        char[] newBuffer = new char[2 * maxRead];
                        System.arraycopy(header.value, 0, newBuffer, 0,
                                maxRead);
                        header.value = newBuffer;
                        maxRead = header.value.length;
                    } else {
                        throw new IOException("requestStream.readline.toolong");
                    }
                }
                header.value[readCount] = ' ';
                readCount++;
            }

        }
        header.valueEnd = readCount;

    }

    /**
     * 读取请求行的各个部分
     * @param partBuffer 请求区域的缓冲字符数组
     * @param maxPartSize 最大缓冲区长度
     * @return
     * @throws IOException
     */
    private int readLinePart(char[] partBuffer, int maxPartSize) throws IOException {
        int maxRead = partBuffer.length;
        int readCount = 0;

        //读取结束
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

            //数据读完是继续读取
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

    public int available() throws IOException {

        return (count-pos)+ inputStream.available();
    }

    public void close() throws IOException {
        if (inputStream == null){
            return;
        }
        inputStream.close();
        inputStream = null;
        buf = null;
    }
}
