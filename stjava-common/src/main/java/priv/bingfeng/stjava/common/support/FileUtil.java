package priv.bingfeng.stjava.common.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileUtil {
	
	private static final Log LOG = LogFactory.getLog(FileUtil.class);

	public static String readFile(String fileName) {
		return readFile(fileName, "utf-8");
	}
	public static String readFile(File file) {
		return readFile(file, "utf-8");
	}
	public static String readFile(String fileName, String encode) {
		return readFile(new File(fileName), encode);
	}
	public static String readFile(File file, String encode) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
	
	public static byte[] readBytes(String fileName) {
		return readBytes(new File(fileName));
	}
	public static byte[] readBytes(File file) {
		byte[] result = null;
		try (FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			result = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<String> readFileList(String fileName) {
		return readFileList(fileName, "utf-8");
	}
	public static List<String> readFileList(String fileName, String encode) {
		List<String> resultList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			File f = new File(fileName);
			if (!f.exists()) return resultList;
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f), encode));
			String line = null;
			while ((line = br.readLine()) != null) {
				resultList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return resultList;
	}
	
	public static boolean appendFile(String file, String data) {
		boolean flag = false;
		
		OutputStream output = null;
		try {
			output = new FileOutputStream(file, true);
			output.write(data.getBytes("UTF8"));
			output.write("\r\n".getBytes("UTF8"));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public static boolean writeFile(String file, String data) {
		return writeFile(new File(file), data);
	}
	public static boolean writeFile(File file, String data) {
		try {
			return writeFile(file, data.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean writeFile(File file, byte[] bytes) {
		boolean flag = false;
		
		OutputStream output = null;
		try {
			output = new FileOutputStream(file);
			output.write(bytes);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public static void deleteFile(String file) {
		File f = new File(file);
		try {
			if (f.exists()) {
				f.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFile(String source, String dest) {
		try (InputStream input = new FileInputStream(source); OutputStream output = new FileOutputStream(dest)) {
			copyFile(input, output);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static void copyFile(InputStream input, OutputStream output) throws IOException {
		byte[] buf = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buf)) != -1)
			output.write(buf, 0, bytesRead);
	}

	public static String zip(String str) {
		String result = str;
		if (str == null || str.length() == 0) return result;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes());
			gzip.close();
			result = out.toString("ISO-8859-1");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (Exception e2) {
				if (LOG.isErrorEnabled()) {
					LOG.error("", e2);
				}
			}
		}
		
		return result;
	}

	// 解压缩
	public static String unzip(String str) {
		String result = str;
		
		if (str == null || str.length() == 0) return str;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		try {
			in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
			GZIPInputStream gzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			result = out.toString();
			gzip.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
			} catch (Exception e2) {
				if (LOG.isErrorEnabled()) {
					LOG.error("", e2);
				}
			}
		}
		
		return result;
	}
	
}
