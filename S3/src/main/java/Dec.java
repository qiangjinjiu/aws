import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Dec {
	/**
	 * 
	 * @param fileUrl 密文文件路径
	 * @param tempUrl 明文文件路径
	 * @param keyLength 密码长度，单位：字节
	 * @return 明文文件路径
	 * @throws Exception
	 */
	public static String decrypt(String fileUrl, String tempUrl, int keyLength)	throws Exception {
		File file = new File(fileUrl); // 读取文件
		if (!file.exists()) {// 判断文件是否存在
			return null;
		}
		InputStream is = new FileInputStream(fileUrl);
		OutputStream out = new FileOutputStream(tempUrl);

		byte[] buffer = new byte[1024];
		byte[] buffer2 = new byte[1024];
		byte bMax = (byte) 255;
		long size = file.length() - keyLength;
		int mod = (int) (size % 1024);// 求余
		int div = (int) (size >> 10);// 右移运算符, kb
		int count = mod == 0 ? div : (div + 1);// 双目运算，mod等于0时，赋值div,否则赋值(div + 1)
		int k = 1, r;
		while ((k <= count && (r = is.read(buffer)) > 0)) {//读取文件
			if (mod != 0 && k == count) { //是否读取到文件最后
				r = mod;
			}
			for (int i = 0; i < r; i++) {
				byte b = buffer[i];
				buffer2[i] = b == 0 ? bMax : --b;//解密，如果是0x00，则解密为0xff,否则自减 ,解密算法就是每个字节都-1
			}
			out.write(buffer2, 0, r);//写入明文到明文文件
			k++;
		}
		out.close();//关闭文件流
		is.close();
		return tempUrl;
	}
}
