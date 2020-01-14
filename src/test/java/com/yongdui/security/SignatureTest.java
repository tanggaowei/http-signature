package com.yongdui.security;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Create by TangGaowei on 2020/1/14
 */
public class SignatureTest {
    private static String PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC/nVvLorSyfBUMA2kWsGcN7zbfdbmbEzwdFcsiFujDIuaMMD29Qw2I93EgzIbyd90Sf6Ixx3sgTBKdCJxtSj2rbyWklC/YGHCKXbROgzPN0V0tVztObcn1aoi+IGKH64bb8Foz441mLyLWWJGGkay2ctK754g6YFMADLYNA+4/0p2BLyacS+8vJZsesTrJeeeBROfCq5wWYc/YoV0FWgx5CWBN1vv8JY443933l9Dw9xqgKvfMp3LD4SmcEbGL9+pK1j390AkTbMysUAtL3k2+WGqEuGK+grCTXQ3ZxFhLccqLZlaGtmDr9Y+IMYh8TBzjVq4nXyeSClQ6itsWTCmNAgMBAAECggEAY4Ei90eiEIHxKBDpVLf8fDb6RubKoOZHNE9p8mT1MMiB8tDmbs9QSw08OS0c8vMd4YpYPwN5RBddHyEmonaIOet5H9M4WT6vyprIlHD7yOjtOytuh9bppsFRC3Kj2DOOD87r+MA1YJgfvjG2OKyThRdxqdSdYLs+vura6z1cTtV5NrqXpQqbwEITBqiA68uI/ljlW8ZWAsUVDuCo0q4UX6QK8Ya1HVU4ieyrpopohJ95MaorY2g/n70O6z9YkKxLAWh+D0Dx1adT/BSeSP3dbfD7UGCl+kI0w1bgE56KwrgtHQu/p2CwfvFMuCAzKT5LUqLRH+ASgTZDavyiQ28P4QKBgQDrGvg+feO4qV9xIiEwTH2rJzYAmperlc3WnnlPVsWz0I+LezPipbDxvx0PtuiOpi5xADKj3kBF5ZKoGGyLksW2tFFpj50AIi1SgA/Pl6O8aRxdb0V94kaPK1tNU+h8tiAVEtnwskFzRfNJFIQev1tlivElkejnAxKwdvavhruCGQKBgQDQpOe70uXyVety8CHUhbj5gPDatb59acUxJ4TPk6xyXuvsHU/NRCycTTDdaHvQlnD4oOpC2B4kklQFrtXHuo+ALD4kGVuA00VoSitZXdvYmz1hbxzWnyOQKGFJiM9zcAwu6ehdiDXGDB2VQBOJ4G8H3rm1L0uNxz/gl+mcp70ZlQKBgB6CtH6fpcLcMCelbzsuYgypHQf9B/vbiAf3LvUN5MBOK6LFTVl3eTwWOjZE3W9Nv68ogGnluN+ZSGwKJ2QBmyj3w6z4Vz6iMNGJDwQ4wKX92hGCFDme+NsxnDqvzfcJR6aUeLYU3AuSo1zzjaXT3Y+S9XKTue7o5i8CcUccLA9ZAoGAaBnPUaSBfK8n4do0VvA9gae3BLjniXWLoOHh3xwET7BSsCVT2svoIjhVaDMoi8N0LGlFWQKxz3d/GrLIEXZhAQeSHbrVL+q517Yh+N3ewi16q7Gfxunx+YxcNi9fqm4+de3ZNUmv4tfKrtwgo4tqepfDOsaVQPEoSuwg8Du1Gx0CgYBKT0bSsT/gE0IcAksFHL9kpj/ZMVY16Qcor/XbnwvGKgZ9AYv8auIFX30rsRjcOWoFIi18HMFO1V9b+UuEtF9CTxIfLJ7bCLCRgQG8aFgiMEGKy0bOdFJ+tsLuuVu00M0pQXwHlWU3cDUTKkdFqltwbXV3W1JZ93Qana9H/cnP+A==";
    private static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv51by6K0snwVDANpFrBnDe8233W5mxM8HRXLIhbowyLmjDA9vUMNiPdxIMyG8nfdEn+iMcd7IEwSnQicbUo9q28lpJQv2Bhwil20ToMzzdFdLVc7Tm3J9WqIviBih+uG2/BaM+ONZi8i1liRhpGstnLSu+eIOmBTAAy2DQPuP9KdgS8mnEvvLyWbHrE6yXnngUTnwqucFmHP2KFdBVoMeQlgTdb7/CWOON/d95fQ8PcaoCr3zKdyw+EpnBGxi/fqStY9/dAJE2zMrFALS95NvlhqhLhivoKwk10N2cRYS3HKi2ZWhrZg6/WPiDGIfEwc41auJ18nkgpUOorbFkwpjQIDAQAB";
    private static String SIGN = "P/1UuZoa/J9GHRpd8ocF2ftLyjjNTWyEwixn1UKOJu6ugHFVVsYUC9vwVeSfVQwmHQeSpnNVqz2SajfjG8CYFAKM3+5rAKAZPqnhL9pf4evPHg3fkuGimugKR7yiycZsGu2/x3+idbE0IAp0b9kmUKZbS6/3FvPRGaIXMGhtBeAr8xOhXyazzMUbgw0TNYlrRiNB+zgeXFF0o8k5CYoBptDpSKgEQAHaL4l+WYU8LPY58RcOw8+aJYTuHvlSlPVFqKqjI908CV8cPVUFvfm7fZZVhA41E3xU986PB0XAPS0PdCc2p+vG2LBLKTwWeWHt0rxHjVomUsXLqREEvlV7mg==";
    private static String CONTENT = "a=1&b=2&sign_type=RSA2";
    private static String JSON_CONTENT = "{\"sign\":\"r9f7HKoaUFSUmnsYVJE5marErf74sXv1dab+h96JvOO3EpE964zpBYjffua1tSIFALH1UJPydKFfHUUF4efmYIbveoLjYgc4bCQBEZIXhrU+ZzModDJX0ruhH9ds5EueFu3T/078RDXhD/i710TEzJ0YPpAnHZTd2f2GJ3HiqbRrQhgVUEguAva6wS8wwfqpNQeSl8IDFR6OxlTlkWyqrC8OoO/4uMkM4f2kNOBrXg9Kk4f2kpLohlkoSw73x88/OAoyLhUl8/Wkvg25wGP1Y7GPwUTt81CD7MIID6ALUDAgHnYtN04M7Daiv61B0hl8ZnPu/eYMrlrQSXT0kRAbNQ==\",\"response\":{\"a\":\"1\",\"b\":\"2\",\"sign_type\":\"RSA2\"}}";
    private static Signature signature = new Signature(PRIVATE_KEY);

    /**
     * 私钥签名
     */
    @Test
    public void sign() {
        try {
            assertEquals("签名错误", signature.sign(CONTENT), SIGN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Map 签名
     */
    @Test
    public void signMap() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("a", "1");
            params.put("b", "2");
            params.put("sign_type", "RSA2");
            assertEquals("Map 签名", signature.sign(params), SIGN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公钥验签
     */
    @Test
    public void check() {
        try {
            assertTrue("验签失败", signature.check(CONTENT, SIGN, PUBLIC_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Json 返回值验签
     */
    @Test
    public void checkJson() {
        try {
            assertTrue("JSON 验签失败", signature.checkJson(JSON_CONTENT, PUBLIC_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}