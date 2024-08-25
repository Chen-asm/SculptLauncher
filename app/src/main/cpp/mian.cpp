#include <jni.h>
#include <string>
#include <jni.h>
#include <jni.h>
#include <filesystem>
#include <fstream>


extern "C" JNIEXPORT jstring JNICALL Java_com_chen_sculptlauncher_MainActivity_stringFromJNI(JNIEnv* env,jobject /* this */) 
{
    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
