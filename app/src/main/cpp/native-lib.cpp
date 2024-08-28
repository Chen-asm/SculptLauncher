#include <jni.h>
#include <string>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_chen_sculptlauncher_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_chen_sculptlauncher_core_bridge_HomeCppBridge_fetchSOAbi(JNIEnv *env, jobject thiz, jstring so_file_path) {
    // so_file_path 表示 minecraftpe.so 绝对路径的字符串
    // 要求返回架构字符串 应当是以下几种：arm64-v8a armeabi x86 x86_64
    // TODO: 待完成
    string arch = "arm64-v8a";
    return env->NewStringUTF(arch.c_str());
}