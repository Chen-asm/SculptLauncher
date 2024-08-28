#include <jni.h>
#include <string>
#include "bridge/home/home_bridge.h"

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
    auto temp = env->GetStringUTFChars(so_file_path, nullptr);
    auto arch = archInfo(temp);
    return env->NewStringUTF(arch.c_str());
}