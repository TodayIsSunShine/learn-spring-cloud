package com.xiangzi.gatewaylimiter.entry;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;


public class AESEncrypter {
    private static final byte[] iv = new byte[]{82, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75};
    private static final byte[] key = new byte[]{-42, 35, 67, -86, 19, 29, -11, 84, 94, 111, 75, -104, 71, 46, 86, -21, -119, 110, -11, -32, -28, 91, -33, -46, 99, 49, 2, 66, -101, -11, -8, 56};

    private AESEncrypter() {
    }

    public static String encrypt(String msg) {
        String str = null;

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key);
            kgen.init(128, secureRandom);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            SecretKey key = kgen.generateKey();
            Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ecipher.init(1, key, paramSpec);
            str = asHex(ecipher.doFinal(msg.getBytes("UTF-8")));
        } catch (Exception var7) {
        }

        return str;
    }

    public static String decrypt(String value) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key);
            kgen.init(128, secureRandom);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            SecretKey key = kgen.generateKey();
            Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            dcipher.init(2, key, paramSpec);
            return new String(dcipher.doFinal(asBin(value)), "UTF-8");
        } catch (Exception var6) {
            return null;
        }
    }

    private static String asHex(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);

        for (int i = 0; i < buf.length; ++i) {
            if ((buf[i] & 255) < 16) {
                strbuf.append("0");
            }

            strbuf.append(Long.toString((long) (buf[i] & 255), 16));
        }

        return strbuf.toString();
    }

    private static byte[] asBin(String src) {
        byte[] encrypted = new byte[src.length() / 2];

        for (int i = 0; i < src.length() / 2; ++i) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
            encrypted[i] = (byte) (high * 16 + low);
        }

        return encrypted;
    }

    public static void main(String[] args) {
/*        System.out.println(encrypt("pledge_test"));
        System.out.println(encrypt("pledge_test123"));
        System.out.println(encrypt("123321"));
        System.out.println(decrypt("3f3cd8ff50028d805dd78a2cf22746b8"));
        System.out.println(encrypt("hbfintech_dev"));
        System.out.println(encrypt("hbfintech"));
        System.out.println(encrypt("123321"));
        System.out.println(decrypt("3f3cd8ff50028d805dd78a2cf22746b8"));
        System.out.println(decrypt("7f30abb47f457b3d6ba701f5b6c8607f"));*/
//        System.out.println(encrypt("hbnuggets_read"));
//        System.out.println(encrypt("j91LIS8x"));
//        System.out.println(decrypt("52680bcc3e0f29b9e58d9575aef597540088634cd81f30d8d196f078d9faa675"));
//        System.out.println(decrypt("52680bcc3e0f29b9e58d9575aef59754745c8ceaa132555d272a8fe10d680807"));

        String str = "{\"code\":999999,\"msg\":\"[39000], 处理成功\"}";

    }
}

