package net.testlab;

import net.testlab.io.BufferedInputStream;
import net.testlab.io.BufferedOuputStream;
import net.testlab.io.ByteArrayInputStream;
import net.testlab.io.ByteArrayOutputStream;


import java.io.IOException;

public class StartApp {

    public static void main(String[] args) {

        // initial array
        byte[] arr = {1, 2, 3, 4, 5, 6, 7, 8};

        // write data to byteOS through bufferOS
        ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
        try (BufferedOuputStream bufferOS = new BufferedOuputStream(byteOS)) {
            for (byte b : arr) {
                bufferOS.write(b);
            }
            bufferOS.flush(); // ensure flushing data to byteOS

        } catch (IOException e) {
            e.printStackTrace();
        }

        // get bytes from byteOS
        byte[] bytesToRead = byteOS.toByteArray();

        // pass data into byteIS to read through bufferIS
        ByteArrayInputStream byteIS = new ByteArrayInputStream(bytesToRead);
        try (BufferedInputStream bufferIS = new BufferedInputStream(byteIS)) {
            int c;
            while ((c = bufferIS.read()) != -1) {
                System.out.print(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


