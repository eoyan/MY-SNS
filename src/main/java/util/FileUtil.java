package util;

import java.io.*;


public class FileUtil {
	public static void saveImage(String root, String fname, byte[] data) throws IOException {
		File f = new File(root);
		if (!f.exists()) f.mkdir();
		
		f = new File(root + "/" + fname);
		FileOutputStream out = new FileOutputStream(f);
		System.out.println("최종 저장 경로: " + root + "/" + fname);
		out.write(data);
		out.close();
	}
}