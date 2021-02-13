package net.testlab.io;

import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {

    private byte[] buffer;
    private int count;
    private static final int INITIAL_CAPACITY = 32;

    // private int size = 32;

    /**
     * Creates a byte array of default size
     */
    public ByteArrayOutputStream() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Creates a byte array of size indicated
     *
     * @param capacity - size of the byte array
     */
    public ByteArrayOutputStream(int capacity) {
        //  this.size = size;
        if (capacity < 0) {
            throw new IllegalArgumentException("Wrong \"capacity\"");
        }
        this.buffer = new byte[capacity];
        this.count = 0;
    }

    /**
     * Writes a byte value into the byte array buf.
     * If the buffer space is out - rewrites buf to larger array
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
     * Takes bytes from a byte array and writes all of them.
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
     * Returns the data written into the bytes array
     *
     * @return bytes array of data written
     */
    public byte[] toByteArray() {
        byte[] newBuf = Arrays.copyOf(buffer, count);
        return newBuf;
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

}
