package main.cipher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ICipher {

    void decryption(DataInputStream in, DataOutputStream out) throws IOException;

    void encryption(DataInputStream in, DataOutputStream out) throws IOException;
}
