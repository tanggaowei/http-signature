package com.yongdui.security.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Create by TangGaowei on 2017/12/7 0007.
 */
public class Base64Utils {
    public static String encode(byte[] binaryData) {
        BASE64Encoder encoder = new BASE64Encoder();
        String sign = encoder.encode(binaryData);
        return sign.replace("\r", "").replace("\n", "");
    }

    public static byte[] decode(String base64String) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            return decoder.decodeBuffer(base64String);
        } catch (Exception e) {
            return null;
        }
    }
}
