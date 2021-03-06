package net.testlab.io;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayOutputStreamTest {

    byte[] initArr = {1, 2, 3, 4, 5, 6, 7, 8};
    byte[] emptyArr = {};

    ByteArrayOutputStream testedStream;
    java.io.ByteArrayOutputStream nativeStream;

    @Nested
    class SimpleWritingTest {


        @BeforeEach
        void setUp() {
            testedStream = new ByteArrayOutputStream();
            nativeStream = new java.io.ByteArrayOutputStream();
        }

        @Test
        @DisplayName("Write nothing")
        void writeNothing() throws Exception {
            assertArrayEquals(testedStream.toByteArray(), nativeStream.toByteArray());
        }

        @Test
        @DisplayName("Write a byte")
        void writeOneByte() throws Exception {
            testedStream.write(46);
            nativeStream.write(46);
            assertArrayEquals(testedStream.toByteArray(), nativeStream.toByteArray());
        }

        @Test
        @DisplayName("Write many bytes")
        void writeManyBytes() throws Exception {
            for (byte b : initArr) {
                testedStream.write(b);
            }
            for (byte b : initArr) {
                nativeStream.write(b);
            }
            assertArrayEquals(testedStream.toByteArray(), nativeStream.toByteArray());
        }


        @Test
        @DisplayName("Write with predefined buf size")
        void writeWithPredefinedCapacity() throws Exception {
            testedStream = new ByteArrayOutputStream(3);
            for (byte b : initArr) {
                testedStream.write(b);
            }
            nativeStream = new java.io.ByteArrayOutputStream(3);
            for (byte b : initArr) {
                nativeStream.write(b);
            }
            assertArrayEquals(testedStream.toByteArray(), nativeStream.toByteArray());
        }

        @AfterEach
        void cleanUp() throws IOException {
            testedStream.close();
            nativeStream.close();
        }
    }


    @Test
    @DisplayName("Try to create with wrong size")
    void createWithWrongCapacity() throws Exception {

        assertAll( //
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    testedStream = new ByteArrayOutputStream(-3);
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    nativeStream = new java.io.ByteArrayOutputStream(-3);
                })
        );
    }
}