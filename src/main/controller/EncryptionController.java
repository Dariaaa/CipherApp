package main.controller;

import main.cipher.ICipher;
import main.cipher.WakeCipher;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EncryptionController {
    public static final int TYPE_WAKE = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;

    public static final int ENC_MOD = 1;
    public static final int DEC_MOD = 2;

    private UiController uiController;

    public EncryptionController(UiController uiController){
        this.uiController = uiController;
    }
    public String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(Charset.forName("UTF-8"))));
    }
    public void encryption(String inFile, String outFile, String key, int cipherType, int mode) {
        String hexKey = toHex(key);
        int k=0;
        for(int i=0;i<hexKey.length();i++){
            if (hexKey.charAt(i) == '0'){
                k=i;
            }else {
                break;
            }
        }
        hexKey = hexKey.substring(k+1);
        int[] keyArr = getIntKey(hexKey);

        ICipher iCipher = null;
        switch (cipherType){
            case TYPE_WAKE:
                iCipher = new WakeCipher();
        }
        if (iCipher==null){
            return;
        }
        iCipher.setKey(keyArr);
        iCipher.setInPath(inFile);
        iCipher.setOutPath(outFile);
        iCipher.setMode(mode);
        iCipher.setUiController(uiController);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(iCipher);
        executorService.shutdown();

    }


    public int[] getIntKey(String key) {
//        char c;
//        int[] keyArr = new int[key.length()];
//        for (int i = 0; i < key.length(); i++) {
//            c = key.charAt(i);
//            keyArr[i] = (int)c;
//        }
        int keyArr[] = new int[4];
        for(int i=0;i<4;i++){
            String str = key.substring(i*key.length()/4,i*key.length()/4+key.length()/4);
            int val = Integer.valueOf(str, 16);
            keyArr[i] = val;
        }
        return keyArr;
    }


}
