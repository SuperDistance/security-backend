package com.platform.security.controller;

import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.CsoftSecurityUtil;
import com.platform.security.common.utils.ResultTool;
import org.jasypt.digest.StandardStringDigester;
import org.jasypt.digest.config.SimpleStringDigesterConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@RestController
@EnableAutoConfiguration
@RequestMapping("/seminar1")
public class Exp1Handler {

    //mapped to hostname:localhost/seminar1/exp1
    // 此处算法需要调整，全后端算法不一致，需要查阅文档手动配置
    // http://www.jasypt.org/api/jasypt/1.9.3/index.html
    @GetMapping("/exp1")
    public JsonResult exp1(@RequestParam("toEncry") String toEncry, @RequestParam("method") String method) {
        SimpleStringDigesterConfig digesterConfig = new SimpleStringDigesterConfig();
        digesterConfig.setStringOutputType("hexadecimal");
        StandardStringDigester standardStringDigester = new StandardStringDigester();
        standardStringDigester.setSaltSizeBytes(0);
        standardStringDigester.setIterations(1);
        String encryptedDigest = " ";
        switch (method) {
            case "MD5":
                digesterConfig.setAlgorithm("MD5");
                break;
            case "SHA-1":
                digesterConfig.setAlgorithm("SHA-1");
                break;
            case "SHA-256":
                digesterConfig.setAlgorithm("SHA-256");
                break;
            case "SHA-512":
                digesterConfig.setAlgorithm("SHA-512");
                break;
        }
        standardStringDigester.setConfig(digesterConfig);
        encryptedDigest = standardStringDigester.digest(toEncry);
        return ResultTool.success(encryptedDigest.toLowerCase());
    }
    //mapped to localhost:port/seminar1/exp2
    @GetMapping("/exp2")
    @ResponseBody
    public JsonResult exp2(@RequestParam("method") String method, @RequestParam("enc") String enc, @RequestParam("iv") String iv,
                         @RequestParam("key") String key) throws Exception {

        // 因为JCA不支持AES CBC PKCS7Padding 手动注册第三方算法提供者BouncyCastle
        if (Security.getProvider("BC") == null) {
            Security .addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } else {
            Security.removeProvider("BC");
            Security .addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }

        String [] results=new String[2];
        String decryptMessage = null;
        double beginTime = 0;
        double endTime = 0;

        // 该结果与前端的publicKey是不同编码的同个数据
        byte[] realKey = "1234567890ABCDEF1234567890ABCDEf".getBytes(StandardCharsets.UTF_8);// key.getBytes(StandardCharsets.UTF_8);
        String k2="1234567890ABCDEF1234567890ABCDEf";
        System.out.println("密钥长度"+realKey.length);
        System.out.println("密钥（Byte）"+ Arrays.toString(realKey));
        System.out.println("密钥（HEX）:"+byteToHexString(realKey));

        if (method.equals("DES")) {
            Algorithm = "DES";
            AlgorithmProvider = "DES/CBC/PKCS7Padding";
        } else if (method.equals("AES")) {
            Algorithm = "AES";
            AlgorithmProvider = "AES/CBC/PKCS7Padding";
        }
        System.out.println("解密前:"+enc);
        beginTime = System.nanoTime();
        if(Algorithm.equals("AES")){
            decryptMessage = new String(decrypt(enc, realKey), StandardCharsets.UTF_8);
        }
        else if (Algorithm.equals("DES")) {
            decryptMessage = decryption(enc, k2);
        }
        endTime = System.nanoTime();
        double costTime = (endTime - beginTime)/1000000.0;
        System.out.println("begin:"+beginTime);
        System.out.println("end:"+endTime);
        System.out.println(costTime);
        System.out.println("解密:" + decryptMessage);
        results[0] = decryptMessage;
        results[1] = String.valueOf(costTime);
        return ResultTool.success(results);
    }

    // 可以使用session或者spring radis存储密码相关的量，这里先写在类里
    private byte[] sessionPrivateKey, sessionPublicKey;

    //mapped to localhost:port/seminar1/exp3/key
    @RequestMapping("/exp31")
    byte[] exp31() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        sessionPublicKey = org.apache.commons.codec.binary.Base64.encodeBase64(publicKey.getEncoded());
        sessionPrivateKey = org.apache.commons.codec.binary.Base64.encodeBase64(privateKey.getEncoded());
        return sessionPublicKey;
    }
    //mapped to localhost:port/seminar1/exp3/key
    @RequestMapping("/exp32")
    JsonResult exp32(@RequestParam("enc") String enc) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        String [] results = new String[2];
        String decryptMessage = "Fail";
        // JDK 1.8
        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = sessionPrivateKey;
        //编码final
        String encodedText = encoder.encodeToString(textByte);
        System.out.println(encodedText);
        //解码
        String privateKeyString = new String(decoder.decode(encodedText));

        System.out.println("解密前:"+enc);
        long begintime = System.nanoTime();
        decryptMessage = CsoftSecurityUtil.decryptRSADefault(privateKeyString, enc);
        long endtime = System.nanoTime();
        long costTime = (endtime - begintime)/1000000;

        System.out.println(costTime);
        System.out.println("解密:" + decryptMessage);
        results[0] = decryptMessage;
        results[1] = String.valueOf(costTime);
        return ResultTool.success(results);
    }

    //mapped to localhost:port/seminar1/exp41
    @RequestMapping("/exp41")
    @ResponseBody
    public JsonResult getPublicKey(){
        RSAPublicKey userPublicKey = (RSAPublicKey) userKeyPair.getPublic();
        RSAPublicKey caPublicKey = (RSAPublicKey) caKeyPair.getPublic();
        String [] publicKeys = new String[2];
        publicKeys[0] = org.apache.commons.codec.binary.Base64.encodeBase64String(userPublicKey.getEncoded());
        publicKeys[1] = org.apache.commons.codec.binary.Base64.encodeBase64String(caPublicKey.getEncoded());
        return ResultTool.success(publicKeys);
    }

    //mapped to localhost:port/seminar1/exp42
    @RequestMapping("/exp42")
    @ResponseBody
    public byte[] genCertification (@RequestParam("issuer") String issuer, @RequestParam("startTime") String startTime,
                                     @RequestParam("endTime") String endTime, @RequestParam("encryMethod") String encryMethod,
                                     @RequestParam("publicKey") byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        // TODO
        String toEncry = "issuer:" + issuer + "\nstartTime:" + startTime + "\nendTime:" + endTime
                + "\npublicKey:" + new String(publicKey) + "\nencryMethod:" + encryMethod;


        byte[] contents = toEncry.getBytes();

        Signature signature = Signature.getInstance("SHA1WithRSA");

        RSAPrivateKey privateKey = (RSAPrivateKey) caKeyPair.getPrivate();
        signature.initSign(privateKey);
        signature.update(contents);
        return org.apache.commons.codec.binary.Base64.encodeBase64(signature.sign());
    }

    @RequestMapping("/exp43")
    public byte[] genSignature (@RequestParam("contents") String contents)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA1WithRSA");
        RSAPrivateKey privatekey = (RSAPrivateKey) userKeyPair.getPrivate();
        signature.initSign(privatekey);
        signature.update(contents.getBytes());
        return org.apache.commons.codec.binary.Base64.encodeBase64(signature.sign());
    }

    @RequestMapping("/exp44")
    public JsonResult validation (@RequestParam("contents") String contents, @RequestParam("issuer") String issuer,
                              @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime,
                              @RequestParam("encryMethod") String encryMethod, @RequestParam("publicKey") String publicKey,
                              @RequestParam("cirtiSign") byte[] cirtiSign, @RequestParam("signature") byte[] signature)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        String toDigest = "issuer:" + issuer + "\nstartTime:" + startTime + "\nendTime:" + endTime
                + "\npublicKey:" + new String(publicKey) + "\nencryMethod:" + encryMethod;

        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initVerify(caKeyPair.getPublic());
        sig.update(toDigest.getBytes());
        // validate the certification file
        if(!sig.verify(org.apache.commons.codec.binary.Base64.decodeBase64(cirtiSign))){
            return ResultTool.success("证书无效");
        }
        // get the userPublicKey

        byte[] keybytes = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keyspec = new X509EncodedKeySpec(keybytes);
        RSAPublicKey userPublicKey = (RSAPublicKey) keyfactory.generatePublic(keyspec);

        sig.initVerify(userPublicKey);
        sig.update(contents.getBytes());

        if (!sig.verify(org.apache.commons.codec.binary.Base64.decodeBase64(signature))){
            return ResultTool.success("内容经过篡改");
        }
        return ResultTool.success("内容完整未修改");
    }

    private static KeyPair genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(512, new SecureRandom());
        return keyPairGen.genKeyPair();
    }

    private static KeyPair userKeyPair, caKeyPair;

    static {
        try {
            userKeyPair = genKeyPair();
            caKeyPair = genKeyPair();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private static final String DES_ALGORITHM = "DES";

    public static String encryption(String plainData, String secretKey) throws Exception {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {

        }

        try {
            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must
            // be multiple of 8 when decrypting with padded cipher异常，
            // 不能把加密后的字节数组直接转换成字符串
            byte[] buf = cipher.doFinal(plainData.getBytes());

            return Base64Utils.encode(buf);

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }
    }

    /**
     * DES解密
     * @param secretData 密码字符串
     * @param secretKey 解密密钥
     * @return 原始字符串
     * @throws Exception
     */
    public static String decryption(String secretData, String secretKey) throws Exception {

        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new Exception("NoSuchPaddingException", e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new Exception("InvalidKeyException", e);

        }

        try {
            byte[] hexBytes = hexStringToBytes(secretData);
            //   byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));
            byte[] buf = cipher.doFinal(hexBytes);
            return new String(buf);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }
    }

    /**
     * 获得秘密密钥
     *
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     */
    private static SecretKey generateKey(String secretKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
        keyFactory.generateSecret(keySpec);
        return keyFactory.generateSecret(keySpec);
    }

    static private class Base64Utils {

        static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
                .toCharArray();
        static private byte[] codes = new byte[256];

        static {
            for (int i = 0; i < 256; i++)
                codes[i] = -1;
            for (int i = 'A'; i <= 'Z'; i++)
                codes[i] = (byte) (i - 'A');
            for (int i = 'a'; i <= 'z'; i++)
                codes[i] = (byte) (26 + i - 'a');
            for (int i = '0'; i <= '9'; i++)
                codes[i] = (byte) (52 + i - '0');
            codes['+'] = 62;
            codes['/'] = 63;
        }

        /**
         * 将原始数据编码为base64编码
         */
        static private String encode(byte[] data) {
            char[] out = new char[((data.length + 2) / 3) * 4];
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) data[i]);
                val <<= 8;
                if ((i + 1) < data.length) {
                    val |= (0xFF & (int) data[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < data.length) {
                    val |= (0xFF & (int) data[i + 2]);
                    quad = true;
                }
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 1] = alphabet[val & 0x3F];
                val >>= 6;
                out[index + 0] = alphabet[val & 0x3F];
            }

            return new String(out);
        }

        /**
         * 将base64编码的数据解码成原始数据
         */
        static private byte[] decode(char[] data) {
            int len = ((data.length + 3) / 4) * 3;
            if (data.length > 0 && data[data.length - 1] == '=')
                --len;
            if (data.length > 1 && data[data.length - 2] == '=')
                --len;
            byte[] out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;
            for (int ix = 0; ix < data.length; ix++) {
                int value = codes[data[ix] & 0xFF];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) ((accum >> shift) & 0xff);
                    }
                }
            }
            if (index != out.length)
                throw new Error("miscalculated data length!");
            return out;
        }
    }


    // AES
    private static String iv = "0123456789ABCDEF";//偏移量字符串必须是16位 当模式是CBC的时候必须设置偏移量
        private static String Algorithm = "AES";
        private static String AlgorithmProvider = "AES/CBC/PKCS7Padding"; //算法/模式/补码方式

        public static byte[] generatorKey() throws NoSuchAlgorithmException {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm);
            keyGenerator.init(256);//默认128，获得无政策权限后可为192或256
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        }

        private static IvParameterSpec getIv() throws UnsupportedEncodingException {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            System.out.println("偏移量："+byteToHexString(ivParameterSpec.getIV()));
            return ivParameterSpec;
        }

        private static byte[] decrypt(String src, byte[] key) throws Exception {
            SecretKey secretKey = new SecretKeySpec(key, Algorithm);

            IvParameterSpec ivParameterSpec = getIv();
            Cipher cipher = Cipher.getInstance(AlgorithmProvider,"BC");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] hexBytes = hexStringToBytes(src);
            byte[] plainBytes = cipher.doFinal(hexBytes);
            return plainBytes;
        }

        /**
         * 将byte转换为16进制字符串
         * @param src
         * @return
         */
        private static String byteToHexString(byte[] src) {
            StringBuilder sb = new StringBuilder();
            for (byte b : src) {
                int v = b & 0xff;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    sb.append("0");
                }
                sb.append(hv);
            }
            return sb.toString();
        }

        /**
         * 将16进制字符串装换为byte数组
         * @param hexString
         * @return
         */
        private static byte[] hexStringToBytes(String hexString) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] b = new byte[length];
            for (int i = 0; i < length; i++) {
                int pos = i * 2;
                b[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }
            return b;
        }

        private static byte charToByte(char c) {
            return (byte) "0123456789ABCDEF".indexOf(c);
        }
}
