package com.yipeng.bill.bms.core.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lancey on 15/3/23.
 */
public class CryptoUtils {

	/**
	 * 通过MD5加密
	 * @param source
	 * @return  null表示加密失败，成功时返回16进制数据
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(final String source) {
		return cryptoString(source, "MD5", ENCODING);
	}

	private static String cryptoString(String source, String crypto, String encoding) {
		MessageDigest alga;
		String myinfo = source;
		try {
			alga = MessageDigest.getInstance(crypto);
			alga.update(myinfo.getBytes(encoding));
			byte[] digesta = alga.digest();
			String hs = "";
			String stmp = "";
			for (int n = 0; n < digesta.length; n++) {
				stmp = (Integer.toHexString(digesta[n] & 0XFF));
				if (stmp.length() == 1)
					hs = hs + "0" + stmp;
				else
					hs = hs + stmp;
			}
			return hs;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过SHA加密
	 * @param source
	 * @return  null表示加密失败，成功时返回16进制数据
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha(final String source) {
		return cryptoString(source, "SHA", ENCODING);
	}

	private static final String ENCODING = "UTF-8";

	public static void main(String[] args) {
		System.out.println(md5("maidou88efh"));
	}
}
