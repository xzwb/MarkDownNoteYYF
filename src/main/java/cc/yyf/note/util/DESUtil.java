package cc.yyf.note.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Des加密/解密算法
 */
public class DESUtil {
    /**
     * 偏移变量固定占8位字节
     */
    private static final String IV_PARAMETER = "12345678";

    /**
     * 秘钥算法
     */
    private static final String ALGORITHM = "DES";

    /**
     * 加密解密算法-工作模式-填充模式
     */
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";

    /**
     * 生成key
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static Key generateKey(String password) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        DESKeySpec desKeySpec = new DESKeySpec(password.getBytes(CHARSET));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(desKeySpec);
    }

    /**
     * DES加密字符串
     * @param password
     * @param data
     * @return
     */
    public static String encrypt(String password, String data) {
        if (password == null || password.length() < 8) {
            throw new RuntimeException("加密失败key不能小于8位");
        }
        if (data == null) {
            return null;
        }
        try {
            Key key = generateKey(password);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));

            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 解密字符串
     * @param password
     * @param data
     * @return
     */
    public static String decrypt(String password, String data) {
        if (password == null || password.length() < 8) {
            throw new RuntimeException("加密失败key不能小于8位");
        }
        if (data == null) {
            return null;
        }
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes(CHARSET))), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }
}
