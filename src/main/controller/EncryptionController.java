package main.controller;

import main.cipher.ICipher;
import main.cipher.WakeCipher;

import java.io.*;

public class EncryptionController {
    public static final int TYPE_WAKE = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;

    private int[] key = null;

    private ICipher cipher;

    public void encryption(String inFile, String outFile, String key) throws IOException {
        int[] keyArr = getIntKey(key);

        cipher.encryption(keyArr, new DataInputStream(new FileInputStream(inFile)),
                new DataOutputStream(new FileOutputStream(outFile)));

    }

    public void decryption(String inFile, String outFile, String key) throws IOException {
        int[] keyArr = getIntKey(key);
        cipher.decryption(keyArr, new DataInputStream(new FileInputStream(inFile)),
                new DataOutputStream(new FileOutputStream(outFile)));

    }

    public int[] getIntKey(String key) {
        char c;
        int[] keyArr = new int[key.length()];
        for (int i = 0; i < key.length(); i++) {
            c = key.charAt(i);
            keyArr[i] = (int)c;
        }
        return keyArr;
    }


    public void setType(int cryptType) {
        switch (cryptType) {
            case TYPE_WAKE:
                cipher = new WakeCipher();
        }
    }
}
