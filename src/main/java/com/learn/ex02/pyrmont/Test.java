package com.learn.ex02.pyrmont;

import java.io.*;

/**
 * Created by huyan on 2016/8/29.
 */
public class Test {

    public static void main(String[] args) throws IOException {

        File file = new File("D:\\app\\DB23-IP-COUNTRY-REGION-CITY-LATITUDE-LONGITUDE-ISP-DOMAIN-MOBILE-USAGETYPE.CSV\\IP-COUNTRY-REGION-CITY-LATITUDE-LONGITUDE-ISP-DOMAIN-MOBILE-USAGETYPE.CSV");

        InputStream inputStream = new FileInputStream(file);

        byte[] b =new byte[1024];

        int n = inputStream.read(b);
        System.out.print(new String(b));
       /* while (n>0){
            System.out.print(new String(b));
            n = inputStream.read(b);

        }*/
        inputStream.close();
    }
}
