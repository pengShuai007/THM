package APP.Comm.Util;

/**
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
 * 任务:KYEEAPP-1066
 * 描述：RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * 作者：houhy
 * 时间:2014年11月28日 21:56:51
 * 
 */
public class RSAUtil {
	
	//private static String RSAKeyStore = "C:/RSAKey.txt";
	private static String RSAKeyStore = "RSAKey.txt";

	private static KeyPair getKeyPair() throws Exception {
		String spath = RSAUtil.class.getClassLoader().getResource("").getPath();
		//String spath = this.getClass().getClassLoader().getResource("/").getPath();
		if (!spath.endsWith("/"))
			spath += "/";
		spath +=RSAKeyStore;		
		//解决文件路径中存在空格问题
		spath = URLDecoder.decode(spath, "UTF-8");
		FileInputStream fis = new FileInputStream(spath);
		ObjectInputStream oos = new ObjectInputStream(fis);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		fis.close();
		return kp;
	}

	
	/**
	 * <pre>
	 * 任务： KYEEAPP-1066
	 * 描述： 保存文件
	 * 作者：houhy 
	 * 时间：2014年11月28日下午9:52:35
	 * @param kp
	 * @throws Exception
	 * returnType：void
	 * </pre>
	*/
	private static void saveKeyPair(KeyPair kp) throws Exception {
		String spath = RSAUtil.class.getClassLoader().getResource("").getPath();
		//String spath = this.getClass().getClassLoader().getResource("/").getPath();
		if (!spath.endsWith("/"))
			spath += "/";
		spath +=RSAKeyStore;		
		//解决文件路径中存在空格问题
		spath = URLDecoder.decode(spath, "UTF-8");
		
		FileOutputStream fos = new FileOutputStream(spath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus *
	 * @param publicExponent *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	private static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus *
	 * @param privateExponent *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	private static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(
				modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE,getKeyPair().getPublic());
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i
							* outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i
							* blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。

				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
/*	public static byte[] decrypt(byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE,getKeyPair().getPrivate());
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}*/
	
	/**
	 * <pre>
	 * 任务：KYEEAPP-1066 
	 * 描述： 解密
	 * 作者：houhy 
	 * 时间：2014年12月1日下午2:38:36
	 * @param raw
	 * @return
	 * @throws Exception
	 * returnType：String
	 * </pre>
	*/
	public static String decrypt(String raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE,getKeyPair().getPrivate());
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			//edit start KYEEAPP-1137 2014年12月14日 20:18:26 houhy
			//byte[] en_result = new BigInteger(raw, 16).toByteArray();
			byte[] en_result=hexStringToBytes(raw);
			//edit end KYEEAPP-1137 2014年12月14日 20:18:26 houhy
			while (en_result.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(en_result, j * blockSize, blockSize));
				j++;
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append(new String(bout.toByteArray()));
			String pwd = sb.reverse().toString();
			pwd = URLDecoder.decode(pwd,"UTF-8");
			return pwd;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	//edit start KYEEAPP-1137 2014年12月14日 20:18:26 houhy
	/**
     * 16进制 To byte[]<br>
     * <font color='red'> fix byte[] en_pwd = new BigInteger(pwd, 16).toByteArray();bug</font>
     * 
     * @param hexString
     * @return byte[]
     */
    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }


    /**
     * Convert char to byte
     * 
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }	
  //edit end KYEEAPP-1137 2014年12月14日 20:18:26 houhy
    
    
	/**
	 * * *
	 * 
	 * @param args *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		RSAUtil rsaUtil=new RSAUtil();	
		
		String test="20141128";
		byte[] en_test = encrypt(test.getBytes());
		//byte[] de_test = decrypt(en_test);
		//System.out.println(new String(de_test));
	}
}
