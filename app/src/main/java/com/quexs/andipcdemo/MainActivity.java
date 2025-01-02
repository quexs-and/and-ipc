package com.quexs.andipcdemo;

import android.os.Bundle;
import android.os.RemoteException;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.quexs.andipc.AndIPCCallback;
import com.quexs.andipc.ipc.AndIPC;
import com.quexs.andipc.ipc.OnceIpcCallbackListener;

public class MainActivity extends AppCompatActivity {
    private AndIPC andIPC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerIPCService(){
        //默认当前进程名为消息接收标签
        andIPC = new AndIPC(this, new AndIPCCallback.Stub() {
            @Override
            public void receiveMessages(String sourceKey, String message, boolean isNeedCallback) throws RemoteException {
                //接收消息
                //TODO 此处处理消息
                if(isNeedCallback){
                    //需要回调
                    andIPC.sendMessage(sourceKey, "message(回调的消息体)");
                }
            }
        });
//        自定义消息接收标签
//        andIPC = new AndIPC(this, "ownTag（自定义消息接收的标签）", new AndIPCCallback.Stub() {
//            @Override
//            public void receiveMessages(String sourceKey, String message, boolean isNeedCallback) throws RemoteException {
//                //接收消息
//                //TODO 此处处理消息
//                if(isNeedCallback){
//                    //需要回调
//                    andIPC.sendMessage(sourceKey, "message(回调的消息体)");
//                }
//            }
//        });
        //绑定服务
        andIPC.bindIPCService();

    }

    private void sendMessage(){
        //发送消息
        andIPC.sendMessage("targetTag(目标进程消息接收标签)", "message(消息体)");
    }

    private void sendMessageAndCallback(){
        andIPC.sendMessage("targetTag(目标进程消息接收标签)", "onceTag（临时消息接收标签）", "message(消息体)", new OnceIpcCallbackListener() {
            @Override
            public void receiveMessages(String sourceTag, String message, boolean isNeedCallback) {
                //接收单次回调的消息
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(andIPC != null){
            andIPC.unbindIPCService();
        }
    }
}