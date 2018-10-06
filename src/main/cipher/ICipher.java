package main.cipher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ICipher {

    void decryption(int[] keyArr, DataInputStream in, DataOutputStream out) throws IOException;

    void encryption(int[] keyArr, DataInputStream in, DataOutputStream out) throws IOException;
}
