# http-request-sign
HTTP request RSA 签名工具。

## 签名
示例 1:
```
String content = "a=1&b=2&sign_type=RSA2";
Signature signature = new Signature(PRIVATE_KEY);
String sign = signature.sign(content);
```

示例 2:
```
Signature signature = new Signature(PRIVATE_KEY);
Map<String, String> params = new HashMap<String, String>();
params.put("a", "1");
params.put("b", "2");
params.put("sign_type", "RSA2");
String sign = signature.sign(params);
```

## 验签

示例 1：
```
String content = "a=1&b=2&sign_type=RSA2";
Signature signature = new Signature(PRIVATE_KEY, PUBLIC_KEY);
signature.check(content, sign);
```

示例 2：
```
String json = "{\"sign\":\"Lu1wuEEhfxVNjpy90T...A==\",\"response\":{\"name\":\"tgw\",\"code\":\"0001\"}}";
Signature signature = new Signature(PRIVATE_KEY, PUBLIC_KEY);
signature.checkJson(json);
```

