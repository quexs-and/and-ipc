// AndIPCInterface.aidl
package com.quexs.andipc;

// Declare any non-default types here with import statements
import com.quexs.andipc.AndIPCCallback;
interface AndIPCInterface {
    oneway void reginsterCallback(String sourceTag, in AndIPCCallback callback);
    oneway void sendMessage(String targetTag, String sourceTag, String message, boolean isNeedCallback);
    oneway void unreginsterCallback(String targetTag);
}