package net.testlab.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends FilterInputStream {


    private byte[] buf;
    private int count;
    private int pos;
    private boolean isClosed;
    private static final int DEFAULT_SIZE = 8 * 1024;

    /**
     * Takes input stream from which the data will be read
     *
     * @param in - underlying input stream
     */
    public BufferedInputStream(InputStream in) {
        this(in, DEFAULT_SIZE);
    }

    /**
     * Takes input stream from which the data will be read
     * and the buffer size
     *
     * @param in   - underlying input stream
     * @param size - buffer size
     */
    public BufferedInputStream(InputStream in, int size) {
        super(in);
        if (size <= 0)
            throw new IllegalArgumentException();
        buf = new byte[size];
        pos = 0;
        count = 0;
        isClosed = false;
    }

    /**
     * Reads data from underlying input stream
     *
     * @return byte read or -1 if underlying input stream has no data to read
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        checkIfClosed();
        if (pos >= count) {
            refillBuffer();
        }
        if (count < 0)
            return -1;
        return buf[pos++];
    }


    /**
     * Reads data from underlying input stream into the destination byte array
     *
     * @param b   - destination array
     * @param off - offset in the destination array
     * @param len - number of bytes that should be read
     * @return number of bytes read or -1 if there was no data to read
     * @throws IOException
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int count = 0;

        checkIfClosed();
        if (b == null)
            throw new NullPointerException(); //exception if array is null
        if (off < 0 || len < 0 || off > b.length - len)
            throw new RuntimeException(); //exception if off or len is incorrect

        for (int i = off; i < off + len; i++) {
            byte val = (byte) this.read();
            if (val < 0) { //  when no value to read
                if (count == 0) count = -1;   // if no value was read at all
                break; // exit cycle
            } else {
                b[i] = val;
            }
            count++;
        }
        return count;
    }

    /**
     * Closes underlying input stream
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        isClosed = true;
        if (in != null)
            in.close();
    }

    /**
     * Tries to read the data from the underlying input stream
     * to the buffer and reset the pos
     *
     * @throws IOException
     */
    private void refillBuffer() throws IOException {
        if (in == null)
            throw new IOException();
        count = in.read(buf);
        pos = 0;
    }

    /**
     * Checks stream is it's closed
     * @throws IOException
     */
    private void checkIfClosed() throws IOException {
        if(isClosed) throw new IOException("Stream is closed");
    }

}
