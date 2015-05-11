package com.ron.utils.readVideo;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReadVideoMessages {

	/**
	 * 得到视频的大小
	 * 
	 * @param f
	 *            文件
	 * @return 视频的大小
	 */
	public static String getFileSize(File f) {
		// 保留两位小数
		DecimalFormat df = new DecimalFormat(".##");
		// 得到视频的长度
		Long long1 = f.length();
		String size = "";
		long G = 1024 * 1024 * 1024;
		long M = 1024 * 1024;
		long K = 1024;
		// 视频大小超过G、超过M不超过G、超过K不超过M
		if (long1 / G >= 1) {
			size = df.format((double) long1 / G) + "G";
		} else if (long1 / M >= 1) {
			size = df.format((double) long1 / M) + "M";
		} else if (long1 / K >= 1) {
			size = df.format((double) long1 / K) + "K";
		} else {
			size = long1 + "B";
		}
		// System.out.println(time);
		return size;

	}

	/**
	 * 得到视频的长度
	 * 
	 * @param f
	 *            文件
	 * @return 视频的长度
	 */
	public static String getVideoTime(File f) {
		String time = "";
		//新建编码器对象
		Encoder encoder = new Encoder();
		try {
			//得到多媒体视频的信息
			MultimediaInfo m = encoder.getInfo(f);
			//得到毫秒级别的多媒体是视频长度
			long ls = m.getDuration();
			//转换为分秒
			time = ls / 60000 + "分" + (ls - (ls / 60000 * 60000)) / 1000 + "秒";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return time;

	}

	// 显示目录的方法
	/**
	 * 得到视频所有的信息
	 * 
	 * @param file
	 *            文件夹 or 文件
	 * @return 视频的信息
	 */
	public static List<VideoName> getAllMessage(File file) {
		List<VideoName> videoNames = new ArrayList<VideoName>();
		// System.out.println( file.getAbsolutePath());
		String time = "";
		String size = "";
		// 判断传入对象是否为一个文件夹对象
		if (!file.isDirectory()) {
			System.out.println("你输入的不是一个文件夹，请检查路径是否有误！！");
		} else {
			File[] f = file.listFiles();
			for (int i = 0; i < f.length; i++) {
				// 判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
				if (f[i].isDirectory()) {
					System.out.println(f[i].getName() + "\tttdir");
					// getAllMessage(f[i]);
				} else {
					time = getVideoTime(f[i]);
					if (time.equals("")) {
						time = "未知";
					}
					size = getFileSize(f[i]);
					VideoName videoName = new VideoName();
					int j = f[i].getName().indexOf("-", 1);
					videoName.setName(f[i].getName().substring(0, j));
					videoName.setSize(size);
					videoName.setTime(time);
					videoName.setBrief(f[i].getName().substring(j + 1,
							f[i].getName().length()));
					videoName.setUrl("F:/Resource/video/硬件/AltiumDesigner/"
							+ f[i].getName());
					videoNames.add(videoName);
					System.out.println(time
							+ "---"
							+ size
							+ "---"
							+ f[i].getName().substring(0, j)
							+ "---"
							+ f[i].getName().substring(j + 1,
									f[i].getName().length()) + "---"
							+ "F:/Resource/video/硬件/AltiumDesigner/"
							+ f[i].getName());
				}
			}
		}
		return videoNames;

	}
	
	public static void main(String[] args) {
//		File f = new File("F:\\Resource\\video\\软件\\LabView");
		File f = new File("G:\\果果视频");
		List<VideoName> videoMessages = getAllMessage(f);
		System.out.println(videoMessages.size());
	}

}
