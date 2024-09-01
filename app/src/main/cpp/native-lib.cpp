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
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_MainActivity_nativeInitializeXboxLive(JNIEnv *env, jobject thiz, jlong v1, jlong v2) {
    // 已经载入了libminecraftpe.so通过System.load()，你看看能否直接调用我载入的，或者你把麻将的代码贴进来
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_MainActivity_nativeOnPickImageCanceled(JNIEnv *env, jobject thiz, jlong v1) {
    // TODO: implement nativeOnPickImageCanceled()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_MainActivity_nativeOnPickImageSuccess(JNIEnv *env, jobject thiz, jlong v1, jstring v2) {
    // TODO: implement nativeOnPickImageSuccess()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_store_NativeStoreListener_onStoreInitialized(JNIEnv *env, jobject thiz,
                                                                         jlong param_long,
                                                                         jboolean param_boolean) {
    // TODO: implement onStoreInitialized()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_store_NativeStoreListener_onQueryPurchasesSuccess(JNIEnv *env,
                                                                              jobject thiz,
                                                                              jlong param_long,
                                                                              jobjectArray param_array_of_purchase) {
    // TODO: implement onQueryPurchasesSuccess()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_store_NativeStoreListener_onQueryPurchasesFail(JNIEnv *env,
                                                                           jobject thiz,
                                                                           jlong param_long) {
    // TODO: implement onQueryPurchasesFail()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_store_NativeStoreListener_onQueryProductsSuccess(JNIEnv *env,
                                                                             jobject thiz,
                                                                             jlong param_long,
                                                                             jobjectArray param_array_of_product) {
    // TODO: implement onQueryProductsSuccess()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_store_NativeStoreListener_onQueryProductsFail(JNIEnv *env, jobject thiz,
                                                                          jlong param_long) {
    // TODO: implement onQueryProductsFail()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_store_NativeStoreListener_onPurchaseCanceled(JNIEnv *env, jobject thiz,
                                                                         jlong param_long,
                                                                         jstring param_string) {
    // TODO: implement onPurchaseCanceled()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_store_NativeStoreListener_onPurchaseFailed(JNIEnv *env, jobject thiz,
                                                                       jlong param_long,
                                                                       jstring param_string) {
    // TODO: implement onPurchaseFailed()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_store_NativeStoreListener_onPurchaseSuccessful(JNIEnv *env,
                                                                           jobject thiz,
                                                                           jlong param_long,
                                                                           jstring param_string) {
    // TODO: implement onPurchaseSuccessful()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_packagesource_NativePackageSourceListener_nativeOnDownloadProgress(
        JNIEnv *env, jobject thiz, jlong param_long1, jlong param_long2, jlong param_long3,
        jfloat param_float, jlong param_long4) {
    // TODO: implement nativeOnDownloadProgress()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_packagesource_NativePackageSourceListener_nativeOnDownloadStarted(
        JNIEnv *env, jobject thiz, jlong param_long) {
    // TODO: implement nativeOnDownloadStarted()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_packagesource_NativePackageSourceListener_nativeOnDownloadStateChanged(
        JNIEnv *env, jobject thiz, jlong param_long, jboolean param_boolean1,
        jboolean param_boolean2, jboolean param_boolean3, jboolean param_boolean4,
        jboolean param_boolean5, jint param_int1, jint param_int2) {
    // TODO: implement nativeOnDownloadStateChanged()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_mojang_minecraftpe_packagesource_NativePackageSourceListener_nativeOnMountStateChanged(
        JNIEnv *env, jobject thiz, jlong param_long, jstring param_string, jint param_int) {
    // TODO: implement nativeOnMountStateChanged()
}