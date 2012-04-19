package com.aurora.soundtouch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.aurora.main.soundfile.util.FileUtils;
import com.aurora.plugin.recording.soundfile.CheapSoundFile;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecordingMain extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private Button recordingBtn;
	private Button recordFinishBtn;
	private Button playRecordBtn;
	ExtAudioRecorder extAudioRecorder;
	private String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recoding);
		findAllUI();
		path = Environment.getExternalStorageDirectory()+ "/media/recording.wav";

	}

	public void findAllUI() {
		recordingBtn = (Button) findViewById(R.id.recordingBtn);
		recordFinishBtn = (Button) findViewById(R.id.recordFinishBtn);
		playRecordBtn = (Button) findViewById(R.id.playBtn);

		/*
		 * listener
		 */
		recordingBtn.setOnClickListener(this);
		recordFinishBtn.setOnClickListener(this);
		playRecordBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.recordingBtn:
			recordingStart();
			break;
		case R.id.recordFinishBtn:
			stop();
			break;
		case R.id.playBtn:
			stop();
			play();
		default:
			break;
		}

	}

	private void stop() {
		if (extAudioRecorder != null) {
			extAudioRecorder.stop();
			extAudioRecorder.release();
			System.out.println("----------stop-----------");
		}
	}

	private void recordingStart() {
	    File file = new File(Environment.getExternalStorageDirectory()+ "/media/");
		if(!file.exists()){
			file.mkdirs();
		}
	    extAudioRecorder = ExtAudioRecorder.getInstanse(false); // Compressed
		extAudioRecorder.setOutputFile(path);
		extAudioRecorder.prepare();
		extAudioRecorder.start();

	}

	private void play() {
		MediaPlayer mediaPlayer;
		if (FileUtils.isFileExist(path)) {

			mediaPlayer = MediaPlayer.create(
					getApplicationContext(),
					Uri.parse(path));
			mediaPlayer.setLooping(false);
			mediaPlayer.start();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					Toast.makeText(getApplicationContext(), "≤•∑≈ÕÍ±œ",
							Toast.LENGTH_SHORT).show();
				}
			});
		}else{
			Toast.makeText(RecordingMain.this, "«Îœ»¬º“Ù", Toast.LENGTH_SHORT).show();
		}
	}

}