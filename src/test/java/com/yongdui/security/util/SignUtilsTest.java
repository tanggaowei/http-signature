package com.yongdui.security.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Create by TangGaowei on 2017/12/7 0007.
 */
public class SignUtilsTest {
    private static String JSON_CONTENT = "{\"sign\":\"Lu1wuEEhfxVNjpy90T//6u+kqANVloJoUx/LAgwdtkyuafC/jUGbdfYfaIeUd1Kkab6iZDvnrmAhe46dKEZfkZeymaL7Up/6TgFtCDBgHh/XF2DX9TUIt0FZtCaJPBSk6qZQgq4ZOQF9WROI8eYsIlbjX9s7O77gpoSey07Hr9SkUEDtddhkjU/iQBP76qeivllQX8SVv3yu8H16cYHtvVfhMVjIZ4cCQWmpegk62wGlapcs3ACdjFkNzGodbIDlRRzXyU1Ex/9nybFDPA591yJk2uxBKxMY3NYRWA2R/Vxe0Jvxi3zfO1oF5b30Lf5k8G6QOvmaj1t9sH+ZEhQ5ZA==\",\"response\":{\"name\":\"tgw\",\"code\":\"0001\"}}";

    @Test
    public void getContentFromJson() {
        try {
            assertEquals("获取 Json 内容失败", SignUtils.getContentFromJson(JSON_CONTENT), "{\"name\":\"tgw\",\"code\":\"0001\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}