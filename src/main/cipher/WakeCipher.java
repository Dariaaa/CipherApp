package main.cipher;

import javafx.scene.control.Alert;
import main.controller.EncryptionController;
import main.controller.UiController;
import main.util.DataStreamUtil;

import java.io.*;

public class WakeCipher extends ICipher {

    private static int s[] = {
            0x726a8f3b,
            0xe69a3b5c,
            0xd3c71fe5,
            0xab3c73d2,
            0x4d3a8eb3,
            0x0396d6e8,
            0x3d4c2f7a,
            0x9ee27cf3
    };


    private int[] generationSbox(int key[]) {
        int[] table = new int[257];
        int x, z;

        for (int i = 0; i < 4; i++) {
            table[i] = key[i];
        }
        for (int i = 4; i < 256; i++) {
            x = table[i - 4] + table[i - 1];
            table[i] = (x >>> 3) ^ s[x & 7];
        }

        for (int i = 0; i < 23; i++) {
            table[i] += table[i + 89];
        }
        x = table[33];
        z = table[59] | 0x01000001;
        z = z & 0xff7fffff;

        for (int i = 0; i < 256; i++) {
            x = (x & 0xff7fffff) + z;
            table[i] = table[i] & 0x00ffffff ^ x;
        }
        table[256] = table[0];
        x &= 255;
        for (int i = 0; i < 256; i++) {
            table[i] = table[x = (table[i ^ x] ^ x) & 255];
        }
        return table;
    }

    private void encryption(int mod, DataInputStream in, DataOutputStream out, int[] key) throws IOException {
        int table[] = generationSbox(key);
        int a = key[0];
        int b = key[1];
        int c = key[2];
        int d = key[3];
        Integer data = DataStreamUtil.getWordFromFile(in);
        while (data != null) {
            int outData = d ^ data;

            DataStreamUtil.saveWordToFile(outData, out);
            if (mod == EncryptionController.ENC_MOD) {
                a = functionM(a, outData, table);
            } else {
                a = functionM(a, data, table);
            }
            b = functionM(b, a, table);
            c = functionM(c, b, table);
            d = functionM(d, c, table);
            data = DataStreamUtil.getWordFromFile(in);
        }
        in.close();
        out.close();
    }

    private int functionM(int x, int y, int table[]) {
        return ((x + y) >> 8) ^ table[(x + y) & 0xff];
    }

    @Override
    public void run() {
        try {
            encryption(mode, new DataInputStream(new FileInputStream(inPath)),
                    new DataOutputStream(new FileOutputStream(outPath)), key);

            uiController.showSimpleAlert(Alert.AlertType.INFORMATION,"Готово!");
            System.out.println("finish");

        } catch (IOException e) {
            e.printStackTrace();
            uiController.showSimpleAlert(Alert.AlertType.ERROR,"Произошла ошибка во время выполнения операции!");
        }
    }
}
