package com.yongdui.security.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author TangGaowei
 */
public class SignUtils {
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String SIGN_TYPE_RSA = "RSA";
    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";


    /**
     * 基础签名
     */
    public static String sign(String content, String privateKey, String charset) {

        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return Base64Utils.encode(signed);
        } catch (Exception e) {
            throw new RuntimeException("签名失败：RSAcontent = " + content + "; charset = " + charset, e);
        }

    }

    public static String sign(String content, String privateKey) {
        return sign(content, privateKey, CHARSET_UTF8);
    }

    public static String sign(Map<String, String> params, String privateKey, String charset) {
        String signContent = getSignContent(params);

        return sign(signContent, privateKey, charset);

    }

    public static String sign(Map<String, String> params, String privateKey) {
        return sign(params, privateKey, CHARSET_UTF8);

    }

    /**
     * 基础验签
     */
    public static boolean check(String content, String sign, String publicKey, String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64Utils.decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("验证失败：RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    public static boolean check(Map<String, String> params, String publicKey, String charset) {
        String sign = params.get("sign");
        String content = getSignCheckContent(params);

        return check(content, sign, publicKey, charset);
    }

    public static boolean check(Map<String, String> params, String publicKey) {
        return check(params, publicKey, CHARSET_UTF8);
    }

    public static boolean check(String content, String sign, String publicKey) {
        return check(content, sign, publicKey, CHARSET_UTF8);
    }

    public static boolean checkParameterMap(Map<String, String[]> parameterMap, String publicKey, String charset) {
        Map<String, String> params = getMap(parameterMap);
        String sign = params.get("sign");
        String content = getSignCheckContent(params);

        return check(content, sign, publicKey, charset);
    }

    public static boolean checkParameterMap(Map<String, String[]> parameterMap, String publicKey) {
        return checkParameterMap(parameterMap, publicKey, CHARSET_UTF8);
    }

    public static boolean checkJson(String json, String publicKey, String charset) {
        // 获取签名
        String sign = getSignFromJson(json);
        String content = getContentFromJson(json);
        return check(content, sign, publicKey, charset);
    }

    public static boolean checkJson(String json, String publicKey) {
        return checkJson(json, publicKey, CHARSET_UTF8);
    }

    /**
     * 从 json 返回数据中获取内容
     */
    public static String getContentFromJson(String json) {
        // 获取 json 内容
        int responseBeginIndex = json.indexOf("\"response\":");
        responseBeginIndex += 11;
        if (json.contains(",\"sign\"")) {
            int responseEndIndex = json.indexOf(",\"sign\"");
            return json.substring(responseBeginIndex, responseEndIndex);
        } else {
            return json.substring(responseBeginIndex, json.length() - 1);
        }
    }

    /**
     * 从 json 返回数据中获取签名信息
     */
    public static String getSignFromJson(String json) {
        // 获取签名
        int signBeginIndex = json.indexOf("\"sign\":\"");
        signBeginIndex += 8;
        String sign = json.substring(signBeginIndex);
        int signEndIndex = sign.indexOf("\"");
        return sign.substring(0, signEndIndex);
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

            byte[] encodedKey = Base64Utils.decode(StreamUtils.readText(ins));

            return encodedKey != null ? keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey)) : null;
        } catch (Exception e) {
            throw new RuntimeException("algorithm = " + algorithm, e);
        }
    }

    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) {
        try {
            if (ins == null || StringUtils.isEmpty(algorithm)) {
                return null;
            }

            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

            byte[] encodedKey = Base64Utils.decode(StreamUtils.readText(ins));

            return encodedKey != null ? keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey)) : null;
        } catch (Exception e) {
            throw new RuntimeException("algorithm = " + algorithm, e);
        }
    }

    private static String getSignCheckContent(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");

        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&"));
            content.append(key);
            content.append("=");
            content.append(value);
        }

        return content.toString();
    }

    private static Map<String, String> getMap(Map<String, String[]> parameterMap) {
        Map<String, String> map = new HashMap();
        for (String key : parameterMap.keySet()) {
            map.put(key, parameterMap.get(key)[0]);
        }
        return map;
    }

    private static String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringUtils.areNotEmpty(key, value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }
}
