package com.etaap.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.etaap.core.exception.HandleException;

/**
 * The Class Zip.
 */
public class Zip {

	/**
	 * Zip folder.
	 * 
	 * @param src
	 *            the src
	 * @param dst
	 *            the dst
	 * @throws Exception
	 *             the exception
	 */
	@HandleException(expected = { Exception.class })
	public static void zipFolder(String src, String dst) throws Exception {
		zipDir(src, dst);
	}

	/**
	 * Zip dir.
	 * 
	 * @param srcFolder
	 *            the src folder
	 * @param destZipFile
	 *            the dest zip file
	 * @throws Exception
	 *             the exception
	 */
	@HandleException(expected = { Exception.class })
	public static void zipDir(String srcFolder, String destZipFile) throws Exception {

		FileOutputStream fileWriter = new FileOutputStream(destZipFile);
		ZipOutputStream zip = new ZipOutputStream(fileWriter);

		addFolderToZip("", srcFolder, zip);
		zip.flush();
		zip.close();
	}

	/**
	 * Adds the file to zip.
	 * 
	 * @param path
	 *            the path
	 * @param srcFile
	 *            the src file
	 * @param zip
	 *            the zip
	 * @throws Exception
	 *             the exception
	 */
	@HandleException(expected = { Exception.class })
	private static void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {

		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			try (FileInputStream in = new FileInputStream(srcFile)) {
				zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}
			}
		}
	}

	/**
	 * Adds the folder to zip.
	 * 
	 * @param path
	 *            the path
	 * @param srcFolder
	 *            the src folder
	 * @param zip
	 *            the zip
	 * @throws Exception
	 *             the exception
	 */
	@HandleException(expected = { Exception.class })
	private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if ("".equals(path)) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}
}