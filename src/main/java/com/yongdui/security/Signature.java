package com.yongdui.security;

import com.yongdui.security.util.SignUtils;

import java.util.Map;

/**
 * @author TangGaowei
 */
public class Signature {
    private String charset;
    private String privateKey; // 我方私钥
    private String otherPublicKey; // 对方公钥

    public Signature(String privateKey) {
        this.charset = "UTF-8";
        this.privateKey = privateKey;
        this.otherPublicKey = "";
    }

    public Signature(String privateKey, String otherPublicKey) {
        this.charset = "UTF-8";
        this.privateKey = privateKey;
        this.otherPublicKey = otherPublicKey;
    }


    /**
     * 字符串签名
     */
    public String sign(String content) {
        return SignUtils.sign(content, privateKey, charset);
    }

    /**
     * Map 签名（包含排序功能，Map 将转化为 query string 格式）
     */
    public String sign(Map<String, String> params) {
        return SignUtils.sign(params, privateKey, charset);

    }

    /**
     * 字符串验签
     */
    public boolean check(String content, String sign, String publicKey) {
        return SignUtils.check(content, sign, publicKey, charset);
    }

    /**
     * 字符串验签（使用初始公钥）
     */
    public boolean check(String content, String sign) {
        return SignUtils.check(content, sign, otherPublicKey, charset);
    }

    /**
     * Map 验签（Map 中包含 sign 信息）
     */
    public boolean check(Map<String, String> params, String publicKey) {
        return SignUtils.check(params, publicKey, charset);
    }

    /**
     * Map 验签（Map 中包含 sign 信息，使用初始公钥）
     */
    public boolean check(Map<String, String> params) {
        return SignUtils.check(params, otherPublicKey, charset);
    }

    /**
     * 使用 HttpServletRequest.getParameterMap() 数据验签
     */
    public boolean checkParameterMap(Map<String, String[]> parameterMap, String publicKey) {
        return SignUtils.checkParameterMap(parameterMap, publicKey, charset);
    }

    /**
     * 使用 HttpServletRequest.getParameterMap() 数据验签（使用初始公钥）
     */
    public boolean checkParameterMap(Map<String, String[]> parameterMap) {
        return SignUtils.checkParameterMap(parameterMap, otherPublicKey, charset);
    }

    /**
     * json 对象字符串验签，格式：{"response":{...},"sign":"..."}
     */
    public boolean checkJson(String json, String publicKey) {
        return SignUtils.checkJson(json, publicKey, charset);
    }

    /**
     * son 对象字符串验签，格式：{"response":{...},"sign":"..."}（使用初始公钥）
     */
    public boolean checkJson(String json) {
        return SignUtils.checkJson(json, otherPublicKey, charset);
    }
}
