package net.testlab.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayInputStreamTest {

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


    @Nested
    class ReadIntoByteArrayWithOffsetTest {

        final byte[] initArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ByteArrayInputStream testedStream;
        java.io.ByteArrayInputStream nativeStream;

        byte[] testedArr;
        int testedCount;
        byte[] nativeArr;
        int nativeCount;

        @BeforeEach
        void setUp() {
            testedStream = new ByteArrayInputStream(initArr);
            nativeStream = new java.io.ByteArrayInputStream(initArr);
        }

        @Test
        @DisplayName("Read (byte[6],0,4)")
        void read1() throws Exception {
            testedArr = new byte[6];
            testedCount = testedStream.read(testedArr, 0, 4);
            nativeArr = new byte[6];
            nativeCount = nativeStream.read(nativeArr, 0, 4);
            assertAll( //
                    () -> assertArrayEquals(nativeArr, testedArr),
                    () -> assertEquals(nativeCount, testedCount)
            );
        }

        @Test
        @DisplayName("Read (byte[6],2,3)")
        void read2() throws Exception {
            testedArr = new byte[6];
            testedCount = testedStream.read(testedArr, 2, 3);
            nativeArr = new byte[6];
            nativeCount = nativeStream.read(nativeArr, 2, 3);
            assertAll( //
                    () -> assertArrayEquals(nativeArr, testedArr),
                    () -> assertEquals(nativeCount, testedCount)
            );
        }

        @Test
        @DisplayName("Read (byte[16],10,4)")
        void read3() throws Exception {
            testedArr = new byte[16];
            testedCount = testedStream.read(testedArr, 10, 4);
            nativeArr = new byte[16];
            nativeCount = nativeStream.read(nativeArr, 10, 4);
            assertAll( //
                    () -> assertArrayEquals(nativeArr, testedArr),
                    () -> assertEquals(nativeCount, testedCount)
            );
        }

        @Test
        @DisplayName("Read (byte[0],0,0)")
        void read() throws Exception {
            testedArr = new byte[0];
            testedCount = testedStream.read(testedArr, 0, 0);
            nativeArr = new byte[0];
            nativeCount = nativeStream.read(nativeArr, 0, 0);
            assertAll( //
                    () -> assertArrayEquals(nativeArr, testedArr),
                    () -> assertEquals(nativeCount, testedCount)
            );
        }


        @Test
        @DisplayName("Read with exception (byte[6],10,4) ")
        void readWithError1() throws Exception {
            testedArr = new byte[6];
            nativeArr = new byte[6];
            assertAll( //
                    () -> assertThrows(RuntimeException.class, () -> {
                        testedStream.read(testedArr, 10, 4);
                    }),
                    () -> assertThrows(RuntimeException.class, () -> {
                        nativeStream.read(nativeArr, 10, 4);
                    })
            );
        }

        @Test
        @DisplayName("Read with exception (byte[6],0,15) ")
        void readWithError2() throws Exception {
            testedArr = new byte[6];
            nativeArr = new byte[6];
            assertAll( //
                    () -> assertThrows(RuntimeException.class, () -> {
                        testedStream.read(testedArr, 0, 15);
                    }),
                    () -> assertThrows(RuntimeException.class, () -> {
                        nativeStream.read(nativeArr, 0, 15);
                    })
            );
        }

        @Test
        @DisplayName("Read with exception (byte[6],3,3) ")
        void readWithError3() throws Exception {
            testedArr = new byte[6];
            nativeArr = new byte[6];
            assertAll( //
                    () -> assertThrows(RuntimeException.class, () -> {
                        testedStream.read(testedArr, 3, 4);
                    }),
                    () -> assertThrows(RuntimeException.class, () -> {
                        nativeStream.read(nativeArr, 3, 4);
                    })
            );
        }

    }
}