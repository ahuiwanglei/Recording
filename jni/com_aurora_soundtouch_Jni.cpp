#include <string.h>
#include <jni.h>
#include "com_aurora_soundtouch_Jni.h"
 #include <android/log.h>
#include <stdexcept>
#include <stdio.h>
#include "RunParameters.h"
#include "WavFile.h"
#include "SoundTouch.h"
#include "BPMDetect.h"

using namespace soundtouch;
using namespace std;

// Processing chunk size
#define BUFF_SIZE           2048

#if WIN32
    #include <io.h>
    #include <fcntl.h>

    // Macro for Win32 standard input/output stream support: Sets a file stream into binary mode
    #define SET_STREAM_TO_BIN_MODE(f) (_setmode(fileno(f), _O_BINARY))
#else
    // Not needed for GNU environment...
    #define SET_STREAM_TO_BIN_MODE(f) {}
#endif

jstring Java_com_aurora_soundtouch_Jni_setWAV( JNIEnv* env, jobject object, jstring _a, jint _b)
{
	 __android_log_print(ANDROID_LOG_INFO, "sysout", "--------------in jni------------");
	    WavInFile *inFile;
	    WavOutFile *outFile;
	    SoundTouch soundTouch;
     try
	    {    jboolean* b;
    	     int bits, samplerate, channels;
    	       inFile = new WavInFile(env->GetStringUTFChars(_a, b));
    	      bits = (int)(*inFile).getNumBits();
    	      samplerate = (int)(*inFile).getSampleRate();
    	      channels = (int)(*inFile).getNumChannels();
              outFile = new WavOutFile("/mnt/sdcard/media/temp.wav", samplerate, bits, channels);





//
//	        if (params->detectBPM == TRUE)
//	        {
//	            // detect sound BPM (and adjust processing parameters
//	            //  accordingly if necessary)
//	            detectBPM(inFile, params);
//	        }
//
//	        // Setup the 'SoundTouch' object for processing the sound
//	        setup(&soundTouch, inFile, params);

                 int sampleRate;
                 int tempchannels;

                 sampleRate = (int)(*inFile).getSampleRate();
                 tempchannels = (int)(*inFile).getNumChannels();
                 soundTouch.setSampleRate(sampleRate);
                 soundTouch.setChannels(tempchannels);

                 soundTouch.setTempoChange(0);
                 soundTouch.setPitchSemiTones(_b);
                 soundTouch.setRateChange(0);

                 soundTouch.setSetting(SETTING_USE_QUICKSEEK, 0);
                 soundTouch.setSetting(SETTING_USE_AA_FILTER, !(0));

                 if (true)
                 {
                     // use settings for speech processing
                	 soundTouch.setSetting(SETTING_SEQUENCE_MS, 40);
                	 soundTouch.setSetting(SETTING_SEEKWINDOW_MS, 15);
                	 soundTouch.setSetting(SETTING_OVERLAP_MS, 8);
                     fprintf(stderr, "Tune processing parameters for speech processing.\n");
                 }

                 // print processing informatio

                 fflush(stderr);

//
//	        // Process the sound
//	        process(&soundTouch, inFile, outFile);
                   int nSamples;
                    int nChannels;
                    int buffSizeSamples;
                    SAMPLETYPE sampleBuffer[BUFF_SIZE];

                    if ((inFile == NULL) || (outFile == NULL)) return env->NewStringUTF("ÎÄ¼þÎª¿Õ");  // nothing to do.

                    nChannels = (int)inFile->getNumChannels();
                    assert(nChannels > 0);
                    buffSizeSamples = BUFF_SIZE / nChannels;

                    // Process samples read from the input file
                    while (inFile->eof() == 0)
                    {
                        int num;

                        // Read a chunk of samples from the input file
                        num = inFile->read(sampleBuffer, BUFF_SIZE);
                        nSamples = num / (int)inFile->getNumChannels();

                        // Feed the samples into SoundTouch processor
                        soundTouch.putSamples(sampleBuffer, nSamples);

                        // Read ready samples from SoundTouch processor & write them output file.
                        // NOTES:
                        // - 'receiveSamples' doesn't necessarily return any samples at all
                        //   during some rounds!
                        // - On the other hand, during some round 'receiveSamples' may have more
                        //   ready samples than would fit into 'sampleBuffer', and for this reason
                        //   the 'receiveSamples' call is iterated for as many times as it
                        //   outputs samples.
                        do
                        {
                            nSamples = soundTouch.receiveSamples(sampleBuffer, buffSizeSamples);
                            outFile->write(sampleBuffer, nSamples * nChannels);
                        } while (nSamples != 0);
                    }

                    // Now the input file is processed, yet 'flush' few last samples that are
                    // hiding in the SoundTouch's internal processing pipeline.
                   // soundTouch.flush();
                    do
                    {
                        nSamples = soundTouch.receiveSamples(sampleBuffer, buffSizeSamples);
                        outFile->write(sampleBuffer, nSamples * nChannels);
                    } while (nSamples != 0);



//	        // Close WAV file handles & dispose of the objects
	        delete inFile;
	        delete outFile;
	  //      delete params;
//
	        fprintf(stderr, "Done!\n");
	    }
	    catch (const runtime_error &e)
	    {

	        fprintf(stderr, "%s\n", e.what());
	        return env->NewStringUTF("error");
	    }
//
//	    return 0;
	return env->NewStringUTF("success");
}
