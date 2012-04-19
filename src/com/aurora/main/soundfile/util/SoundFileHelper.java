package com.aurora.main.soundfile.util;

import com.aurora.plugin.recording.soundfile.CheapSoundFile;

public class SoundFileHelper {
	private int mSampleRate;
    private int mSamplesPerFrame;
    private CheapSoundFile mSoundFile;
    public SoundFileHelper(CheapSoundFile mSoundFile){
    	this.mSoundFile = mSoundFile;
    	this.mSampleRate = mSoundFile.getSampleRate();
    	this.mSamplesPerFrame = mSoundFile.getSamplesPerFrame();
    }
	public  int secondsToFrames(double seconds) {
        return (int)(1.0 * seconds * mSampleRate / mSamplesPerFrame + 0.5);
    }
}
