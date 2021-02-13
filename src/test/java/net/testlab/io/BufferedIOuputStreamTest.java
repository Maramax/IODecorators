package net.testlab.io;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BufferedIOuputStreamTest {
    byte[] initArr = {1, 2, 3, 4, 5, 6, 7, 8};
    byte[] emptyArr = {};
    java.io.ByteArrayOutputStream testedByteStream;
    BufferedOuputStream testedStream;
    java.io.ByteArrayOutputStream nativeByteStream;
    java.io.BufferedOutputStream nativeStream;

    @Nested
    class SimpleWritingTest {

        @BeforeEach
        void setUp() {
            testedByteStream = new java.io.ByteArrayOutputStream();
            testedStream = new BufferedOuputStream(testedByteStream);

            nativeByteStream = new java.io.ByteArrayOutputStream();
            nativeStream = new java.io.BufferedOutputStream(nativeByteStream);
        }


        @Test
        @DisplayName("Write nothing")
        void writeNothing() throws Exception {
            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
            testedStream.flush();
            nativeStream.flush();
            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
        }

        @Test
        @DisplayName("Write a byte")
        void writeOneByte() throws Exception {

            testedStream.write(46);
            nativeStream.write(46);

            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
            testedStream.flush();
            nativeStream.flush();
            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
        }

        @Test
        @DisplayName("Write many bytes")
        void writeBytes() throws Exception {
            for (byte b : initArr) {
                testedStream.write(b);
            }
            for (byte b : initArr) {
                nativeStream.write(b);
            }

            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
            assertArrayEquals(emptyArr, testedByteStream.toByteArray());
            testedStream.flush();
            nativeStream.flush();
            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
            assertArrayEquals(initArr, testedByteStream.toByteArray());
        }

        @Test
        @DisplayName("Write over buffer size")
        void writeBytesOverBufferSize() throws Exception {
            testedStream = new BufferedOuputStream(testedByteStream, 3);
            for (byte b : initArr) {
                testedStream.write(b);
            }
            nativeStream = new java.io.BufferedOutputStream(nativeByteStream, 3);
            for (byte b : initArr) {
                nativeStream.write(b);
            }

            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
            testedStream.flush();
            nativeStream.flush();
            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
        }

        @Test
        @DisplayName("Write bytes and check data after closing")
        void checkFlushingAfterClose() throws Exception {
            testedStream = new BufferedOuputStream(testedByteStream, 3);
            for (byte b : initArr) {
                testedStream.write(b);
            }
            nativeStream = new java.io.BufferedOutputStream(nativeByteStream, 3);
            for (byte b : initArr) {
                nativeStream.write(b);
            }

            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
            testedStream.close();
            nativeStream.close();
            assertArrayEquals(nativeByteStream.toByteArray(), testedByteStream.toByteArray());
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
        testedByteStream = new java.io.ByteArrayOutputStream();
        nativeByteStream = new java.io.ByteArrayOutputStream();

        assertAll( //
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    testedStream = new BufferedOuputStream(testedByteStream, -3);
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    nativeStream = new java.io.BufferedOutputStream(nativeByteStream, -3);
                })
        );
    }

    @Test
    @DisplayName("Try to create and use with null stream parameter")
    void createWithNullStream() throws Exception {
        testedStream = new BufferedOuputStream(null);
        nativeStream = new java.io.BufferedOutputStream(null);

        testedStream.write(2);
        nativeStream.write(2);

        testedStream.write(new byte[]{3, 4, 5, 6});
        nativeStream.write(new byte[]{3, 4, 5, 6});

        testedStream.write(new byte[]{7, 8, 9, 10}, 1, 3);
        nativeStream.write(new byte[]{7, 8, 9, 10}, 1, 3);

        assertAll( //
                () -> assertThrows(NullPointerException.class, () -> {
                    testedStream.flush();
                }),
                () -> assertThrows(NullPointerException.class, () -> {
                    nativeStream.flush();
                })
        );

        assertAll( //
                () -> assertThrows(NullPointerException.class, () -> {
                    testedStream.close();
                }),
                () -> assertThrows(NullPointerException.class, () -> {
                    nativeStream.close();
                })
        );
    }

}