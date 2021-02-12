package net.testlab;

import net.testlab.io.BufferedInputStream;
import net.testlab.io.BufferedOuputStream;
import net.testlab.io.ByteArrayInputStream;
import net.testlab.io.ByteArrayOutputStream;


import java.io.IOException;

public class StartApp {

    public static void main(String[] args) throws IOException {




        byte[] arr = {1,2,3,4,5,6,7,8};

        java.io.ByteArrayOutputStream testedByteStream = new java.io.ByteArrayOutputStream();
        BufferedOuputStream testedStream = new BufferedOuputStream(testedByteStream);
        for (byte b : arr) {
            testedStream.write(b);
        }
        java.io.ByteArrayOutputStream nativeByteStream = new java.io.ByteArrayOutputStream();
        java.io.BufferedOutputStream nativeStream = new java.io.BufferedOutputStream(nativeByteStream);
        for (byte b : arr) {
            nativeStream.write(b);
        }
        System.out.println(nativeByteStream.toByteArray().length);
        for(byte b: nativeByteStream.toByteArray())
        System.out.print(b);



        System.out.println("===========");
        System.out.println(testedByteStream.toByteArray().length);
        for(byte b: testedByteStream.toByteArray())
            System.out.print(b);



    }


}


