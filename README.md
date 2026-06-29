<div align="center">

# CipherKit

**对称加解密 RESTful 服务 —— SM4 / AES，支持 ECB / CBC / CTR。**

[前端 UI](https://gitee.com/gzzzxx/gzzz-tool-show) · [Issue](https://gitee.com/gzzzxx/cipherkit/issues)

![Java](https://img.shields.io/badge/Java-1.8+-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.0-brightgreen?style=flat-square)
![License](https://img.shields.io/badge/license-Apache%202.0-blue?style=flat-square)

</div>

---

## ✨ 特性

- **SM4**：ECB / CBC / CTR × NoPadding / PKCS5Padding / PKCS7Padding
- **AES**：ECB / CBC × PKCS5Padding / PKCS7Padding
- **数据格式**：明文 / 密钥 / IV 均支持 `TEXT` / `HEX` / `BASE64` 三种输入
- **密钥长度**：固定 128 位
- **加密输出**：统一 Base64
- **统一响应** `Result<T>` + 全局异常处理
- **接口幂等**（`@Repeat`） + **操作日志**（`@Log`）

---

## 🧩 算法矩阵

| 算法 | 模式 | 填充 | 需 IV |
| :--: | :--: | :-- | :--: |
| SM4  | ECB  | PKCS5Padding   | ❌ |
| SM4  | ECB  | PKCS7Padding   | ❌ |
| SM4  | CBC  | PKCS5Padding   | ✅ |
| SM4  | CBC  | PKCS7Padding   | ✅ |
| SM4  | CTR  | NoPadding      | ✅ |
| AES  | ECB  | PKCS5Padding   | ❌ |
| AES  | ECB  | PKCS7Padding   | ❌ |
| AES  | CBC  | PKCS5Padding   | ✅ |
| AES  | CBC  | PKCS7Padding   | ✅ |

---

## 🚀 快速开始

**环境**：JDK 1.8+ / Maven 3.6+

```bash
git clone https://gitee.com/gzzzxx/cipherkit.git
cd cipherkit

# 开发期
mvn spring-boot:run

# 打包运行
mvn clean package -DskipTests
java -jar target/cipherkit-0.0.1-SNAPSHOT.jar
```

启动后默认监听 `http://localhost:8080`。

**Docker**：

```bash
mvn clean package -DskipTests
docker build -t cipherkit .
docker run -d -p 8080:8080 cipherkit
```

---

## 📡 API 速览

> 统一响应：`{ "success": true, "code": 200, "msg": "操作成功", "data": { ... } }`
> 状态码：`200` 成功 / `500` 失败 / `701` 重复提交

### 加密

```http
POST /sm4/encrypt
```

| 字段 | 必填 | 说明 |
| :-- | :--: | :-- |
| `algorithmName` | ✅ | `SM4` / `AES` |
| `mode`          | ✅ | `ECB` / `CBC` / `CTR` |
| `iv`            | ⚠️ | CBC/CTR 必填，16 字节 |
| `key`           | ✅ | 16 字节（128 位） |
| `keyType`       | ✅ | `TEXT` / `HEX` / `BASE64` |
| `data`          | ✅ | 待加密明文 |
| `dataType`      | ✅ | `TEXT` / `HEX` / `BASE64` |

**示例**：

```bash
curl -X POST http://localhost:8080/sm4/encrypt \
  -H "Content-Type: application/json" \
  -d '{
    "algorithmName": "SM4",
    "mode": "CBC",
    "iv": "fedcba98765432100123456789abcdef",
    "key": "0123456789abcdeffedcba9876543210",
    "keyType": "HEX",
    "data": "Hello, CipherKit!",
    "dataType": "TEXT"
  }'
```

```json
{ "success": true, "code": 200, "msg": "操作成功",
  "data": { "data": "uZ6S2xKQv3YpZ4n7sXh9YQ==" } }
```

### 解密

```http
POST /sm4/decrypt
```

请求结构同上，`data` 传加密结果的 Base64，`dataType` 传 `BASE64`。

---

## 🗂️ 更新日志

**v0.0.1-SNAPSHOT**

- [x] SM4 / AES 加解密（9 种组合）
- [x] 多数据格式输入（TEXT / HEX / BASE64）
- [x] 统一响应 / 全局异常
- [x] `@Repeat` 幂等 / `@Log` 日志
- [ ] 非对称算法（RSA / SM2）
- [ ] 摘要算法（MD5 / SHA / SM3）
- [ ] 签名验签
- [ ] 接口鉴权

---

## 🤝 贡献

Fork → 新建分支 → 提交 → PR。提交前请确保 `mvn clean package` 通过。

---

## 📄 协议

[Apache License 2.0](LICENSE) · Copyright (c) 2025 gzzzxx

---

## 🙏 致谢

[Spring Boot](https://spring.io/projects/spring-boot) · [Hutool](https://hutool.cn) · [Bouncy Castle](https://www.bouncycastle.org) · [Lombok](https://projectlombok.org)

---

<div align="center">⭐ Star 一波，就是最大的支持！</div>
