package net.testlab.io;

import java.io.*;

public class BufferedOuputStream extends FilterOutputStream {


    private byte[] buffer;
    private int count;
    private static final int INITIAL_CAPACITY = 8 * 1024;

    /**
     * Takes output stream into which the data should be written.
     * Creates buffer with default capacity.
     *
     * @param out - underlying output stream
     */
    public BufferedOuputStream(OutputStream out) {
        this(out, INITIAL_CAPACITY);
    }

    /**
     * Takes output stream into which the data should be write
     * and the buffer capacity. Creates buffer with the indicated capacity.
     *
     * @param out      - underlying output stream
     * @param capacity - buffer capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public BufferedOuputStream(OutputStream out, int capacity) {
        super(out);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Wrong \"capacity\"");
        }
        buffer = new byte[capacity];
        count = 0;
    }

    /**
     * Writes a byte value into the buffer.
     * If the buffer space is out - invoke flush() method
     *
     * @param b - byte to be write
     * @throws IOException if gets IOException in flush()
     */
    @Override
    public void write(int b) throws IOException {
        // write to stream if buf is full
        if (count >= buffer.length) {
            flush();
        }
        // write to buf
        buffer[count++] = (byte) b;
    }

    /**
     * Takes bytes from a byte array and writes all of them into the buffer
     * invoking write() method in cycle
     *
     * @param bytes         - byte array with bytes to be write
     * @param offset        - offset in the byte array
     * @param lengthToWrite - number of bytes that should be write
     * @throws IOException if get IOException in write()
     * @throws IllegalArgumentException if offset < 0, lengthToRead < 0 or
     *                                  offset > bytes.length - lengthToRead
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
     * flush it and reset count
     *
     * @throws IOException if get IOException in write() or flush()
     */
    @Override
    public void flush() throws IOException {
        checkInnerStreamForNull();
        out.write(buffer, 0, count);
        out.flush();
        count = 0;
    }


    /**
     * Closes the underlying input stream invoking its close() method
     * and flush data
     *
     * @throws IOException if get IOException in close()
     */
    @Override
    public void close() throws IOException {
        checkInnerStreamForNull();
        flush();
        out.close();
    }

    /**
     * Check parameters received for validity
     *
     * @param bytes         - byte array with bytes to be write
     * @param offset        - offset in the byte array
     * @param lengthToWrite - number of bytes that should be write
     * @throws IllegalArgumentException if offset < 0, lengthToRead < 0 or
     *                                  offset > bytes.length - lengthToRead
     */
    private void validateWriteParameters(byte[] bytes, int offset, int lengthToWrite) {
        if (bytes == null) {
            throw new NullPointerException("Parameter \"bytes\" is null");
        }
        if (offset < 0 || lengthToWrite < 0 || offset > bytes.length - lengthToWrite) {
            throw new IllegalArgumentException("Wrong \"offset\" and/or \"lenghtToRead\"");
        }
    }

    /**
     * Checks if the underlying stream is not null
     */
    private void checkInnerStreamForNull() {
        if (out == null) {
            throw new NullPointerException("Stream \"out\" is null");
        }
    }

}
