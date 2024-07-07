# and-ip

**and-ip** 一个灵活且强大的Android 应用内多进程间通信解决方案。

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

###  Gradle (Module app)
```groovy
//Add the dependency
dependencies {
	        implementation 'com.github.quexs-and:and-ipc:1.0.0'
	}
```
