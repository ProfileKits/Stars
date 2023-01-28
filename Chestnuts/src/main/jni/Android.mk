LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := ChestnutData

LOCAL_SRC_FILES := com_predictor_library_jni_ChestnutData.cpp

include $(BUILD_SHARED_LIBRARY)