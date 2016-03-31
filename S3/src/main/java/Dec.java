import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Dec {
	/**
	 * 
	 * @param fileUrl �����ļ�·��
	 * @param tempUrl �����ļ�·��
	 * @param keyLength ���볤�ȣ���λ���ֽ�
	 * @return �����ļ�·��
	 * @throws Exception
	 */
	public static String decrypt(String fileUrl, String tempUrl, int keyLength)	throws Exception {
		File file = new File(fileUrl); // ��ȡ�ļ�
		if (!file.exists()) {// �ж��ļ��Ƿ����
			return null;
		}
		InputStream is = new FileInputStream(fileUrl);
		OutputStream out = new FileOutputStream(tempUrl);

		byte[] buffer = new byte[1024];
		byte[] buffer2 = new byte[1024];
		byte bMax = (byte) 255;
		long size = file.length() - keyLength;
		int mod = (int) (size % 1024);// ����
		int div = (int) (size >> 10);// ���������, kb
		int count = mod == 0 ? div : (div + 1);// ˫Ŀ���㣬mod����0ʱ����ֵdiv,����ֵ(div + 1)
		int k = 1, r;
		while ((k <= count && (r = is.read(buffer)) > 0)) {//��ȡ�ļ�
			if (mod != 0 && k == count) { //�Ƿ��ȡ���ļ����
				r = mod;
			}
			for (int i = 0; i < r; i++) {
				byte b = buffer[i];
				buffer2[i] = b == 0 ? bMax : --b;//���ܣ������0x00�������Ϊ0xff,�����Լ� ,�����㷨����ÿ���ֽڶ�-1
			}
			out.write(buffer2, 0, r);//д�����ĵ������ļ�
			k++;
		}
		out.close();//�ر��ļ���
		is.close();
		return tempUrl;
	}
}
