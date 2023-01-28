#include <jni.h>
#ifndef _Included_com_flycode_encryption_jni_ChestnutData
#define _Included_com_flycode_encryption_jni_ChestnutData
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_com_flycode_encryption_jni_ChestnutData_getKey(
        JNIEnv *,jobject,jobject);

JNIEXPORT jboolean JNICALL Java_com_flycode_encryption_jni_ChestnutData_getPermission(
        JNIEnv *,jobject);

JNIEXPORT jboolean JNICALL Java_com_flycode_encryption_jni_ChestnutData_getToken(
        JNIEnv *,jobject,jobject,jstring);

JNIEXPORT jstring JNICALL Java_com_flycode_encryption_jni_ChestnutData_getStartCode(
          JNIEnv *,jobject);

#ifdef __cplusplus
}
#endif
#endif