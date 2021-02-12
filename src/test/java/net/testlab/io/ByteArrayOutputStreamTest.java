package net.testlab.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayOutputStreamTest {



    @Nested
    class SimpleWriteTest {

        byte[] arr = {1,2,3,4,5,6,7,8};

        @Test
        @DisplayName("Write ()")
        void write() throws Exception {
            ByteArrayOutputStream testedStream = new ByteArrayOutputStream(3);
            for (byte b : arr) {
                testedStream.write(b);
            }

            java.io.ByteArrayOutputStream nativeStream = new java.io.ByteArrayOutputStream(3);
            for (byte b : arr) {
                nativeStream.write(b);
            }

            assertArrayEquals(testedStream.toByteArray(),nativeStream.toByteArray());
        }
    }
}