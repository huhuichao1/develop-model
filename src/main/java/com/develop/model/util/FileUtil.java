package com.develop.model.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.develop.model.util.IOUtil;

/**
 * 文件工具类
 * 
 * @author huhuichao
 * 
 */
public class FileUtil {

	public static boolean mkdirs(File directory) {
		if (directory == null) {
			return false;
		}
		if (directory.isFile()) {
			if (directory.delete()) {
			} else {
				return false;
			}
		}
		if (!directory.exists()) {
			if (directory.mkdirs()) {
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean writeLines(File file, boolean append, String... lines) {
		if (file == null || lines == null) {
			return false;
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, append));
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i];
				if (line == null) {
					writer.write("");
				} else {
					writer.write(line);
				}
				writer.newLine();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static String[] readLines(File file) {
		if (file == null || !file.isFile()) {
			return null;
		}
		BufferedReader reader = null;
		try {
			List<String> list = new ArrayList<String>();
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			return list.toArray(new String[list.size()]);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String readString(File file) {
		return readString(file, null);
	}

	/**
	 * 
	 * @param file
	 *            文本文件
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String readString(File file, String charset) {
		if (file == null || !file.isFile()) {
			return null;
		}
		byte[] b = read(file);
		if (b == null) {
			return null;
		}
		if (StringUtil.isEmpty(charset)) {
			return new String(b);
		} else {
			try {
				return new String(b, charset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] read(File file) {
		// TODO Auto-generated method stub
		if (file == null || !file.isFile()) {
			return null;
		}
		try {
			return IOUtil.read(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 * @param input
	 * @return
	 */
	public static File write(File file, byte[] input) {
		return write(file, false, input);
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 * @param input
	 * @return
	 */
	public static File write(File file, boolean append, byte[] input) {
		if (file == null || input == null) {
			return null;
		}
		try {
			if (IOUtil.write(new FileOutputStream(file, append), input)) {
				return file;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 移动文件（不支持目录）
	 * 
	 * @param source
	 * @param target
	 * @param replace
	 * @return
	 */
	public static boolean move(File source, File target, boolean replace) {
		if (source == null || !source.isFile() || target == null) {
			return false;
		}
		if (copy(source, target, replace)) {
			if (source.delete()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 复制文件(不支持目录)
	 * 
	 * @param source
	 * @param target
	 * @param replace
	 * @return
	 */
	public static boolean copy(File source, File target, boolean replace) {
		if (source == null || !source.isFile() || target == null) {
			return false;
		}
		File file = null;
		if (target.exists() && target.isDirectory()) {// 目标文件存在并且为目录
			file = new File(target, source.getName());
		} else {
			file = target;
		}
		if (file.exists() && !replace) {
			return false;
		}
		try {
			IOUtil.readAndWrite(new FileInputStream(source), new FileOutputStream(file));
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		// String[] lines = { "first", "", "", null, "end" };
		File file = new File("abc.txt");
		// writeLines(file, lines, true);
		String[] a = readLines(file);
		System.out.println(a);
	}
}
