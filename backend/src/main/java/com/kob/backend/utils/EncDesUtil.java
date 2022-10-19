package com.kob.backend.utils;

import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 堆成加密工具类
 * @date 2022/10/18 22:29:15
 */
public class EncDesUtil {
    /**
     * 默认密钥
     */
    private static String strDefaultKey = "YNbIPFx25UNtE9";

    private Cipher encryptCipher = null;

    private Cipher decryptCipher = null;


    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        int len = bytes.length;
        if (len <= 0) {
            return "";
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)){
            return null;
        }
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }


    /**
     * 默认构造方法，使用默认密钥
     *
     * @throws Exception
     */
    public EncDesUtil() {
        this(strDefaultKey);
    }

    private static EncDesUtil encDesUtils = new EncDesUtil();

    public static EncDesUtil getEncUtil() {
        try {
            if (encDesUtils == null) {
                encDesUtils = new EncDesUtil();
            }
            return encDesUtils;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定密钥构造方法
     *
     * @param strKey 指定的密钥
     * @throws Exception
     */
    public EncDesUtil(String strKey) {

        try {
            Key key = getKey(strKey.getBytes("UTF-8"));

            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {

        }

    }

    /**
     * 加密字节数组
     *
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }


    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn) {
        try {
            if (StringUtils.isEmpty(strIn)) {
                return strIn;
            }
            return bytes2HexString(encrypt(strIn.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strIn;

    }

    /**
     * 解密字节数组
     *
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        if(arrB.length < 8){
            return null;
        }
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn) throws Exception {
        if (StringUtils.isEmpty(strIn)) {
            return strIn;
        }
        return new String(decrypt(hexString2Bytes(strIn)),"UTF-8");
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBtmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private Key getKey(byte[] arrBtmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBtmp.length && i < arrB.length; i++) {
            arrB[i] = arrBtmp[i];
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    /**
     * 解密方法
     *
     * @param result
     * @return
     */
    public static String dec(String result) {
        if(StringUtils.isEmpty(result)){
            return "";
        }
        // 自定义密钥.
        EncDesUtil des = null;
        String decontent = null;
        try {
            des = new EncDesUtil();

            decontent = des.decrypt(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return decontent;
    }

    /**
     * 加密
     *
     * @param result
     * @return
     */
    public static String enc(String result) {
        // 自定义密钥
        EncDesUtil des = null;
        String encontent = null;
        try {
            des = new EncDesUtil();

            encontent = des.encrypt(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontent;
    }


}
