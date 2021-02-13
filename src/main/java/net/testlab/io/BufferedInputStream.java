package net.testlab.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends FilterInputStream {


    private byte[] buffer;
    private int count;
    private int position;
    private boolean isClosed;
    private static final int INITIAL_CAPACITY = 8 * 1024;

    /**
     * Takes input stream from which the data will be read
     *
     * @param in - underlying input stream
     */
    public BufferedInputStream(InputStream in) {
        this(in, INITIAL_CAPACITY);
    }

    /**
     * Takes input stream from which the data will be read
     * and the buffer size
     *
     * @param in       - underlying input stream
     * @param capacity - buffer size
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
        isClosed = false;
    }

    /**
     * Reads data from underlying input stream
     *
     * @return byte read or -1 if underlying input stream has no data to read
     * @throws IOException if thread is closed or get IOException in refillBuffer()
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
     *
     * @param bytes        - destination array
     * @param offset       - offset in the destination array
     * @param lengthToRead - number of bytes that should be read
     * @return number of bytes read or -1 if there was no data to read
     * @throws IOException if get IOException in read()
     */
    @Override
    public int read(byte[] bytes, int offset, int lengthToRead) throws IOException {
        int count = 0;

        checkIfClosed();
        validateReadParameters(bytes, offset, lengthToRead);

        if (bytes.length == 0 || lengthToRead == 0) {
            return 0;
        }

        for (int i = offset; i < offset + lengthToRead; i++) {
            byte val = (byte) this.read();
            if (val < 0) {
                if (count == 0) {
                    count = -1;
                }
                return count;
            } else {
                bytes[i] = val;
            }
            count++;
        }
        return count;
    }



    /**
     * Closes underlying input stream
     *
     * @throws IOException if get IOException in close()
     */
    @Override
    public void close() throws IOException {
        checkInnerStreamForNull();
        isClosed = true;
        in.close();

    }

    /**
     * Tries to read the data from the underlying input stream
     * to the buffer and reset the pos
     *
     * @throws IOException if get IOException in read()
     * @throws NullPointerException     if the underlying buffer is null
     */
    private void refillBuffer() throws IOException {
        checkInnerStreamForNull();
        count = in.read(buffer);
        position = 0;
    }

    /**
     * Check parameters of method read() for validity
     *
     * @param bytes        - destination array
     * @param offset       - offset in the destination array
     * @param lengthToRead - number of bytes that should be read
     * @throws NullPointerException     if bytes is null
     * @throws IllegalArgumentException if offset < 0, lengthToRead < 0 or
     *                                  offset > bytes.length - lengthToRead
     */
    private void validateReadParameters(byte[] bytes, int offset, int lengthToRead) {
        if (bytes == null) {
            throw new NullPointerException("Parameter \"bytes\" is null");
        }
        if (offset < 0 || lengthToRead < 0 || offset > bytes.length - lengthToRead) {
            throw new RuntimeException("Wrong \"offset\" and/or \"lengthToRead\"");
        }
    }

    /**
     * Checks stream is it's closed
     *
     * @throws IOException if the underlying input stream is closed
     */
    private void checkIfClosed() throws IOException {
        if (isClosed) {
            throw new IOException("Stream is closed");
        }
    }

    /**
     * Checks that the underlying stream is not null
     *
     * @throws NullPointerException if underlying stream is null
     */
    private void checkInnerStreamForNull() {
        if (in == null) {
            throw new NullPointerException("Underlying stream \"in\" is null");
        }
    }

}
