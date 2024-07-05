// AndIPCCallback.aidl
package com.quexs.andipc;

// Declare any non-default types here with import statements

interface AndIPCCallback {
    oneway void receiveMessages(String sourceTag, String message, boolean isNeedCallback);
}