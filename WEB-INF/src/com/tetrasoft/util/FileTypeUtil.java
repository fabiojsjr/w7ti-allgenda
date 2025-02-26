package com.tetrasoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;

import com.technique.engine.util.ExceptionWarning;
/**
 *
 * @author  Administrator
 */
public class FileTypeUtil {

    /** Creates a new instance of FileTypeUtil */
    private FileTypeUtil() {
    }


    public static HashSet<String> imagesType = new HashSet<String>();
    static {
    	imagesType.add("JPG");
    	imagesType.add("JPEG");
    	imagesType.add("GIF");
    	imagesType.add("BMP");
    	imagesType.add("PNG");
    	imagesType.add("TIFF");
    }

    public static HashSet<String> videoTypes = new HashSet<String>();
    static {
    	videoTypes.add("AVI");
    	videoTypes.add("MPG");
    	videoTypes.add("MPEG");
    	videoTypes.add("ASF");
    	videoTypes.add("WMV");
    }

    public static HashSet<String> filesType = new HashSet<String>();
    static {
    	filesType.add("DOC");
    	filesType.add("XLS");
    	filesType.add("PDF");
    	filesType.add("PRJ");
    	filesType.add("TXT");
    	filesType.add("MP3");
    	filesType.add("MPG");
    	filesType.add("MPEG");
    	filesType.add("AVI");
    	filesType.add("ASX");
    	filesType.add("ASF");
    	filesType.add("WAV");
    	filesType.add("WMV");
    	filesType.add("WMA");
    }

    public static HashSet<String> audioTypes = new HashSet<String>();
    static {
		audioTypes.add("MP3");
		audioTypes.add("WAV");
		audioTypes.add("WMV");
		audioTypes.add("WMA");
    }

    public static int TYPE_NOT_VALID = 0;
    public static int TYPE_IMAGE = 1;
    public static int TYPE_FILE = 2;
    public static int TYPE_MULTIMEDIA = 3;
    public static int TYPE_VIDEO = 4;
	public static int TYPE_AUDIO = 5;

    public static boolean isValidFile(String msg) {
        if (checkFileName(msg)==TYPE_NOT_VALID) return false;
        return true;
    }

    public static int isAudioFile(String msg) {
    	if (msg==null) return TYPE_NOT_VALID;
    	if (msg.length()<3) return TYPE_NOT_VALID;
    	String fileEnd = msg.substring(msg.length()-3, msg.length()).toUpperCase();
    	if (audioTypes.contains(fileEnd)) return TYPE_AUDIO;
    	fileEnd = msg.substring(msg.length()-4, msg.length()).toUpperCase();
    	if (audioTypes.contains(fileEnd)) return TYPE_AUDIO;
    	return TYPE_NOT_VALID;
    }

    public static int isVideoFile(String msg) {
    	if (msg==null) return TYPE_NOT_VALID;
    	if (msg.length()<3) return TYPE_NOT_VALID;
    	String fileEnd = msg.substring(msg.length()-3, msg.length()).toUpperCase();
    	if (videoTypes.contains(fileEnd)) return TYPE_VIDEO;
    	fileEnd = msg.substring(msg.length()-4, msg.length()).toUpperCase();
    	if (videoTypes.contains(fileEnd)) return TYPE_VIDEO;
    	return TYPE_NOT_VALID;
    }

    public static int checkFileName(String msg) {
        if (msg==null) return TYPE_NOT_VALID;
        if (msg.length()<3) return TYPE_NOT_VALID;
        String fileEnd = msg.substring(msg.length()-3, msg.length()).toUpperCase();
        if (imagesType.contains(fileEnd)) return TYPE_IMAGE;
        if (filesType.contains(fileEnd)) return TYPE_FILE;
        if (msg.length()<4) return TYPE_NOT_VALID;
        fileEnd = msg.substring(msg.length()-4, msg.length()).toUpperCase();
        if (imagesType.contains(fileEnd)) return TYPE_IMAGE;
        if (filesType.contains(fileEnd)) return TYPE_FILE;
        return TYPE_NOT_VALID;
    }


	/**
	 * @see copyTo();
	 * @param sourceFile
	 * @param destFile
	 * @throws ExceptionWarning
	 */
	public static void copyFile(File sourceFile, File destFile) throws ExceptionWarning {
		try {
			new File(destFile.getParent()).mkdirs();
			if ( destFile.exists() ) destFile.delete();

			InputStream in = new FileInputStream(sourceFile);
			OutputStream out = new FileOutputStream(destFile);

			byte[] buf = new byte[1024];
			int len;
			while ( (len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			throw new ExceptionWarning("Exception in copyFile. Reason:" + e.getMessage(), e);
		}
	}
}