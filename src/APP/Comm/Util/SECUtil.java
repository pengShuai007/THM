package APP.Comm.Util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


/**
 * HRP的公用密鈅和私用密鈅 公用密鈅用来加密，私用密钥用来解密 施建龙 2013年12月2日17:50:05
 */
public class SECUtil {
	public SECUtil() {
		super();
		// TODO Auto-generated constructor stub
		
	}

//	private  Base64 encoder64=new Base64();
//	private  Base64 decoder64=new Base64();	
	
	private  String Algorithm = "DES"; // 定义 加密算法,可用
	// DES,DESede,Blowfish

	private  boolean debug = false;
	
	private Base32 base32=new Base32();
	
//	static {
//		Security.addProvider(new com.sun.crypto.provider.SunJCE());
//	}

	// 生成密钥, 注意此步骤时间比较长
	//随机密钥，测试时使用，正式环境下使用定制的Key
	//施建龙
	public  byte[] getTempKey() throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
		SecretKey deskey = keygen.generateKey();
		if (debug)
			System.out.println("生成密钥:" + byte2hex(deskey.getEncoded()));
		return deskey.getEncoded();
	}
	
	
	
	public  String desEncrypt(String message) throws Exception {
		String key=this.getHrpDesKey();
		
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
//		byte[] IV = { (byte)0x85, (byte)0x21, (byte)0x54, (byte)0x48, (byte)0x18, (byte)0x99, (byte)0x19, (byte)0x50 };
		String[] sArray={
	            "-123", "33", "84", "72", "24", "-103", "25", "80"};
		byte[] IV = string2ByteArray(sArray);
		//		byte[] IV = { 0x45, 0x21, 0x54, 0x48, 0x18, 0x49, 0x19, 0x50 };
		IvParameterSpec iv = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return Base64.encodeBase64String(cipher.doFinal(message.getBytes("UTF-8")));
	}
	
	public  String desDecrypt(String message) throws Exception {
		byte[] bytesrc = Base64.decodeBase64(message);
		String key=this.getHrpDesKey();
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		String[] sArray = { "-123", "33", "84", "72", "24", "-103", "25", "99" };// 密钥字符串更改
		byte[] IV = string2ByteArray(sArray);
		IvParameterSpec iv = new IvParameterSpec(IV);

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		
		return new String(retByte,"UTF8");
	}

	// md5()信息摘要, 不可逆
	public  byte[] md5(byte[] input) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest
		.getInstance("MD5"); // or "SHA-1"
		if (debug) {
			System.out.println("摘要前的二进串:" + byte2hex(input));
			System.out.println("摘要前的字符串:" + new String(input));
		}
		alg.update(input);
		byte[] digest = alg.digest();
		if (debug)
		System.out.println("摘要后的二进串:" + byte2hex(digest));
		return digest;
	}

	// 字节码转换成16进制字符串
	private  String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
			//hs = hs + ":";
				hs = hs + "";
		}
		return hs.toUpperCase();
	}


	private  byte[] hex2byte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
		}
		return bts;
	}  

	/**
	 * 创建密钥对生成器，指定加密和解密算法为RSA
	 * 
	 * @param keylen
	 * @return
	 */
	public String[] createKey(int keylen) {// 输入密钥长度
		String[] output = new String[5]; // 用来存储密钥的e n d p q
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(keylen); // 指定密钥的长度，初始化密钥对生成器
			
			String e="116AB";
			String n="572C853AA53E688AABE2C2E2226818D872D01CC42E6D3B862767EE56B95B4FB7AD7EE7AF4571B98D667240E3DD95E1CD9C268E239F7F7B12AA87FF0312034BE1";
			String d="2155D3AFB91F6EDE150604D0270D3BDBBB6980766D18608347E8365D9D779C0FCDA71E14BEED70F483D1976EAA0E2207192225AF4908993224D82CD79846E5C3";
			String p="84674325708568FFADBACEE799D50F60B8312253F388DDCB0367B0A4A9C0744B";
			String q="A88CAB7C79EBDA3362978B569E86DC97FB72CE3304A6E51953EEA28676FA6D03";
//			String e="10001";
//			String n="9aa83262a952b6cf4ffaa55a7ee31d82dbec098ff67266c9bc184bc6630583b5c35cb5060e04d536c48033913349893e1a8216b605cf0c60b83769a6484a6321500e24cc424b81fc3a1165602025bb1e980bd3a66ba4290fea57039069aeb40a9c60b298cf340b772e900098b77d93dc789022c34dc56bdfb5ea64fa65296b57";
//			String d="387d6b3a026306071f4d8c0c4c00a50ce14b8c0a9b9819d761eef5ee96c8b2dd3768ab6e74bc6b2cc0059bd0538d6ea5d284e5cac2c067e30426a95e6555567388d8203c7dbfbc6f6e49f746b71e3050be99132e43851f7c122a7f2d3aa2e31944afafabbb05a402f557dab93253a4430a7ee2e29ec11d1d5c8212ea7391d6a1";
//			String p="d52764ed0f39e1449613a1e3d30121144805d3363ccc7fd0bd56fafacd15d9c385b6bf8d0a257e2d771d0fd1b1140d43cb691354ed32ad85f5556179c63c2999";
//			String q="b9bea2644d02c2c1684d5aee4c5d026d9137c101b98dd125264ca78c1dff2db1074bf15754065ed356716bd60ccc661f08089118140142816ea086e30bbfb26f";
			output[0] = e.toString();
			output[1] = n.toString();
			output[2] = d.toString();
			output[3] = p.toString();
			output[4] = q.toString();
		} catch (NoSuchAlgorithmException ex) {
//			HLogger.Error(ex);
			ex.printStackTrace();
		}
		return output;
	}

	/**
	 * 加密在RSA公钥中包含有两个整数信息：e和n。对于明文数字m,计算密文的公式是m的e次方再与n求模。
	 * 
	 * @param clearText
	 *            明文
	 * @param eStr
	 *            公钥
	 * @param nStr
	 * @return
	 */
	public String encrypt(String clearText) {
		String secretText = new String();
		String[] str =createKey(512);//均为16进制文本
		String eStr=str[0];
		String nStr=str[1];
		try {
			clearText = URLEncoder.encode(clearText, "UTF-8");
			byte text[] = clearText.getBytes("UTF-8");// 将字符串转换成byte类型数组，实质是各个字符的二进制形式
			BigInteger mm = new BigInteger(text);// 二进制串转换为一个大整数
			clearText = mm.toString();

			BigInteger e = new BigInteger(eStr,16);
			BigInteger n = new BigInteger(nStr,16);
			byte[] ptext = clearText.getBytes("UTF-8"); // 获取明文的大整数
			BigInteger m = new BigInteger(ptext);
			BigInteger c = m.modPow(e, n);
			secretText = c.toString();
		} catch (UnsupportedEncodingException ex) {
//			HLogger.Error(ex);
			ex.printStackTrace();
		}
		return secretText;
	}

	/**
	 * 解密
	 * 
	 * @param secretText
	 *            密文
	 * @param dStr
	 *            私钥
	 * @param nStr
	 * @return
	 */
	public String decrypt(String secretText) {
		StringBuffer clearTextBuffer = new StringBuffer();
		String[] str =createKey(512);//均为16进制文本
		String dStr=str[2];
		String nStr=str[1];
		BigInteger d = new BigInteger(dStr,16);// 获取私钥的参数d,n
		BigInteger n = new BigInteger(nStr,16);
		BigInteger c = new BigInteger(secretText);
		BigInteger m = c.modPow(d, n);// 解密明文
		byte[] mt = m.toByteArray();// 计算明文对应的字符串并输出
		for (int i = 0; i < mt.length; i++) {
			clearTextBuffer.append((char) mt[i]);
		}
		String temp = clearTextBuffer.toString();// temp为明文的字符串形式
		BigInteger b = new BigInteger(temp);// b为明文的BigInteger类型
		byte[] mt1 = b.toByteArray();

		try {
			String clearText = (new String(mt1, "UTF-8"));
			clearText = URLDecoder.decode(clearText, "UTF-8");
			return clearText;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * comments:从财务数据导出导入接口中提取出来，成为公共的处理方法
	 * 为财务接口导入专用方法
	 * 
	 * sjl modify 2014年2月10日下午4:29:13
	 */
	public String getMd5Hash(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

//			messageDigest.update(str.getBytes("UTF-8"));
			messageDigest.update(str.getBytes("GBK"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}  
	
	/**
	 * comments:使用3des或者aes进行加密
	 * 
	 * sjl modify 2014年2月10日下午4:39:55
	 */
	private String getHrpAesKey(){
		String key="1111111111111111";
		return key;
	}
	
	/**
	 * comments:
	 * 
	 * sjl modify 2014年2月13日上午10:16:47
	 */
	private String getHrpDesKey(){
		String key="40082060";
		return key;
	}
	
	
	/**
	 * comments:String数组转换为Byte数组，用于加密处理
	 * 
	 * sjl modify 2014年2月10日下午4:49:52
	 */
	private  byte[] string2ByteArray(String[] data){
		
		byte[] IV = new byte[data.length];
		int i=0;
		for (String s : data) {
			IV[i]=Byte.valueOf(s);
			i++;
		}
		
		return IV;
	}
	
	
	public String md5Encrypt(String messag){
		String str = DigestUtils.md5Hex(messag);
		return str;
	}
	

	
	public String aesDecrypt(String message) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		String sKey=this.getHrpAesKey();
//		byte[] bytesrc = Base64.decodeBase64(message);
		byte[] bytesrc = hex2byte(message);
//		String[] sArray={
//	            "-123", "33", "84", "72", "24", "-103", "25", "80","-123", "33", "84", "72", "24", "-103", "25", "80"};
		String[] sArray={
				"-123", "16", "69", "72", "24", "-103", "25", "80","-123", "33", "84", "72", "24", "-103", "25", "80"};
//		String[] sArray={
//				"-123", "16", "69", "72", "24", "-103", "25", "80","-123", "33", "84", "72", "24", "-103", "25", "64"};
		byte[] IV = string2ByteArray(sArray);
		Key key = new SecretKeySpec(sKey.getBytes("UTF-8"), "AES");
		Cipher out = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
		out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
		byte[] dec = out.doFinal(bytesrc);
		
		return new String(dec,"UTF8");
	}
	
	/**
	 * comments:16位密钥，对称加密
	 * 
	 * sjl modify 2014年2月12日下午3:07:51
	 */
	public String aesEncrypt(String message) throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		String sKey=this.getHrpAesKey();
		Key key = new SecretKeySpec(sKey.getBytes("UTF-8"), "AES");
//		String[] sArray={
//	            "-123", "33", "84", "72", "24", "-103", "25", "80","-123", "33", "84", "72", "24", "-103", "25", "80"};
		String[] sArray={
				"-123", "16", "69", "72", "24", "-103", "25", "80","-123", "33", "84", "72", "24", "-103", "25", "80"};
		byte[] IV = string2ByteArray(sArray);
		Cipher in = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
		in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
		byte[] enc = in.doFinal(message.getBytes("UTF-8"));
		
//		String base64String=Base64.encodeBase64String(enc);
		String hexString=byte2hex(enc);
		return hexString;
	}

	/**
	 * <pre>
	 * 任务：KYEEAPPC-1356 
	 * 描述：DES加密，带有特殊前缀
	 * 作者：houhy 
	 * 时间：2015年1月19日下午4:38:13
	 * @param message
	 * @return
	 * @throws Exception
	 * returnType：String
	 * </pre>
	 */
	public String desEncryptPrefix(String message) throws Exception {
		String Prefix = "!!@#$!!!";
		String result = null;
		result = Prefix + desEncryptNew(message);
		return result;
	}

	// 加密
	public String desEncryptNew(String message) throws Exception {
		String key = this.getHrpDesKey();
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		String[] sArray = { "-123", "33", "84", "72", "24", "-103", "25", "99" };// 密钥字符串更�?
		byte[] IV = string2ByteArray(sArray);
		IvParameterSpec iv = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return Base64.encodeBase64String(cipher.doFinal(message
				.getBytes("UTF-8")));
	}
	
	public static void main(String[] args) throws Exception {
		SECUtil rsa = new SECUtil();
		String clearText = "13619293899"; 
//		String encryptStr=rsa.encrypt(clearText);
//		System.out.println("加密前："+clearText);
//		System.out.println("加密后："+encryptStr);
//		String decryptStr=rsa.decrypt(encryptStr);
//		System.out.println("解密后："+decryptStr);
		
		System.out.println("desEncryptPrefix加密前：" + clearText);
		String newStr=rsa.desEncryptPrefix(clearText);
		String newStr2 = rsa.desDecrypt(newStr);
		//!!@#$!!!HPfNkiJX4usSIBeeMphmSg==
		//!!@#$!!!CdNnwdtRCBmiXttMU8O0Zw==
		System.out.println("desEncryptPrefix加密后：" + newStr);
		System.out.println("desEncryptPrefix解密后：" + newStr2);
	}
}