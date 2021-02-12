package net.testlab.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ByteArrayInputStream extends InputStream {

    private byte[] buf;
    private int count;
    private int pos;

    /**
     * Set a byte array from which the data will be read.
     * Do not copy the array.
     *
     * @param buf - byte array to read
     */
    public ByteArrayInputStream(byte[] buf) {
        this(buf, 0, buf.length);
    }

    /**
     * Set also offset and max length to read.
     *
     * @param buf    - byte array to read
     * @param offset - offset in the array buf from which reading will be started
     * @param length - maximum length of bytes that can be read
     */
    public ByteArrayInputStream(byte[] buf, int offset, int length) {
        this.pos = offset;
        this.count = Math.min(buf.length, offset + length);
        this.buf = buf;
    }

    /**
     * Reads byte from buf and return it.
     * Cannot read bytes at and after count number
     *
     * @return byte read or -1 if end of allowed range
     */
    @Override
    public int read() {
        if (pos >= count)
            return -1;
        return buf[pos++];
    }

    /**
     * Reads data from underlying input stream into the destination byte array
     *
     * @param b   destination array
     * @param off offset in the destination array
     * @param len number of bytes that should be read
     * @return number of bytes read or -1 if there was no data to read
     */
    @Override
    public int read(byte[] b, int off, int len) {
        int readCount = 0;

        if (b == null)
            throw new NullPointerException(); //exception if array is null
        if (off < 0 || len < 0 || off > b.length - len)
            throw new IndexOutOfBoundsException(); //exception if off or len is incorrect

        for (int i = off; i < off + len; i++) {
            byte val = (byte) this.read();
            if (val < 0) { //  when no value to read
                if (readCount == 0) readCount = -1;   // if no value was read at all
                break; // exit cycle
            } else {
                b[i] = val;
            }

            readCount++;
        }
        return readCount;
    }

    /**
     * Has no effect
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
    }
}
