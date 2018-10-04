package main.controller;

import main.cipher.ICipher;
import main.cipher.WakeCipher;

import java.io.*;

public class EncryptionController {
    public static final int TYPE_WAKE = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;

    private static int[] key = {0x50505050, 0x50505050, 0x50505050, 0x50505050};

    private ICipher cipher;

    public void encryption(String inFile, String outFile) throws IOException {

            cipher.encryption(new DataInputStream(new FileInputStream(inFile)),
                    new DataOutputStream(new FileOutputStream(outFile)));

    }

    public void decryption(String inFile, String outFile) throws IOException {

            cipher.decryption(new DataInputStream(new FileInputStream(inFile)),
                    new DataOutputStream(new FileOutputStream(outFile)));

    }


    public void setType(int cryptType) {
        switch (cryptType){
            case TYPE_WAKE:
                cipher = new WakeCipher(key);
        }
    }
}
