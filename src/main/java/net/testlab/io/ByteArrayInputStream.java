package net.testlab.io;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {

    private byte[] buffer;
    private int count;
    private int position;

    /**
     * Set a byte array from which the data will be read.
     * Do not copy the array.
     *
     * @param byteArray - byte array to read
     */
    public ByteArrayInputStream(byte[] byteArray) {
        this(byteArray, 0, byteArray.length);
    }

    /**
     * Set also offset and max length to read.
     *
     * @param byteArray    - byte array to read
     * @param offset - offset in the array buf from which reading will be started
     * @param length - maximum length of bytes that can be read
     */
    public ByteArrayInputStream(byte[] byteArray, int offset, int length) {
        this.position = offset;
        this.count = Math.min(byteArray.length, offset + length);
        this.buffer = byteArray;
    }

    /**
     * Reads byte from buf and return it.
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
     * Reads data from underlying input stream into the destination byte array
     *
     * @param bytes   destination array
     * @param offset offset in the destination array
     * @param lengthToRead number of bytes that should be read
     * @return number of bytes read or -1 if there was no data to read
     */
    @Override
    public int read(byte[] bytes, int offset, int lengthToRead) {
        int readCount = 0;

       validateReadParameters(bytes,offset,lengthToRead);

        if (bytes.length == 0 || lengthToRead == 0) {
            return 0;
        }

        for (int i = offset; i < offset + lengthToRead; i++) {
            byte val = (byte) this.read();
            if (val < 0) {
                if (readCount == 0) {
                    readCount = -1;
                }
                return readCount;
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
    public void close()  {
    }

    /**
     * Check parameters of method read() for validity
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
            throw new RuntimeException("Wrong \"offset\" and/or \"lengthToRead\"");
        }
    }

}
