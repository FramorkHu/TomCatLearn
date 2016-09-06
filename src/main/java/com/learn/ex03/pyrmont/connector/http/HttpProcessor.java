package com.learn.ex03.pyrmont.connector.http;


import com.learn.ex03.pyrmont.ServletProcessor;
import com.learn.ex03.pyrmont.StaticResourceProcessor;
import org.apache.catalina.util.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by huyan on 16/8/29.
 */
public class HttpProcessor {

    private HttpRequestLine requestLine = new HttpRequestLine();
    private HttpRequest request;
    private HttpResponse response;


    public void process(Socket socket){
        SocketInputStream input = null;
        OutputStream out = null;

        try {

            input = new SocketInputStream(socket.getInputStream(), 1024);
            out = socket.getOutputStream();

            request = new HttpRequest(input);

            response = new HttpResponse(out);
            response.setRequest(request);

            parseRequest(input);
            parseHeader(input);

            if (request.getRequestURI().startsWith("/servlet")){
                ServletProcessor processor = new ServletProcessor();
                processor.process(request, response);

            } else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
            }


            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseHeader(SocketInputStream input) throws IOException, ServletException {

        //循环读取请求头，直到取完
        while (true){

            HttpHeader header = new HttpHeader();
            input.readHeader(header);
            if (header.nameEnd == 0){

                if (header.valueEnd == 0){
                    return;
                } else {
                    throw new ServletException("httpProcessor.parseHeaders.colon");
                }
            }

            String name = new String(header.name, 0, header.nameEnd);
            String value = new String(header.value, 0, header.valueEnd);

            request.addHeader(name, value);

            if ("cookie".equalsIgnoreCase(name)){
                Cookie[] cookies = RequestUtil.parseCookieHeader(value);
                for (Cookie cookie : cookies){
                    if (cookie.getName().equals("jsessionid")){

                        if (!request.isRequestedSessionIdFromCookie()){

                            request.setRequestSessionId(cookie.getValue());
                            request.setRequestSessionCookie(true);
                            request.setRequestSessionUri(false);
                        }
                    }

                    request.addCookie(cookie);
                }
            } else if ("content-length".equalsIgnoreCase(name)){
                int n;
                try {
                    n = Integer.parseInt(value);
                } catch (Exception e){
                    throw new ServletException("httpProcessor.parseHeaders.contentLength");
                }
                request.setContentLength(n);

            } else if ("content-type".equalsIgnoreCase(name)){

                request.setContentType(value);
            }
        }


    }

    private void parseRequest(SocketInputStream input) throws IOException, ServletException {

        input.readRequestLine(requestLine);

        String method = new String(requestLine.method, 0, requestLine.methodEnd);
        String uri ;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        if (method.length()<1){
            throw new ServletException("Miss HTTP Request method");
        }
        if (requestLine.uriEnd<1){
            throw new ServletException("Miss HTTP Request URI");
        }

        //解析查询字符串  即问号后面的内容
        int question = requestLine.indexOf("?");
        if (question >=0){
            request.setQueryString(new String(requestLine.uri, question+1, requestLine.uriEnd-question-1));

            uri = new String(requestLine.uri, 0, question);
        } else{
            request.setQueryString(null);
            uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        }

        //检查uri的绝对路径, 包含协议名称
        if (!uri.startsWith("/")){
            //不以 / 开头， 表示绝对路径
            int pos = uri.indexOf("://");
            if (pos!=-1){
                pos = uri.indexOf("/", pos+3);
                if (pos == -1){
                    uri = "";
                } else {
                    uri = uri.substring(pos);
                }
            }
        }

        //提取sessionId的值
        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon>=0){
            String rest = uri.substring(semicolon+ match.length());
            int semicolon2 = rest.indexOf(";");
            if (semicolon2 >= 0){
                request.setRequestSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            } else {
                request.setRequestSessionId(rest);
                rest = "";
            }

            request.setRequestSessionUri(true);
            uri = uri.substring(0, semicolon) + rest;
        } else {
            request.setRequestSessionUri(false);
            request.setRequestSessionId(null);
        }

        String normalizedUri = normalize(uri);
        request.setMethod(method);
        request.setProtocol(protocol);
        if ( normalizedUri!= null){
            request.setRequestUri(normalizedUri);
        } else {
            request.setRequestUri(uri);
            throw new ServletException("Invalid URI: " + uri + "'");
        }

    }

    /**
     * 修正uri
     */
    protected String normalize(String path) {
        if (path == null)
            return null;
        // Create a place for the normalized path
        String normalized = path;

        // Normalize "/%7E" and "/%7e" at the beginning to "/~"
        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
            normalized = "/~" + normalized.substring(4);

        // Prevent encoding '%', '/', '.' and '\', which are special reserved
        // characters
        if ( normalized.contains("%25")
                || normalized.contains("%2F")
                || normalized.contains("%2E")
                || normalized.contains("%5C")
                || normalized.contains("%2f")
                || normalized.contains("%2e")
                || normalized.contains("%5c")) {
            return null;
        }

        if (normalized.equals("/."))
            return "/";

        // Normalize the slashes and add leading slash if necessary
        if (normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Declare occurrences of "/..." (three or more dots) to be invalid
        // (on some Windows platforms this walks the directory tree!!!)
        if (normalized.contains("/..."))
            return (null);

        // Return the normalized path that we have completed
        return (normalized);

    }
}
