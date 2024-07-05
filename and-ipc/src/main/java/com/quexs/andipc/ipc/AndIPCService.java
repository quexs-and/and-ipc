package com.quexs.andipc.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.quexs.andipc.AndIPCCallback;
import com.quexs.andipc.AndIPCInterface;

import java.util.concurrent.ConcurrentHashMap;

/**
* @date 2024/7/5 22:25
* @author Quexs
* @Description Android IPC Service
*/
public class AndIPCService extends Service {

    private final ConcurrentHashMap<String, AndIPCCallback> callbackMap = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub.asBinder();
    }

    private final AndIPCInterface.Stub stub = new AndIPCInterface.Stub() {
        @Override
        public void reginsterCallback(String sourceTag, AndIPCCallback callback) throws RemoteException {
            callbackMap.put(sourceTag, callback);
        }

        @Override
        public void sendMessage(String targetTag, String sourceTag, String message, boolean isNeedCallback) throws RemoteException {
            AndIPCCallback andPICCallback = callbackMap.get(targetTag);
            if(andPICCallback != null){
                andPICCallback.receiveMessages(sourceTag, message, isNeedCallback);
            }
        }

        @Override
        public void unreginsterCallback(String targetTag) throws RemoteException {
            callbackMap.remove(targetTag);
        }
    };

}
