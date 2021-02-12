package net.testlab.io;

import java.io.*;

public class BufferedOuputStream extends FilterOutputStream {


    private byte[] buf;
    private int count;
    private static final int DEFAULT_SIZE = 8 * 1024;

    /**
     * Takes output stream into which the data will be write
     *
     * @param out - underlying output stream
     */
    public BufferedOuputStream(OutputStream out) {
        this(out, DEFAULT_SIZE);
    }

    /**
     * Takes output stream into which the data will be write
     * and the buffer size
     *
     * @param out  - underlying output stream
     * @param size - buffer size
     */
    public BufferedOuputStream(OutputStream out, int size) {
        super(out);
        if (size <= 0)
            throw new IllegalArgumentException();
        buf = new byte[size];
        count = 0;
    }

    /**
     * Writes a byte value into the buffer.
     * If the buffer space is out - write the data from the buffer
     * into underlying output stream
     *
     * @param b - byte to be write
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        // write to stream if buf is full
        if (count >= buf.length) {
            writeToStream();
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
    public void write(byte[] b, int off, int len) throws IOException {

        if (b == null)
            throw new NullPointerException(); //exception if array is null
        if (off < 0 || len < 0 || off > b.length - len)
            throw new RuntimeException(); //exception if off or len is incorrect

        for (int i = off; i < off + len; i++) {
            this.write(b[i]);
        }

    }

    /**
     * Writes the data from the buffer into the underlying output stream
     *
     * @throws IOException
     */
    @Override
    public void flush() throws IOException {
        writeToStream();
    }

    /**
     * Closes underlying output stream
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (out != null)
            out.close();
    }

    /**
     * Tries to write the data from the buffer into the underlying
     * output stream and reset the count
     *
     * @throws IOException
     */
    private void writeToStream() throws IOException {
        if (out == null)
            throw new IOException();
        out.write(buf, 0, count);
        count = 0;
    }


}
