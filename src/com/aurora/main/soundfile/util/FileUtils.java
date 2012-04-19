package com.aurora.main.soundfile.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import android.os.Environment;
import android.util.Log;

public class FileUtils {

	private static String SDPATH;

	public String getSDPATH() {
		return SDPATH;
	}
/*
 * 返回sd卡根目录
 */
	public FileUtils() {SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	/*
	 * 在指定目录下新建�?��文件
	 * dir ：目�?
	 * fileName ：文件名
	 */
	public File createFileInSDCard(String fileName, String dir)
			throws IOException {
		Log.i("sysout", SDPATH + dir + File.separator + fileName);
		File file = new File(SDPATH + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/*
	 *  创建目录
	 */
	public File createSDDir(String dirName) {
		File file = new File(SDPATH + dirName);
		file.mkdir();
		return file;
	}

	/*
	 * 判断是否有存在指定文�?
	 */
	public static boolean isFileExist(String fileName, String path) {
		File file = new File(SDPATH + path + File.separator + fileName);
		return file.exists();
	}
	
	public static boolean isFileExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	/*
	 * 根据�?��二进制流写入�?��文件�?
	 * path：路�?
	 * fileName：文件名
	 * input 输入�?
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			int temp;
			byte buffer[] = new byte[4 * 1024];
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	
}
