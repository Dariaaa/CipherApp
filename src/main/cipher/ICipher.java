package main.cipher;

import main.controller.UiController;

public abstract class ICipher implements Runnable {

    public void setKey(int[] key) {
        this.key = key;
    }

    public void setInPath(String inPath) {
        this.inPath = inPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setUiController(UiController uiController) {
        this.uiController = uiController;
    }

    protected int[] key;
    protected String inPath;
    protected String outPath;
    protected int mode;
    protected UiController uiController;
}
