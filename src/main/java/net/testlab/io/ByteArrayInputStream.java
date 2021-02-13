package net.testlab.io;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {

    private byte[] buffer;
    private int count;
    private int position;

    /**
     * Takes a byte array from which the data will be read.
     * Does not copy the array.
     *
     * @param byteArray - byte array to read
     */
    public ByteArrayInputStream(byte[] byteArray) {
        this(byteArray, 0, byteArray.length);
    }

    /**
     * Takes a byte array from which the data will be read, its offset and max length to read.
     * Does not copy the array.
     *
     * @param byteArray - byte array to read
     * @param offset    - offset in the array buf from which reading will be started
     * @param length    - maximum length of bytes that can be read
     */
    public ByteArrayInputStream(byte[] byteArray, int offset, int length) {
        this.position = offset;
        this.count = Math.min(byteArray.length, offset + length);
        this.buffer = byteArray;
    }

    /**
     * Reads a byte from buffer and return it.
     * Cannot read bytes at and after count number
     *
     * @return byte read or -1 if end of allowed range
     */
    @Override
    public int read() {
        if (position >= count) {
            return -1;
        }
        return buffer[position++];
    }

    /**
     * Reads data from the buffer into the destination byte array
     * invoking read() method in cycle.
     * Returns number of bytes read.
     *
     * @param bytes        destination array
     * @param offset       offset in the destination array
     * @param lengthToRead number of bytes that should be read
     * @return number of bytes read or -1 if there was no data to read
     */
    @Override
    public int read(byte[] bytes, int offset, int lengthToRead) {
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
     * Has no effect
     */
    @Override
    public void close() {
    }

    /**
     * Check parameters received for validity
     *
     * @param offset       - offset in the destination array
     * @param lengthToRead - number of bytes that should be read
     * @return number of bytes read or -1 if there was no data to read
     */
    private void validateReadParameters(byte[] bytes, int offset, int lengthToRead) {
        if (bytes == null) {
            throw new NullPointerException("Parameter \"bytes\" is null");
        }
        if (offset < 0 || lengthToRead < 0 || offset > bytes.length - lengthToRead) {
            throw new IllegalArgumentException("Wrong \"offset\" and/or \"lengthToRead\"");
        }
    }

}
