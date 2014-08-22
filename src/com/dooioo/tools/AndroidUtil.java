package com.dooioo.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;

import com.dooioo.bean.FileObject;

public class AndroidUtil {

	@SuppressLint("SimpleDateFormat")
	public static String getCurrebtDate(long date)
	{
		String strDate = "";
		strDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
		return strDate;
	}

	public static ArrayList<FileObject> fileList(String path) 
	{

		ArrayList<FileObject> fileList = new ArrayList<FileObject>();

		File dir = new File(path);
		
		if (dir.exists())
		{

			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) 
			{
				File file = files[i];
				String fileName = file.getName();
				String fileSize = (file.length() / 1024) + "KB";
				String fileTime = AndroidUtil.getCurrebtDate(file.lastModified());
				String filePath = file.getAbsolutePath();

				FileObject fileObject = new FileObject(fileName, fileSize, fileTime, filePath);

				fileList.add(fileObject);
			}

			return fileList;

		} 
		else 
		{
			return fileList;
		}
	}

}
