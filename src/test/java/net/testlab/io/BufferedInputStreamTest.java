package net.testlab.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BufferedInputStreamTest {

    @Nested
    class SimpleReadTest {

        final byte[] initArr = {1, 2, 3};


        ByteArrayInputStream testedStream;
        java.io.ByteArrayInputStream nativeStream;

        int testedValue;
        int nativeValue;

        @Test
        @DisplayName("Read ()")
        void read1() throws Exception {

            testedStream = new ByteArrayInputStream(initArr);
            nativeStream = new java.io.ByteArrayInputStream(initArr);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);
        }
    }
}