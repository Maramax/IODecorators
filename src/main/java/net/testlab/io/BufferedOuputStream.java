package net.testlab.io;

import java.io.*;

public class BufferedOuputStream extends FilterOutputStream {


    private byte[] buffer;
    private int count;
    private static final int INITIAL_CAPACITY = 8 * 1024;

    /**
     * Takes output stream into which the data will be write
     *
     * @param out - underlying output stream
     */
    public BufferedOuputStream(OutputStream out) {
        this(out, INITIAL_CAPACITY);
    }

    /**
     * Takes output stream into which the data will be write
     * and the buffer size
     *
     * @param out      - underlying output stream
     * @param capacity - buffer size
     */
    public BufferedOuputStream(OutputStream out, int capacity) {
        super(out);
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        buffer = new byte[capacity];
        count = 0;
    }

    /**
     * Writes a byte value into the buffer.
     * If the buffer space is out - write the data from the buffer
     * into underlying output stream
     *
     * @param b - byte to be write
     * @throws IOException if get IOException in writeToStream()
     */
    @Override
    public void write(int b) throws IOException {
        System.out.println(count);
        // write to stream if buf is full
        if (count >= buffer.length) {
            flush();
        }
        // write to buf
        buffer[count++] = (byte) b;
    }

    /**
     * Takes bytes from a byte array and writes all of them.
     *
     * @param bytes         - byte array with bytes to be write
     * @param offset        - offset in the byte array
     * @param lengthToWrite - number of bytes that should be write
     * @throws IOException if get IOException in write()
     */
    @Override
    public void write(byte[] bytes, int offset, int lengthToWrite) throws IOException {

        validateWriteParameters(bytes, offset, lengthToWrite);

        if (bytes.length == 0 || lengthToWrite == 0) {
            return;
        }

        for (int i = offset; i < offset + lengthToWrite; i++) {
            this.write(bytes[i]);
        }

    }



    /**
     * Writes the data from the buffer into the underlying output stream
     *
     * @throws IOException if get IOException in write()
     */
    @Override
    public void flush() throws IOException {
        checkInnerStreamForNull();
        out.write(buffer, 0, count);
        count = 0;
    }


    /**
     * Closes underlying output stream
     *
     * @throws IOException if get IOException in close()
     */
    @Override
    public void close() throws IOException {
        checkInnerStreamForNull();
        out.close();
    }

    /**
     * Check parameters of method write() for validity
     *
     * @param bytes         - byte array with bytes to be write
     * @param offset        - offset in the byte array
     * @param lengthToWrite - number of bytes that should be write
     */
    private void validateWriteParameters(byte[] bytes, int offset, int lengthToWrite) {
        if (bytes == null) {
            throw new NullPointerException("Parameter \"bytes\" is null");
        }
        if (offset < 0 || lengthToWrite < 0 || offset > bytes.length - lengthToWrite) {
            throw new RuntimeException("Wrong \"offset\" and/or \"lenghtToRead\"");
        }
    }

    /**
     * Checks that the underlying stream is not null
     *
     * @throws NullPointerException if underlying stream is null
     */
    private void checkInnerStreamForNull() {
        if (out == null) {
            throw new NullPointerException("Underlying stream \"out\" is null");
        }
    }

}
