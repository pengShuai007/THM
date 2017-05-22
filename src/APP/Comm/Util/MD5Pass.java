package APP.Comm.Util;

import java.security.MessageDigest;
import java.util.ArrayList;

import APP.Comm.BLL.BaseBllException;


public class MD5Pass
{
	// Hash an input string and return the hash as
	// a 32 character hexadecimal string.
	public static String getMd5HashOld(String input)
	{
//		// Create a new instance of the MD5CryptoServiceProvider object.
//		MD5 md5Hasher = MD5.Create();
//
//		// Convert the input string to a byte array and compute the hash.
//		byte[] data = md5Hasher.ComputeHash(Encoding.Default.GetBytes(input));
//
//		// Create a new Stringbuilder to collect the bytes
//		// and create a string.
//		StringBuilder sBuilder = new StringBuilder();
//
//		// Loop through each byte of the hashed data 
//		// and format each one as a hexadecimal string.
//		for (int i = 0; i < data.length; i++)
//		{
//			sBuilder.append(String.format("%02x", data[i]));
//		}
//
//		// Return the hexadecimal string.
//		return sBuilder.toString();
		return null;
	}

	//************************字符串加密算法**********************
	public static String EncryptString(String str)
	{
		char[] Base64Code = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '=' };
		byte empty = (byte)0;
//		char[] input=new char[str.length()];
//		str.getChars(0, str.length(), input, 0);
		//
		java.util.ArrayList byteMessage = new ArrayList();
		for(byte b1: str.getBytes()){
			byteMessage.add(b1);
		}		
		
		StringBuilder outmessage;
		
		int messageLen = byteMessage.size();
		int page = messageLen / 3;
		int use = 0;
		if ((use = messageLen % 3) > 0)
		{
			for (int i = 0; i < 3 - use; i++)
			{
				byteMessage.add(empty);
			}
			page++;
		}
		outmessage = new StringBuilder(page * 4);
		for (int i = 0; i < page; i++)
		{
			byte[] instr = new byte[3];
			instr[0] = Byte.parseByte(byteMessage.get(i * 3)+"");
			instr[1] = Byte.parseByte(byteMessage.get(i * 3 + 1)+"");
			instr[2] = Byte.parseByte(byteMessage.get(i * 3 + 2)+"");
			int[] outstr = new int[4];
			outstr[0] = instr[0] >> 2;
			outstr[1] = ((instr[0] & 0x03) << 4) ^ (instr[1] >> 4);
			if (!(new Byte(instr[1])).equals(empty))
			{
				outstr[2] = ((instr[1] & 0x0f) << 2) ^ (instr[2] >> 6);
			}
			else
			{
				outstr[2] = 64;
			}
			if (!(new Byte(instr[2])).equals(empty))
			{
				outstr[3] = (instr[2] & 0x3f);
			}
			else
			{
				outstr[3] = 64;
			}
			outmessage.append(Base64Code[outstr[0]]);
			outmessage.append(Base64Code[outstr[1]]);
			outmessage.append(Base64Code[outstr[2]]);
			outmessage.append(Base64Code[outstr[3]]);
		}
		return outmessage.toString();
	}
	
	
	
	//*************************************************字符串解密算法*************************************************
	public static String DecryptString(String str) throws BaseBllException
	{
		if ((str.length() % 4) != 0)
		{
			throw new BaseBllException("不是正确的编码，请检查。");
		}
//		if (!System.Text.RegularExpressions.Regex.IsMatch(str, "^[A-Z0-9/+=]*$", System.Text.RegularExpressions.RegexOptions.IgnoreCase))
//		{
//			throw new BaseBllException("包含不正确的编码，请检查。");
//		}
		String Base64Code = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+/=";
		int page = str.length() / 4;
		ArrayList outMessage = new ArrayList(page * 3);
		char[] message = str.toCharArray();
		for (int i = 0; i < page; i++)
		{

			byte[] instr = new byte[4];
			instr[0] = (byte)Base64Code.indexOf(message[i * 4]);
			instr[1] = (byte)Base64Code.indexOf(message[i * 4 + 1]);
			instr[2] = (byte)Base64Code.indexOf(message[i * 4 + 2]);
			instr[3] = (byte)Base64Code.indexOf(message[i * 4 + 3]);
			byte[] outstr = new byte[3];
			outstr[0] = (byte)((instr[0] << 2) ^ ((instr[1] & 0x30) >> 4));
			if (instr[2] != 64)
			{
				outstr[1] = (byte)((instr[1] << 4) ^ ((instr[2] & 0x3c) >> 2));
			}
			else
			{
				outstr[2] = 0;
			}
			if (instr[3] != 64)
			{
				outstr[2] = (byte)((instr[2] << 6) ^ instr[3]);
			}
			else
			{
				outstr[2] = 0;
			}
			outMessage.add(outstr[0]);
			if (outstr[1] != 0)
			{
				outMessage.add(outstr[1]);
			}
			if (outstr[2] != 0)
			{
				outMessage.add(outstr[2]);
			}
		}
		byte[] outbyte = new byte[outMessage.size()];
		int i=0;
		for(Object b1: outMessage){
			outbyte[i]= Byte.parseByte(b1+"");
			i++;
		}
		return new String(outbyte);
	}

	
	public static void main(String[] args) throws BaseBllException{
		System.out.println(EncryptString("123456"));
		System.out.println(DecryptString("mtiZndu2"));
	}
	
	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年5月29日下午5:30:31
	 * @param _data
	 * @return
	 * @throws BaseBllException
	 */
	public static String Md5Encrypt(String _data) throws BaseBllException {
		String result = null;
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(_data.getBytes("UTF-8"));

			byte[] byteArray = messageDigest.digest();
			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
			result = md5StrBuff.toString();
		} catch (Exception e) {
			throw new BaseBllException(e);
		}
		return result;
	}
	
}