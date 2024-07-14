package com.quexs.andipc.ipc;

import android.os.RemoteException;

import com.quexs.andipc.AndIPCCallback;
import com.quexs.andipc.AndIPCInterface;


public class OnceIPCCallback extends AndIPCCallback.Stub{
    private final String onceTag;
    private final AndIPCInterface andIPCInterface;
    private final OnceIpcCallbackListener onceIpcCallbackListener;

    public OnceIPCCallback(String onceTag, AndIPCInterface andIPCInterface, OnceIpcCallbackListener onceIpcCallbackListener){
        this.onceTag = onceTag;
        this.andIPCInterface = andIPCInterface;
        this.onceIpcCallbackListener = onceIpcCallbackListener;
    }

    @Override
    public void receiveMessages(String sourceTag, String message, boolean isNeedCallback) throws RemoteException {
        if(onceIpcCallbackListener != null){
            onceIpcCallbackListener.receiveMessages(sourceTag, message, isNeedCallback);
        }
        this.andIPCInterface.unregisterCallback(onceTag);
    }
}
