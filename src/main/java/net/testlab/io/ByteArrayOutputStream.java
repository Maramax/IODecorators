package net.testlab.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {

    private byte[] buf;
    private int count;
    private static final int DEFAULT_SIZE = 32;

    // private int size = 32;

    /**
     * Creates a byte array of default size
     */
    public ByteArrayOutputStream() {
        this(DEFAULT_SIZE);
    }

    /**
     * Creates a byte array of size indicated
     *
     * @param size - size of the byte array
     */
    public ByteArrayOutputStream(int size) {
        //  this.size = size;
        if (size < 0)
            throw new IllegalArgumentException();
        this.buf = new byte[size];
        this.count = 0;
    }

    /**
     * Writes a byte value into the byte array buf.
     * If the buffer space is out - rewrites buf to larger array
     *
     * @param b - byte to be write
     * @throws IOException
     */
    @Override
    public void write(int b) {
        // extending buf if out of range
        if (count >= buf.length) {
            byte[] temp = buf;
            buf = new byte[buf.length * 2];
            for (int i = 0; i < temp.length; i++) {
                buf[i] = temp[i];
            }
        }
        // write to buf
        buf[count++] = (byte) b;
    }

    /**
     * Takes bytes from a byte array and writes all of them.
     *
     * @param b   - byte array with bytes to be write
     * @param off - offset in the byte array
     * @param len - number of bytes that should be write
     * @throws IOException
     */
    @Override
    public void write(byte[] b, int off, int len) {

        if (b == null)
            throw new NullPointerException(); //exception if array is null
        if (off < 0 || len < 0 || off > b.length - len)
            throw new RuntimeException(); //exception if off or len is incorrect

        for (int i = off; i < off + len; i++) {
            this.write(b[i]);
        }

    }

    /**
     * Has no effect
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
    }

    /**
     * Returns the data written into the bytes array
     *
     * @return bytes array of data written
     */
    public byte[] toByteArray() {
        byte[] newBuf = Arrays.copyOf(buf, count);
        return newBuf;
    }


}
