package com.learn.ex03.pyrmont.connector.http;

/**
 * Created by huyan on 16/9/1.
 * HTTP 头
 */
public class HttpHeader {

    public static final int INITIAL_NAME_SIZE = 32;
    public static final int INITIAL_VALUE_SIZE = 64;
    public static final int MAX_NAME_SIZE = 128;
    public static final int MAX_VALUE_SIZE = 4096;

    public char[] name;
    public int nameEnd;
    public char[] value;
    public int valueEnd;

    private int hashCode = 0;

    public HttpHeader(){
        this(new char[INITIAL_NAME_SIZE], 0, new char[INITIAL_VALUE_SIZE], 0);
    }

    public HttpHeader(char[] name, int nameEnd, char[] value, int valueEnd){
        this.name = name;
        this.nameEnd = nameEnd;
        this.value = value;
        this.valueEnd = valueEnd;
    }

    public HttpHeader(String name, String value){

        this.name = name.toCharArray();
        this.nameEnd = name.length();
        this.value = value.toCharArray();
        this.valueEnd = value.length();
    }


    public void recycle(){
        nameEnd = 0;
        valueEnd = 0;
        hashCode = 0;
    }


    public boolean equals(char[] buf){
        return equals(buf, buf.length);
    }

    public boolean equals(String str){
        return equals(str.toCharArray());
    }

    /**
     * 对比name和buf是否相同
     */
    public boolean equals(char[] buf, int end){

        if (end != nameEnd){
            return false;
        }

        for (int i=0; i<end; i++){

            if (buf[i] != name[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * 对比value和所给的buf是否相同
     */
    public boolean valueEqual(char[] buf, int end) {
        if (end != valueEnd) {
            return false;
        }
        for (int i=0; i< end; i++){
            if(value[i]!= buf[i]){
                return false;
            }
        }

        return true;
    }

    public boolean valueIncludes(char[] buf, int end){
        char firstChar = buf[0];
        int pos = 0;
        while (pos < valueEnd){
            pos = valueIndexOf(firstChar, pos);
            if (pos == -1){
                return false;
            }

            if ((valueEnd-pos) < end){
                return false;
            }
            for (int i=0; i<end; i++){
                if (buf[i] != value[pos+i]){
                    break;
                }
                if (i == (end-1)){
                    return true;
                }
            }
            pos++;
        }

        return false;
    }

    public int valueIndexOf(char c, int start){

        for (int i=start; i< valueEnd; i++){
            if (value[i] == c){
                return i;
            }
        }

        return -1;
    }


}
