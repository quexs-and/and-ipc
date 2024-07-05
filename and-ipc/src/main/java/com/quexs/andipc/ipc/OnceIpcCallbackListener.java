package com.quexs.andipc.ipc;

public interface OnceIpcCallbackListener {

    void receiveMessages(String sourceTag, String message, boolean isNeedCallback);
}
