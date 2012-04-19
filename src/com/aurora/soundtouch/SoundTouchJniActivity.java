package com.aurora.soundtouch;

import java.io.File;

import com.aurora.main.soundfile.util.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SoundTouchJniActivity extends Activity implements OnClickListener {

	static {
		System.loadLibrary("HelloNDK");
	}

	private TextView changeinfo;
	private Button changesound;
	private Button changeplay;
	private EditText changedata;
	private Button changerecording;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViews();
        path = Environment.getExternalStorageDirectory() +"/media/recording.wav";
		

	}

	private void findViews() {
		changeinfo = (TextView) findViewById(R.id.changeinfo);
		changesound = (Button)findViewById(R.id.changesound);
		changedata = (EditText)findViewById(R.id.changedata);
		changeplay = (Button)findViewById(R.id.changeplay);
		changerecording = (Button)findViewById(R.id.changerecording);
		
		changerecording.setOnClickListener(this);
		changesound.setOnClickListener(this);
		changeplay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changesound:
			 File file = new File(Environment.getExternalStorageDirectory()+ "/media/");
				if(!file.exists()){
					Toast.makeText(SoundTouchJniActivity.this, "请先说一段话吧", Toast.LENGTH_SHORT).show();
					return;
				}
			Jni jni = new Jni();
			try{
			 changeinfo.setText(jni.setWAV(path, Integer.parseInt(changedata.getText().toString())));
			}catch(NumberFormatException  e){
				Toast.makeText(SoundTouchJniActivity.this, "请输入有效数字", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.changeplay:
			play();
			break;
		case R.id.changerecording:
			recording();
		default:
			break;
		}

	}

	private void recording() {
		 Intent intent = new Intent(SoundTouchJniActivity.this, RecordingMain.class);
		 SoundTouchJniActivity.this.startActivity(intent);
	}

	private void play() {
		MediaPlayer mediaPlayer;
		if (FileUtils.isFileExist(Environment.getExternalStorageDirectory()	+ "/media/temp.wav")) {
			mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri.parse(Environment.getExternalStorageDirectory()	+ "/media/temp.wav"));
			mediaPlayer.setLooping(false);
			mediaPlayer.start();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					Toast.makeText(getApplicationContext(), "播放完毕",
							Toast.LENGTH_SHORT).show();
				}
			});
		}else{
			Toast.makeText(SoundTouchJniActivity.this, "请先说一段话吧", Toast.LENGTH_SHORT).show();
		}
		
	}
}
