# CipherKit 接口 curl 速查

> 基于 `scripts/test-api.sh` 整理。每条命令都是**完整自包含**的——hex / base64 已固化，直接复制即可执行。
>
> 默认 `BASE_URL=http://localhost:8080`，批量替换为你自己的地址即可（macOS / Linux 通用）。

## 0. 基础数据

| 字段 | 值 | HEX | BASE64 |
| :-- | :-- | :-- | :-- |
| 明文 | `Hello, CipherKit` | `48656c6c6f2c204369706865724b6974` | `SGVsbG8sIENpcGhlcktpdA==` |
| Key (16B) | `0123456789abcdef` | `30313233343536373839616263646566` | `MDEyMzQ1Njc4OWFiY2RlZg==` |
| IV  (16B) | `fedcba9876543210` | `66656463626139383736353433323130` | `ZmVkY2JhOTg3NjU0MzIxMA==` |

请求体公共字段：

| 字段 | 说明 |
| :-- | :-- |
| `algorithmName` | `SM4` / `AES` |
| `mode` | `ECB` / `CBC` / `CTR` |
| `iv` | CBC/CTR 必填，ECB 留空 |
| `key` | 密钥 |
| `keyType` | `TEXT` / `HEX` / `BASE64` |
| `data` | 加/解密内容 |
| `dataType` | `TEXT` / `HEX` / `BASE64` |

响应统一结构：

```json
{ "success": true, "data": { "data": "<BASE64/HEX/原文>" }, "msg": null }
```

---

## 一、SM4 算法（6 条）

### 1. SM4 + ECB + PKCS5Padding（TEXT 模式）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "ECB",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

### 2. SM4 + ECB + PKCS7Padding（HEX 模式）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "ECB",
    "key": "30313233343536373839616263646566",
    "keyType": "HEX",
    "data": "48656c6c6f2c204369706865724b6974",
    "dataType": "HEX"
  }'
```

### 3. SM4 + CBC + PKCS5Padding（TEXT）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CBC",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "iv": "fedcba9876543210",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

### 4. SM4 + CBC + PKCS7Padding（HEX）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CBC",
    "key": "30313233343536373839616263646566",
    "keyType": "HEX",
    "iv": "66656463626139383736353433323130",
    "data": "48656c6c6f2c204369706865724b6974",
    "dataType": "HEX"
  }'
```

### 5. SM4 + CBC + PKCS7Padding（BASE64）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CBC",
    "key": "MDEyMzQ1Njc4OWFiY2RlZg==",
    "keyType": "BASE64",
    "iv": "ZmVkY2JhOTg3NjU0MzIxMA==",
    "data": "SGVsbG8sIENpcGhlcktpdA==",
    "dataType": "BASE64"
  }'
```

### 6. SM4 + CTR + NoPadding（HEX，输入需 16 字节倍数）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CTR",
    "key": "30313233343536373839616263646566",
    "keyType": "HEX",
    "iv": "66656463626139383736353433323130",
    "data": "48656c6c6f2c204369706865724b6974",
    "dataType": "HEX"
  }'
```

---

## 二、AES 算法（4 条）

### 7. AES + ECB + PKCS5Padding（TEXT）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "AES",
    "mode": "ECB",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

### 8. AES + ECB + PKCS7Padding（HEX）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "AES",
    "mode": "ECB",
    "key": "30313233343536373839616263646566",
    "keyType": "HEX",
    "data": "48656c6c6f2c204369706865724b6974",
    "dataType": "HEX"
  }'
```

### 9. AES + CBC + PKCS5Padding（TEXT）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "AES",
    "mode": "CBC",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "iv": "fedcba9876543210",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

### 10. AES + CBC + PKCS7Padding（HEX）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "AES",
    "mode": "CBC",
    "key": "30313233343536373839616263646566",
    "keyType": "HEX",
    "iv": "66656463626139383736353433323130",
    "data": "48656c6c6f2c204369706865724b6974",
    "dataType": "HEX"
  }'
```

---

## 三、加密 → 解密 闭环验证（2 条）

> 先跑加密，把响应里的 `data.data` 字段值粘到解密命令的 `data` 里。
> 加密输出的 data 编码格式 = 你请求时指定的 `dataType`。

### 11. 闭环：SM4 + CBC + PKCS7Padding（TEXT → BASE64 → TEXT）

**Step 1 · 加密**：

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CBC",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "iv": "fedcba9876543210",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

响应示例（BASE64 密文）：

```json
{ "success": true, "data": { "data": "XXXXXXXXX..." }, "msg": null }
```

**Step 2 · 解密**（把上面 `data.data` 的值粘到 `data` 字段）：

```bash
curl -X POST http://localhost:8080/sm4/decrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CBC",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "iv": "fedcba9876543210",
    "data": "<把上一步的 data.data 粘过来>",
    "dataType": "BASE64"
  }'
```

预期 `data.data` = `Hello, CipherKit`，闭环成功。

### 12. 闭环：AES + ECB + PKCS7Padding（HEX → HEX）

**Step 1 · 加密**：

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "AES",
    "mode": "ECB",
    "key": "30313233343536373839616263646566",
    "keyType": "HEX",
    "data": "48656c6c6f2c204369706865724b6974",
    "dataType": "HEX"
  }'
```

**Step 2 · 解密**（把 `data.data` 粘到下方 `data` 字段）：

```bash
curl -X POST http://localhost:8080/sm4/decrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "AES",
    "mode": "ECB",
    "key": "30313233343536373839616263646566",
    "keyType": "HEX",
    "data": "<把上一步的 data.data 粘过来>",
    "dataType": "HEX"
  }'
```

预期 `data.data` = `48656c6c6f2c204369706865724b6974`，闭环成功。

---

## 四、错误用例（6 条，预期 `success: false`）

### 13. 密钥长度不足（8 字节 → 应失败）

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "ECB",
    "key": "12345678",
    "keyType": "TEXT",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

预期：msg 含「密钥长度必须为128位」。

### 14. CBC 模式缺 IV → 应失败

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CBC",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

预期：msg 含「CBC模式向量不能为空」。

### 15. 不支持的 mode（CFB）→ 应失败

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CFB",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

预期：msg 含「不支持」。

### 16. AES + CTR（AES 不支持 CTR）→ 应失败

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "AES",
    "mode": "CTR",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "iv": "fedcba9876543210",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

预期：success=false。

### 17. 不支持的算法（DES）→ 应失败

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "DES",
    "mode": "ECB",
    "key": "0123456789abcdef",
    "keyType": "TEXT",
    "data": "Hello, CipherKit",
    "dataType": "TEXT"
  }'
```

预期：success=false。

### 18. 错误 HTTP Method（GET）→ 应失败

```bash
curl -X GET http://localhost:8080/sm4/encrypt
```

预期：405 或 success=false（具体看全局异常处理实现）。

---

## 附：批量替换 BASE_URL

macOS / Linux：

```bash
sed -i '' 's|http://localhost:8080|http://192.168.1.10:8080|g' docs/api-curl.md
# Linux 把 '' 去掉：sed -i 's|...|...|g' docs/api-curl.md
```

要直接把所有 curl 一行化丢终端跑：

```bash
grep '```bash' docs/api-curl.md -A 10000 | grep '^curl' | tr -d '\\\n'
```

> 不过闭环那两条里有占位符，批量执行时记得手动替换。
