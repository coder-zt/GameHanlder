package com.coder.zt.gamehanlder.net;

public class ResponseResult {
    private boolean isSucess;
    private int keyCode;

    public int getKeyCode() {
        return keyCode;
    }

    public ResponseResult setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        return this;
    }

    public boolean isSucess() {
        return isSucess;
    }

    public ResponseResult setSucess(boolean isSucess) {
        this.isSucess = isSucess;
        return this;
    }
}
