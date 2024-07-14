package com.quexs.andipc.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.quexs.andipc.AndIPCCallback;
import com.quexs.andipc.AndIPCInterface;
import com.quexs.andipc.utils.ApkUtils;

/**
* @date 2024/7/5 22:43
* @author Quexs
* @Description Android IPC
*/
public class AndIPC {
    private ContextWrapper wrapper;
    private ServiceConnection serviceConnection;
    private AndIPCInterface andPICInterface;
    private final AndIPCCallback.Stub andIPCCallback;
    private final String ownTag;

    /**
     *
     * @param wrapper
     * @param andIPCCallback 消息接收器
     */
    public AndIPC(ContextWrapper wrapper, AndIPCCallback.Stub andIPCCallback){
        this(wrapper, ApkUtils.getProcessName(), andIPCCallback);
    }

    /**
     *
     * @param wrapper
     * @param ownTag 消息接收器绑定的标签
     * @param andIPCCallback 消息接收器
     */
    public AndIPC(ContextWrapper wrapper, String ownTag, AndIPCCallback.Stub andIPCCallback){
        this.wrapper = wrapper;
        this.ownTag = ownTag;
        this.andIPCCallback = andIPCCallback;
    }

    /**
     * 绑定进程间通信服务服务
     */
    public void bindIPCService(){
        this.serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                andPICInterface = AndIPCInterface.Stub.asInterface(service);
                if(andIPCCallback != null){
                    try {
                        andPICInterface.registerCallback(ownTag, andIPCCallback);
                    } catch (RemoteException e) {
                        Log.e("IPC","", e);
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                try {
                    andPICInterface.unregisterCallback(ownTag);
                } catch (RemoteException e) {
                    Log.e("IPC","", e);
                }
                andPICInterface = null;
            }
        };
        Intent intent = new Intent(this.wrapper, AndIPCService.class);
        this.wrapper.bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 发送消息
     * @param targetTag 目标回调标签
     * @param message 消息体
     */
    public void sendMessage(String targetTag, String message){
        if(andPICInterface == null) return;
        try {
            andPICInterface.sendMessage(targetTag, ownTag, message, false);
        } catch (RemoteException e) {
            Log.e("IPC","", e);
        }
    }

    /**
     * 发送消息 并 监听回调信息
     * @param targetTag 目标消息回调标签
     * @param onceTag 一次一标签
     * @param message 消息
     * @param onceIpcCallbackListener 消息回调监听
     */
    public void sendMessage(String targetTag, String onceTag, String message, OnceIpcCallbackListener onceIpcCallbackListener){
        if(andPICInterface == null) return;
        try {
            andPICInterface.registerCallback(targetTag, new OnceIPCCallback(onceTag, andPICInterface, onceIpcCallbackListener));
            andPICInterface.sendMessage(targetTag, onceTag, message, true);
        } catch (RemoteException e) {
            Log.e("IPC","", e);
        }
    }

    /**
     * 添加回调监听
     * @param callbackTag 回调标签
     * @param callback 回调监听器
     */
    public void addCallback(String callbackTag,  AndIPCCallback.Stub callback){
        if(andPICInterface == null) return;
        try {
            andPICInterface.registerCallback(callbackTag,callback);
        } catch (RemoteException e) {
            Log.e("IPC","", e);
        }
    }

    /**
     * 移除回调监听
     * @param callbackTag 回调标签
     */
    public void removeCallback(String callbackTag){
        if(andPICInterface == null) return;
        try {
            andPICInterface.unregisterCallback(callbackTag);
        } catch (RemoteException e) {
            Log.e("IPC","", e);
        }
    }

    /**
     * 解除进程间通信服务器
     */
    public void unbindIPCService(){
        if(this.serviceConnection != null){
            this.wrapper.unbindService(serviceConnection);
            this.wrapper = null;
        }
    }

}
