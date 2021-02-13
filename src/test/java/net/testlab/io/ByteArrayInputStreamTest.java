package net.testlab.io;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayInputStreamTest {

    final byte[] initArr = {1, 2, 3};


    ByteArrayInputStream testedStream;
    java.io.ByteArrayInputStream nativeStream;

    int testedValue;
    int nativeValue;

    @Nested
    class SimpleReadingTest {

        @Test
        @DisplayName("Read nothing")
        void readNothing() throws Exception {

            testedStream = new ByteArrayInputStream(new byte[0]);
            nativeStream = new java.io.ByteArrayInputStream(new byte[0]);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);

        }

        @Test
        @DisplayName("Read bytes")
        void readBytes() throws Exception {

            testedStream = new ByteArrayInputStream(initArr);
            nativeStream = new java.io.ByteArrayInputStream(initArr);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);
            assertEquals(1, testedValue);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);
            assertEquals(2, testedValue);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);
            assertEquals(3, testedValue);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);
            assertEquals(-1, testedValue);
        }
    }


    @Nested
    class ReadingIntoArrayTest {

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
        void readBytesIntoArray() throws Exception {
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
        void readBytesIntoArrayWithOffset() throws Exception {
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
        void readBytesIntoArrayWithBiggerOffset() throws Exception {
            testedArr = new byte[1024];
            testedCount = testedStream.read(testedArr, 1002, 4);
            nativeArr = new byte[1024];
            nativeCount = nativeStream.read(nativeArr, 1002, 4);
            assertAll( //
                    () -> assertArrayEquals(nativeArr, testedArr),
                    () -> assertEquals(nativeCount, testedCount)
            );
        }

        @Test
        @DisplayName("Read (byte[0],0,0)")
        void readBytesIntoEmptyArray() throws Exception {
            testedArr = new byte[0];
            testedCount = testedStream.read(testedArr, 0, 0);
            nativeArr = new byte[0];
            nativeCount = nativeStream.read(nativeArr, 0, 0);
            assertAll( //
                    () -> assertArrayEquals(nativeArr, testedArr),
                    () -> assertEquals(nativeCount, testedCount),
                    () -> assertEquals(0, testedCount)
            );
        }

        @Test
        @DisplayName("Read (byte[6],0,0)")
        void readBytesWithZeroLenght() throws Exception {
            testedArr = new byte[6];
            testedCount = testedStream.read(testedArr, 2, 0);
            nativeArr = new byte[6];
            nativeCount = nativeStream.read(nativeArr, 2, 0);
            assertAll( //
                    () -> assertArrayEquals(nativeArr, testedArr),
                    () -> assertEquals(nativeCount, testedCount),
                    () -> assertEquals(0, testedCount)
            );
        }

        @Test
        @DisplayName("Read from empty arr")
        void readBytesFromEmptyArray() throws Exception {

            testedStream = new ByteArrayInputStream(new byte[0]);
            testedArr = new byte[6];
            testedCount = testedStream.read(testedArr, 0, 4);

            nativeStream = new java.io.ByteArrayInputStream(new byte[0]);
            nativeArr = new byte[6];
            nativeCount = nativeStream.read(nativeArr, 0, 4);
            assertAll( //
                    () -> assertArrayEquals(nativeArr, testedArr),
                    () -> assertEquals(nativeCount, testedCount),
                    () -> assertEquals(-1, testedCount)
            );
        }


        @Test
        @DisplayName("Read with exception (byte[6],10,4) ")
        void readBytesIntoArrayWithWrongOffset() throws Exception {
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
        void readBytesIntoArrayWithWrongLength() throws Exception {
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
        void readBytesIntoArrayWithWrongSumOffsetAndLength() throws Exception {
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

        @AfterEach
        void cleanUp() throws IOException {
            testedStream.close();
            nativeStream.close();
        }

    }

    @Nested
    class CreatingTest {
        @Test
        @DisplayName("Try to create with wrong offset")
        void createWithWrongOffset() throws Exception {
            assertAll( //
                    () -> assertDoesNotThrow(() -> {
                        testedStream = new ByteArrayInputStream(initArr, -2, 3);
                    }),
                    () -> assertDoesNotThrow(() -> {
                        nativeStream = new java.io.ByteArrayInputStream(initArr, -2, 3);
                    })
            );
        }

        @Test
        @DisplayName("Try to create with wrong length")
        void createWithWrongLength() throws Exception {
            assertAll( //
                    () -> assertDoesNotThrow(() -> {
                        testedStream = new ByteArrayInputStream(initArr, 1, -3);
                    }),
                    () -> assertDoesNotThrow(() -> {
                        nativeStream = new java.io.ByteArrayInputStream(initArr, 1, -3);
                    })
            );
        }

        @Test
        @DisplayName("Try to create with too long length")
        void createWithTooLongLenth() throws Exception {
            assertAll( //
                    () -> assertDoesNotThrow(() -> {
                        testedStream = new ByteArrayInputStream(initArr, 1, 321);
                    }),
                    () -> assertDoesNotThrow(() -> {
                        nativeStream = new java.io.ByteArrayInputStream(initArr, 1, 321);
                    })
            );
        }

        @Test
        @DisplayName("Try to create with null")
        void createWithNullArray() throws Exception {
            assertAll( //
                    () -> assertThrows(NullPointerException.class, () -> {
                        testedStream = new ByteArrayInputStream(null);
                    }),
                    () -> assertThrows(NullPointerException.class, () -> {
                        nativeStream = new java.io.ByteArrayInputStream(null);
                    })
            );
        }
    }
}