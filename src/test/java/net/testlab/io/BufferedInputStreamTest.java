package net.testlab.io;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BufferedInputStreamTest {
    final byte[] initArr = {1, 2, 3};

    java.io.ByteArrayInputStream testedByteStream;
    java.io.ByteArrayInputStream nativeByteStream;

    BufferedInputStream testedStream;
    java.io.BufferedInputStream nativeStream;

    int testedValue;
    int nativeValue;

    @Nested
    class SimpleReadTest {
        @BeforeEach
        void setUp() {
            testedByteStream = new java.io.ByteArrayInputStream(initArr);
            testedStream = new BufferedInputStream(testedByteStream);

            nativeByteStream = new java.io.ByteArrayInputStream(initArr);
            nativeStream = new java.io.BufferedInputStream(nativeByteStream);
        }


        @Test
        @DisplayName("Read nothing")
        void readNothing() throws Exception {
            testedByteStream = new java.io.ByteArrayInputStream(new byte[0]);
            testedStream = new BufferedInputStream(testedByteStream);

            nativeByteStream = new java.io.ByteArrayInputStream(new byte[0]);
            nativeStream = new java.io.BufferedInputStream(nativeByteStream);

            testedValue = testedStream.read();
            nativeValue = nativeStream.read();
            assertEquals(nativeValue, testedValue);

        }

        @Test
        @DisplayName("Read bytes")
        void readBytes() throws Exception {
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

        @Test
        @DisplayName("Read after close")
        void readWithError() throws Exception {
            testedStream.close();
            nativeStream.close();
            assertAll( //
                    () -> assertThrows(IOException.class, () -> {
                        testedStream.read();
                    }),
                    () -> assertThrows(IOException.class, () -> {
                        nativeStream.read();
                    })
            );
        }

        @AfterEach
        void cleanUp() throws IOException {
            testedByteStream.close();
            testedStream.close();
            nativeByteStream.close();
            nativeStream.close();
        }
    }


    @Test
    @DisplayName("Try to create with wrong size")
    void createWithWrongCapacity() throws Exception {
        testedByteStream = new java.io.ByteArrayInputStream(initArr);
        nativeByteStream = new java.io.ByteArrayInputStream(initArr);

        assertAll( //
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    testedStream = new BufferedInputStream(testedByteStream, -3);
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    nativeStream = new java.io.BufferedInputStream(nativeByteStream, -3);
                })
        );
    }

    @Test
    @DisplayName("Try to create and use with null stream parameter")
    void createWithNullStream() throws Exception {
        testedStream = new BufferedInputStream(null);
        nativeStream = new java.io.BufferedInputStream(null);

        assertAll( //
                () -> assertThrows(IOException.class, () -> {
                    int val = testedStream.read();
                }),
                () -> assertThrows(IOException.class, () -> {
                    int val = nativeStream.read();
                })
        );
    }

}