LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)
 
LOCAL_C_INCLUDES := $(JNI_H_INCLUDE)
LOCAL_SHARED_LIBRARIES := libutils
LOCAL_PRELINK_MODULE := false
LOCAL_LDLIBS := -llog
 
LOCAL_MODULE    := HelloNDK
LOCAL_SRC_FILES := \
AAFilter.cpp \
BPMDetect.cpp \
cpu_detect_x86_gcc.cpp \
FIFOSampleBuffer.cpp \
FIRFilter.cpp \
mmx_optimized.cpp \
PeakFinder.cpp \
RateTransposer.cpp \
SoundTouch.cpp \
RunParameters.cpp \
sse_optimized.cpp \
TDStretch.cpp \
WavFile.cpp \
main.cpp \
com_aurora_soundtouch_Jni.cpp \ 

include $(BUILD_SHARED_LIBRARY)