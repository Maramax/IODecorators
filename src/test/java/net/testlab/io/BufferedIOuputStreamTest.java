package net.testlab.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BufferedIOuputStreamTest {


    @Nested
    class SimpleWriteTest {

        byte[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] emptyArr = {};

        @Test
        @DisplayName("Write ()")
        void write() throws Exception {
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

            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
            assertArrayEquals(emptyArr, testedByteStream.toByteArray());
            testedStream.flush();
            nativeStream.flush();
            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
            assertArrayEquals(arr, testedByteStream.toByteArray());
        }
    }
}