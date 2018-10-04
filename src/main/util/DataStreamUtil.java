package main.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataStreamUtil {

    public static Integer getWordFromFile(final DataInputStream in) throws IOException {
        byte[] dataBytes = new byte[4];
        int er = in.read(dataBytes, 0, dataBytes.length);
        if (er == -1) {
            System.out.println("end of file");
            return null;
        }
        if (er < 4) {
            System.out.println("bytes < 4");
            for (int i = er; i < 4; i++) {
                dataBytes[i] = (byte) 0xff;
            }
        }
        int word = (
                ((((int) dataBytes[0]) & 0xff) << 24) |
                ((((int) dataBytes[1]) & 0xff) << 16) |
                ((((int) dataBytes[2]) & 0xff) << 8) |
                (((int) dataBytes[3]) & 0xff)
        );
        return word;
    }
    public static void saveWordToFile(int word, final DataOutputStream out) throws IOException {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (word & 0xff);
        bytes[2] = (byte) ((word >> 8) & 0xff);
        bytes[1] = (byte) ((word >> 16) & 0xff);
        bytes[0] = (byte) ((word >> 24) & 0xff);
        out.write(bytes, 0, bytes.length);
    }
}
