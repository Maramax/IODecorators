package net.testlab.io;

import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {

    private byte[] buffer;
    private int count;
    private static final int INITIAL_CAPACITY = 32;

    /**
     * Creates an inner byte array into which the data should be written
     * with default capacity.
     */
    public ByteArrayOutputStream() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Takes byte array capacity. Creates an inner byte array into which
     * the data should be written with indicated capacity.
     *
     * @param capacity - capacity of the byte array
     */
    public ByteArrayOutputStream(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Wrong \"capacity\"");
        }
        this.buffer = new byte[capacity];
        this.count = 0;
    }

    /**
     * Writes a byte value into the inner byte array.
     * If the byte array is out of capacity - rewrites it to a larger array
     *
     * @param b - byte to be write
     */
    @Override
    public void write(int b) {
        // extending buf if out of range
        if (count >= buffer.length) {
            byte[] temp = buffer;
            buffer = new byte[buffer.length * 2];
            for (int i = 0; i < temp.length; i++) {
                buffer[i] = temp[i];
            }
        }
        // write to buf
        buffer[count++] = (byte) b;
    }

    /**
     * Takes bytes from a byte array and writes all of them into
     * the inner byte array invoking write() method in cycle.
     *
     * @param bytes         - byte array with bytes to be write
     * @param offset        - offset in the byte array
     * @param lengthToWrite - number of bytes that should be write
     */
    @Override
    public void write(byte[] bytes, int offset, int lengthToWrite) {

        validateWriteParameters(bytes, offset, lengthToWrite);
        for (int i = offset; i < offset + lengthToWrite; i++) {
            this.write(bytes[i]);
        }

    }


    /**
     * Has no effect
     */
    @Override
    public void close() {
    }

    /**
     * Returns the data written into the inner bytes array
     *
     * @return bytes array of data written
     */
    public byte[] toByteArray() {
        byte[] newBuf = Arrays.copyOf(buffer, count);
        return newBuf;
    }

    /**
     * Check parameters received for validity
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
            throw new IllegalArgumentException("Wrong \"offset\" and/or \"lenghtToRead\"");
        }
    }

}
