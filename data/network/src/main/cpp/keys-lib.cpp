#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_hrhera_ali_network_KeysProvider_getApiKey(JNIEnv* env, jobject /* this
 * */) {
    std::string apiKey = "f5cb0b965ea1564c50c6f1b74534d823";
    return env->NewStringUTF(apiKey.c_str());
}