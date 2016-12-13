package com.smarttop.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Message;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件操作类
 *
 * @Description: 文件操作类
 *
 * @FileName: FileUtil.java
 *
 * @Package com.device.photo
 *
 * @Author Hanyonglu
 *
 * @Date 2012-5-10 下午01:37:49
 *
 * @Version V1.0
 */
public class FileUtil {
	public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String PATH = "versionupdate";
	public FileUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * InputStream to byte
	 *
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public byte[] readInputStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}

		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();

		return data;
	}

	/**
	 * Byte to bitmap
	 *
	 * @param bytes
	 * @param opts
	 * @return
	 */
	public Bitmap getBitmapFromBytes(byte[] bytes, BitmapFactory.Options opts) {
		if (bytes != null) {
			if (opts != null) {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
			} else {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			}
		}

		return null;
	}

	public static boolean isDebug = LogUtil.isDebug;

	public static void writeToSdCard(final String message) {
		if (isDebug) {

			new Thread() {
				@Override
				public void run() {
					File file = new File(Environment.getExternalStorageDirectory(), "cpblog.txt");
					if (file.exists()) {
						file.delete();
					}
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(file, true);
						fos.write(message.getBytes());
						fos.flush();
						fos.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();

		}

	}

	public static void writeHpCacheDataToSdCard(final String message) {

		new Thread() {
			@Override
			public void run() {
				File file = new File(Environment.getExternalStorageDirectory(), "hp_cache_data.txt");
				if (file.exists()) {
					file.delete();
				}
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(file, true);
					fos.write(message.getBytes());
					fos.flush();
					fos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	public static void readHpCacheDataFromSdCard(final TransactionHandler handler) {
		new Thread() {
			@Override
			public void run() {

				try {

					File file = new File(Environment.getExternalStorageDirectory(), "hp_cache_data.txt");
					if (!file.exists() || file.isDirectory()) {
						Message message = Message.obtain();
						message.what = -1;
						message.arg1 = 500;
						message.arg2 = 1;
						message.obj = "Error";
						handler.sendMessage(message);
					} else {
						BufferedReader br = new BufferedReader(new FileReader(file));
						String temp = null;
						StringBuffer sb = new StringBuffer();
						temp = br.readLine();
						while (temp != null) {
							sb.append(temp + " ");
							temp = br.readLine();
						}

						Message message = Message.obtain();
						message.what = 1;
						message.arg1 = 200;
						message.arg2 = 0;
						message.obj = sb.toString();
						handler.sendMessage(message);
					}

				} catch (IOException e) {
					e.printStackTrace();
					Message message = Message.obtain();
					message.what = -1;
					message.arg1 = 500;
					message.arg2 = 1;
					message.obj = "Error";
					handler.sendMessage(message);

				}
			}
		}.start();

	}

	/**
	 * 判断本地数据库文件是否存在
	 *
	 * @return boolean
	 */
	public static boolean LocationContactExist(Context context) {
		File file = new File(context.getFilesDir(), "local_contact_data.txt");
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 将本地联系人保存为本地文件,每次调用都重新写入
	 *
	 * @param message
	 */
	public static synchronized void writeContactToSdCard(final Context context, final String message) {

		new Thread() {
			@Override
			public void run() {
				File file = new File(context.getFilesDir(), "local_contact_data.txt");
				if (file.exists()) {
					file.delete();
				}
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(file, true);
					fos.write(message.getBytes());
					fos.flush();
					fos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	/**
	 * 获取保存在本地的联系人json字符串
	 *
	 * @param context
	 * @return
	 */
	public static String readLocationContactFile(Context context) {
		String result = "";

		try {
			FileInputStream f = new FileInputStream(context.getFilesDir() + "/local_contact_data.txt");
			BufferedReader bis = new BufferedReader(new InputStreamReader(f));
			String line = "";
			while ((line = bis.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除单个文件
	 *
	 * @param filePath
	 *            被删除文件的文件名
	 * @return 文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 删除文件夹以及目录下的文件
	 *
	 * @param filePath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String filePath) {
		boolean flag = false;
		// 如果filePath不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}
		File dirFile = new File(filePath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();
		// 遍历删除文件夹下的所有文件(包括子目录)
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				// 删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				// 删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前空目录
		return dirFile.delete();
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 *
	 * @param filePath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile()) {
				// 为文件时调用删除文件方法
				return deleteFile(filePath);
			} else {
				// 为目录时调用删除目录方法
				return deleteDirectory(filePath);
			}
		}
	}
}