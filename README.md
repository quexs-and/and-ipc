# and-ipc

**and-ipc** 一个灵活且强大的 Android 应用内多进程间通信解决方案。

[![](https://jitpack.io/v/quexs-and/and-ipc.svg)](https://jitpack.io/#quexs-and/and-ipc)

## 👏 特性 

- 支持 **跨进程发送信息**;
- 支持 **跨进程发送信息并接收消息回调**;

## 👨‍💻‍ 依赖方式

### Gradle(Project Setting)
```groovy
//Add it in your root build.gradle at the end of repositories
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

###  Gradle (Module app)
```groovy
//Add the dependency
dependencies {
	        implementation 'com.github.quexs-and:and-ipc:1.0.0'
	}
```

## 👨‍🔧‍ 使用方式

### 绑定IPC服务

#### 使用进程名作为IPC服务消息接收的标签（默认）
```java
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
//绑定服务
andIPC.bindIPCService();
```
#### 为Activity或者service单独绑定消息接收的标签
```java
 andIPC = new AndIPC(this, "ownTag（自定义消息接收的标签）", new AndIPCCallback.Stub() {
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
//绑定服务
andIPC.bindIPCService();
```

### 发送消息

#### 发送消息无回调
```java
andIPC.sendMessage("targetTag(目标进程消息接收标签)", "message(消息体)");
```
#### 发送消息带回调消息
```java
andIPC.sendMessage("targetTag(目标进程消息接收标签)", "onceTag（临时消息接收标签）", "message(消息体)", new OnceIpcCallbackListener() {
            @Override
            public void receiveMessages(String sourceTag, String message, boolean isNeedCallback) {
                //接收单次回调的消息
            }
        });
```
