package net.testlab.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends FilterInputStream {
    private byte[] buffer;
    private int count;
    private int position;
    private static final int INITIAL_CAPACITY = 8 * 1024;

    /**
     * Takes input stream from which the data should be read.
     * Create buffer with default capacity
     *
     * @param in - underlying input stream
     */
    public BufferedInputStream(InputStream in) {
        this(in, INITIAL_CAPACITY);
    }

    /**
     * Takes input stream from which the data should be read
     * and the buffer capacity. Creates buffer with the indicated capacity
     *
     * @param in       - underlying input stream
     * @param capacity - buffer capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public BufferedInputStream(InputStream in, int capacity) {
        super(in);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Wrong \"capacity\"");
        }
        buffer = new byte[capacity];
        position = 0;
        count = 0;
    }

    /**
     * Reads data from the underlying input stream through the buffer.
     * If there are data in the buffer - read data from buffer,
     * otherwise - read new portion of data into buffer from the underlying input
     *
     * @return byte read or -1 if underlying input stream has no data to read
     * @throws IOException if stream is closed or gets exception
     *                     while reading the underlying stream
     */
    @Override
    public int read() throws IOException {
        checkIfClosed();
        if (position >= count) {
            refillBuffer();
        }
        if (count < 0) {
            return -1;
        }
        return buffer[position++];
    }


    /**
     * Reads data from underlying input stream into the destination byte array
     * through the buffer invoking read() method in cycle.
     * Returns number of bytes read.
     *
     * @param bytes        - destination array
     * @param offset       - offset in the destination array
     * @param lengthToRead - number of bytes that should be read
     * @return number of bytes read or -1 if there was no data to read
     * @throws IOException if gets IOException in read()
     * @throws IllegalArgumentException if offset < 0, lengthToRead < 0 or
     *                                     offset > bytes.length - lengthToRead
     */
    @Override
    public int read(byte[] bytes, int offset, int lengthToRead) throws IOException {
        checkIfClosed();
        validateReadParameters(bytes, offset, lengthToRead);
        if (bytes.length == 0 || lengthToRead == 0) {
            return 0;
        }

        int readCount = 0;
        for (int i = offset; i < offset + lengthToRead; i++) {
            byte val = (byte) this.read();
            if (val < 0) {
                return readCount != 0 ? readCount : -1;
            } else {
                bytes[i] = val;
            }
            readCount++;
        }
        return readCount;
    }

    /**
     * Closes the underlying input stream invoking its close() method
     * and set it to null
     *
     * @throws IOException if gets IOException in close() method
     */
    @Override
    public void close() throws IOException {
        in.close();
        in = null;
    }

    /**
     * Tries to read the data from the underlying input stream
     * to the buffer and reset the position
     *
     * @throws IOException if gets exception while reading from the underlying stream
     */
    private void refillBuffer() throws IOException {
        count = in.read(buffer);
        position = 0;
    }

    /**
     * Check parameters received for validity
     *
     * @param bytes        - destination array
     * @param offset       - offset in the destination array
     * @param lengthToRead - number of bytes that should be read
     * @throws IllegalArgumentException if offset < 0, lengthToRead < 0 or
     *                                  offset > bytes.length - lengthToRead
     */
    private void validateReadParameters(byte[] bytes, int offset, int lengthToRead) {
        if (bytes == null) {
            throw new NullPointerException("Parameter \"bytes\" is null");
        }
        if (offset < 0 || lengthToRead < 0 || offset > bytes.length - lengthToRead) {
            throw new IllegalArgumentException("Wrong \"offset\" and/or \"lengthToRead\"");
        }
    }

    /**
     * Checks if the underlying stream is closed
     *
     * @throws IOException if the underlying stream is null
     */
    private void checkIfClosed() throws IOException {
        if (in == null) {
            throw new IOException("Stream is closed");
        }
    }

}
