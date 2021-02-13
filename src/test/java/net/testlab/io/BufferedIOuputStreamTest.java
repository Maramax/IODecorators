package net.testlab.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

    }

    @Nested
    class CreatingTest {
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
    }
}