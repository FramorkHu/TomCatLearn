package com.learn.ex03.pyrmont.connector.http;

import org.apache.catalina.connector.RequestStream;
import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.util.RequestUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by huyan on 16/8/29.
 */
public class HttpRequest implements HttpServletRequest {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private String queryString;
    private String requestSessionId;
    private boolean requestSessionUri;
    private InputStream input;
    private String method;
    private String requestUri;
    private String protocol;
    private int contentLength;
    private String contentType;
    private boolean requestSessionCookie;


    protected Map<String, List<String>> headers = new HashMap<>();
    protected List<Cookie> cookies = new ArrayList<>();

    protected Map<String, Object> attributes = new HashMap<>();

    private boolean parse = false;
    private ParameterMap parameters;

    protected SimpleDateFormat formats[] = {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
    };


    public void addHeader(String name, String value){

        name = name.toLowerCase();

        synchronized (headers){
            List<String> values = headers.get(name);
            if (values == null){
                values = new ArrayList<>();
                headers.put(name, values);
            }
            values.add(value);
        }
    }

    /**
     * 解析参数
     * 1、查询字符串 GET
     * 2、HTTP请求体中 POST
     */
    public void parseParameters() throws IOException {

        if (parse){
            return;
        }

        ParameterMap results = parameters;

        if (results == null) {
            results = new ParameterMap();
        }
        results.setLocked(true);  // ?应该是锁不住的，多个线程执行（不用考虑，没有共享变量）

        String encoding = getCharacterEncoding();
        if (null == encoding){
            encoding = DEFAULT_ENCODING;
        }
        String queryString = getQueryString();

        try {
            RequestUtil.parseParameters(results, queryString, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //如果是post提交，请求体会包含参数
        //Content-Type:application/x-www-form-urlencoded     Content-Length>0
        String contentType = getContentType();
        if (contentType == null){
            contentType = "";
        }
        int semicolon = contentType.indexOf(";");

        if (semicolon>0){
            contentType = contentType.substring(0, semicolon).trim();
        } else {
            contentType = contentType.trim();
        }
        if ( "POST".equals(getMethod())
                && getContentLength()>0
                && "application/x-www-form-urlencoded".equals(contentType) ){

            int max = getContentLength();
            int len = 0;
            byte[] buf = new byte[getContentLength()];

            //ServletInputStream inputStream = getInputStream();

        }

    }


    public void addCookie(Cookie cookie){
        synchronized (cookies){
            cookies.add(cookie);
        }
    }

    public boolean isRequestSessionUri() {
        return requestSessionUri;
    }

    public void setRequestSessionUri(boolean requestSessionUri) {
        this.requestSessionUri = requestSessionUri;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setRequestSessionId(String requestSessionId) {
        this.requestSessionId = requestSessionId;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setRequestSessionCookie(boolean requestSessionCookie) {
        this.requestSessionCookie = requestSessionCookie;
    }

    public InputStream getStream() {
        return input;
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String s) {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public Enumeration getHeaders(String s) {
        return null;
    }

    @Override
    public Enumeration getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String s) {
        return 0;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return this.queryString;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return this.requestSessionId;
    }

    @Override
    public String getRequestURI() {
        return this.requestUri;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this.requestSessionCookie;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return this.contentLength;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }
}
