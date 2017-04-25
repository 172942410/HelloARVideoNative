# HelloARVideoNative
gradle-wrapper.propertise 文件中必须是：

distributionUrl=https\://services.gradle.org/distributions/gradle-2.8-all.zip

和 
build.gradle文件中：

    dependencies {
        classpath 'com.android.tools.build:gradle-experimental:0.4.0'
    }
 目前配置其他版本的无法使用
 
ndk的配置应该为举例：

ndk.dir=/Users/lipengjun/Desktop/work/dev-tools/android/sdk/ndk-bundle
