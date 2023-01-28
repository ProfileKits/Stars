#include<jni.h>
#include<string>
#include"valid.cpp"
using namespace
std;
#ifndef _Included_com_flycode_encryption_jni_ChestnutData
#define _Included_com_flycode_encryption_jni_ChestnutData
#ifdef __cplusplus
extern "C"
{
#endif
        jboolean canUse = false;

        jstring char2Jstring (JNIEnv * env, const char*pat )
        {
            //定义java String类 strClass
            jclass strClass = (env) -> FindClass("java/lang/String");
            //获取java String类方法String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
            jmethodID ctorID = (env) -> GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
            //建立byte数组
            jbyteArray bytes = (env) -> NewByteArray((jsize) strlen(pat));
            //将char* 转换为byte数组
            (env) -> SetByteArrayRegion(bytes, 0, (jsize) strlen(pat), (jbyte *)pat);
            //设置String, 保存语言类型,用于byte数组转换至String时的参数
            jstring encoding = (env) -> NewStringUTF("GB2312");
            //将byte数组转换为java String,并输出
            return (jstring) (env) -> NewObject(strClass, ctorID, bytes, encoding);
        }


        char*Jstring2CStr(JNIEnv * env, jstring jstr)
        {
            char*rtn = NULL;
            jclass clsstring = env -> FindClass("java/lang/String");
            jstring strencode = env -> NewStringUTF("GB2312");
            jmethodID mid = env -> GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
            jbyteArray barr = (jbyteArray) env -> CallObjectMethod(jstr, mid, strencode);
            jsize alen = env -> GetArrayLength(barr);
            jbyte * ba = env -> GetByteArrayElements(barr, JNI_FALSE);
            if (alen > 0) {
                rtn = ( char*)malloc(alen + 1);//new char[alen+1];
                memcpy(rtn, ba, alen);
                rtn[alen] = 0;
            }
            env -> ReleaseByteArrayElements(barr, ba, 0);
            return rtn;
        }

        char*jstringToChar(JNIEnv * env, jstring jstr) {
        char*rtn = NULL;
        jclass clsstring = env -> FindClass("java/lang/String");
        jstring strencode = env -> NewStringUTF("GB2312");
        jmethodID mid = env -> GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
        jbyteArray barr = (jbyteArray) env -> CallObjectMethod(jstr, mid, strencode);
        jsize alen = env -> GetArrayLength(barr);
        jbyte * ba = env -> GetByteArrayElements(barr, JNI_FALSE);
        if (alen > 0) {
            rtn = ( char*)malloc(alen + 1);
            memcpy(rtn, ba, alen);
            rtn[alen] = 0;
        }
        env -> ReleaseByteArrayElements(barr, ba, 0);
        return rtn;
    }

    JNIEXPORT jstring JNICALL Java_com_flycode_encryption_jni_ChestnutData_getKey (
            JNIEnv * env, jobject, jobject contextObject){
        char *sha1 = getSha1(env, contextObject);
        jstring str = char2Jstring(env, sha1);
        return str;
    }

    JNIEXPORT jboolean JNICALL Java_com_flycode_encryption_jni_ChestnutData_getPermission (
            JNIEnv * env, jobject){
        if (canUse) {
            return true;
        } else {
            return false;
        }
    }


    JNIEXPORT jstring JNICALL Java_com_flycode_encryption_jni_ChestnutData_getStartCode (
                JNIEnv * env, jobject){
         jstring str = char2Jstring(env, app_key);
         return str;
    }


    JNIEXPORT jboolean JNICALL Java_com_flycode_encryption_jni_ChestnutData_getToken (
            JNIEnv * env,
            jobject,
            jobject contextObject,
            jstring key){
        char *sha1 = getSha1(env, contextObject);
        char *key1 = jstringToChar(env, key);
        jboolean result = checkValidity(env, sha1);
        jboolean keyR = checkKey(env, key1);
        if (result && keyR) {
            canUse = true;
            return true;
        } else {
            canUse = false;
            return false;
        }
        /**
         if(result){
         return env->NewStringUTF("获取Token成功");
         }else{
         return env->NewStringUTF("获取失败，请检查valid.cpp文件配置的sha1值");
         }
         **/
    }

#ifdef __cplusplus
}
#endif
#endif