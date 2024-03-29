package com.ron.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class REncrypt {
	
	public static void sha256(){
	    try {
	//    	String  ccc = username+"=="+password; 
	    	String  ccc = "abcd"; 
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(ccc.getBytes("utf-8"));
		    for(byte b:md.digest()){
				System.out.format("%02X",b);	
		    }
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	  /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String base64Encode(byte[] bytes){  
        return new BASE64Encoder().encode(bytes);  
    }  
      
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    public static byte[] base64Decode(String base64Code) throws Exception{  
        return StringUtil.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);  
    } 
	
	 /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
//        kgen.init(128, new SecureRandom(encryptKey.getBytes())); 
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encryptKey.getBytes());
        kgen.init(128, random);
  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
          
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
      
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }
    
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
//        kgen.init(128, new SecureRandom(decryptKey.getBytes()));  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(decryptKey.getBytes());
        kgen.init(128, random);
          
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes);  
    }  
      
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
        return StringUtil.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
    } 

}
