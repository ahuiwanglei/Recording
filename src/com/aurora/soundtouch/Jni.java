package com.aurora.soundtouch;

public class Jni {
	public native int getCInt();
	 
	public native String getCString();
	
	public native String setWAV(String filePath, int pitch);
}
