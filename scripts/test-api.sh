#!/usr/bin/env bash
# =============================================================================
# CipherKit 接口测试脚本
# 用法：bash scripts/test-api.sh [BASE_URL]
# 默认 BASE_URL=http://localhost:8080
# 依赖：bash / curl / python3 / jq
# =============================================================================
set -u

BASE_URL="${1:-http://localhost:8080}"
PASS=0
FAIL=0

# ---------- 工具函数 ----------
hr()    { printf '\n%s\n' "------------------------------------------------------------"; }
title() { hr; printf '【%s】\n' "$1"; hr; }

# hex 编码：传入字符串，输出 hex 字符串
to_hex() { python3 -c 'import sys; print(sys.argv[1].encode().hex())' "$1"; }
# base64 编码
to_b64() { python3 -c 'import sys,base64; print(base64.b64encode(sys.argv[1].encode()).decode())' "$1"; }

# 用 jq 美化输出，失败时打印原始响应
pretty() {
  local body="$1"
  if echo "$body" | jq -e . >/dev/null 2>&1; then
    echo "$body" | jq .
  else
    echo "$body"
  fi
}

# 通用调用：METHOD PATH JSON_BODY  -> 退出码 0/1
call() {
  local method="$1" path="$2" body="$3"
  local resp
  resp=$(curl -sS -X "$method" "$BASE_URL$path" \
         -H "Content-Type: application/json" \
         -d "$body")
  pretty "$resp"
  if echo "$resp" | jq -e '.success == true' >/dev/null 2>&1; then
    PASS=$((PASS+1))
    return 0
  else
    FAIL=$((FAIL+1))
    return 1
  fi
}

# ---------- 基础数据 ----------
PLAINTEXT="Hello, CipherKit"               # 16 字节
KEY="0123456789abcdef"                     # 16 字节
IV="fedcba9876543210"                      # 16 字节

# 预计算编码形式
KEY_HEX=$(to_hex "$KEY")
KEY_B64=$(to_b64 "$KEY")
IV_HEX=$(to_hex "$IV")
IV_B64=$(to_b64 "$IV")
PT_HEX=$(to_hex "$PLAINTEXT")
PT_B64=$(to_b64 "$PLAINTEXT")

printf 'BASE_URL = %s\n' "$BASE_URL"
printf '\n基础数据：\n'
printf '  明文  : %s\n' "$PLAINTEXT"
printf '  Key   : %s (HEX=%s, B64=%s)\n' "$KEY" "$KEY_HEX" "$KEY_B64"
printf '  IV    : %s (HEX=%s, B64=%s)\n' "$IV" "$IV_HEX" "$IV_B64"

# =============================================================================
# 一、SM4 算法
# =============================================================================

# ---------- 1. SM4 / ECB / PKCS5Padding (TEXT) ----------
title "1. SM4 + ECB + PKCS5Padding (TEXT 模式)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "SM4",
  "mode": "ECB",
  "key": "$KEY",
  "keyType": "TEXT",
  "data": "$PLAINTEXT",
  "dataType": "TEXT"
}
EOF
)"

# ---------- 2. SM4 / ECB / PKCS7Padding (HEX 模式) ----------
title "2. SM4 + ECB + PKCS7Padding (HEX 模式)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "SM4",
  "mode": "ECB",
  "key": "$KEY_HEX",
  "keyType": "HEX",
  "data": "$PT_HEX",
  "dataType": "HEX"
}
EOF
)"

# ---------- 3. SM4 / CBC / PKCS5Padding (TEXT + TEXT) ----------
title "3. SM4 + CBC + PKCS5Padding (TEXT 明文 + TEXT Key/IV)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "SM4",
  "mode": "CBC",
  "key": "$KEY",
  "keyType": "TEXT",
  "iv": "$IV",
  "data": "$PLAINTEXT",
  "dataType": "TEXT"
}
EOF
)"

# ---------- 4. SM4 / CBC / PKCS7Padding (HEX) ----------
title "4. SM4 + CBC + PKCS7Padding (HEX 模式)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "SM4",
  "mode": "CBC",
  "key": "$KEY_HEX",
  "keyType": "HEX",
  "iv": "$IV_HEX",
  "data": "$PT_HEX",
  "dataType": "HEX"
}
EOF
)"

# ---------- 5. SM4 / CBC / PKCS7Padding (BASE64) ----------
title "5. SM4 + CBC + PKCS7Padding (BASE64 模式)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "SM4",
  "mode": "CBC",
  "key": "$KEY_B64",
  "keyType": "BASE64",
  "iv": "$IV_B64",
  "data": "$PT_B64",
  "dataType": "BASE64"
}
EOF
)"

# ---------- 6. SM4 / CTR / NoPadding (HEX) ----------
title "6. SM4 + CTR + NoPadding (HEX 模式，输入必须是 16 字节倍数)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "SM4",
  "mode": "CTR",
  "key": "$KEY_HEX",
  "keyType": "HEX",
  "iv": "$IV_HEX",
  "data": "$PT_HEX",
  "dataType": "HEX"
}
EOF
)"

# =============================================================================
# 二、AES 算法
# =============================================================================

# ---------- 7. AES / ECB / PKCS5Padding ----------
title "7. AES + ECB + PKCS5Padding (TEXT 模式)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "AES",
  "mode": "ECB",
  "key": "$KEY",
  "keyType": "TEXT",
  "data": "$PLAINTEXT",
  "dataType": "TEXT"
}
EOF
)"

# ---------- 8. AES / ECB / PKCS7Padding ----------
title "8. AES + ECB + PKCS7Padding (HEX 模式)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "AES",
  "mode": "ECB",
  "key": "$KEY_HEX",
  "keyType": "HEX",
  "data": "$PT_HEX",
  "dataType": "HEX"
}
EOF
)"

# ---------- 9. AES / CBC / PKCS5Padding ----------
title "9. AES + CBC + PKCS5Padding (TEXT 模式)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "AES",
  "mode": "CBC",
  "key": "$KEY",
  "keyType": "TEXT",
  "iv": "$IV",
  "data": "$PLAINTEXT",
  "dataType": "TEXT"
}
EOF
)"

# ---------- 10. AES / CBC / PKCS7Padding (HEX) ----------
title "10. AES + CBC + PKCS7Padding (HEX 模式)"
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "AES",
  "mode": "CBC",
  "key": "$KEY_HEX",
  "keyType": "HEX",
  "iv": "$IV_HEX",
  "data": "$PT_HEX",
  "dataType": "HEX"
}
EOF
)"

# =============================================================================
# 三、加密 -> 解密 闭环验证
# =============================================================================

# ---------- 11. 闭环：SM4-CBC-PKCS7Padding (TEXT) ----------
title "11. 闭环验证：SM4 + CBC + PKCS7Padding (TEXT)"
CIPHERTEXT_B64=$(curl -sS -X POST "$BASE_URL/sm4/encrypt" \
  -H "Content-Type: application/json" \
  -d "$(cat <<EOF
{
  "algorithmName": "SM4",
  "mode": "CBC",
  "key": "$KEY",
  "keyType": "TEXT",
  "iv": "$IV",
  "data": "$PLAINTEXT",
  "dataType": "TEXT"
}
EOF
)" | jq -r '.data.data')

printf '\n[STEP 1] 加密结果（Base64）：%s\n' "$CIPHERTEXT_B64"

call POST /sm4/decrypt "$(cat <<EOF
{
  "algorithmName": "SM4",
  "mode": "CBC",
  "key": "$KEY",
  "keyType": "TEXT",
  "iv": "$IV",
  "data": "$CIPHERTEXT_B64",
  "dataType": "BASE64"
}
EOF
)"

# ---------- 12. 闭环：AES-ECB-PKCS7Padding (HEX) ----------
title "12. 闭环验证：AES + ECB + PKCS7Padding (HEX)"
CIPHERTEXT_HEX=$(curl -sS -X POST "$BASE_URL/sm4/encrypt" \
  -H "Content-Type: application/json" \
  -d "$(cat <<EOF
{
  "algorithmName": "AES",
  "mode": "ECB",
  "key": "$KEY_HEX",
  "keyType": "HEX",
  "data": "$PT_HEX",
  "dataType": "HEX"
}
EOF
)" | jq -r '.data.data')

printf '\n[STEP 1] 加密结果（Hex）：%s\n' "$CIPHERTEXT_HEX"

call POST /sm4/decrypt "$(cat <<EOF
{
  "algorithmName": "AES",
  "mode": "ECB",
  "key": "$KEY_HEX",
  "keyType": "HEX",
  "data": "$CIPHERTEXT_HEX",
  "dataType": "HEX"
}
EOF
)"

# =============================================================================
# 四、错误用例（预期 success=false）
# =============================================================================

# ---------- 13. 密钥长度错误（key 只有 8 字节，期望 16）----------
title "13. 错误用例：密钥长度不足（8 字节 -> 应失败）"
printf '预期：success=false，msg 含「密钥长度必须为128位」\n\n'
call POST /sm4/encrypt '{
  "algorithmName": "SM4",
  "mode": "ECB",
  "key": "12345678",
  "keyType": "TEXT",
  "data": "Hello, CipherKit",
  "dataType": "TEXT"
}' || true

# ---------- 14. CBC 模式缺 IV ----------
title "14. 错误用例：CBC 模式缺 IV -> 应失败"
printf '预期：success=false，msg 含「CBC模式向量不能为空」\n\n'
call POST /sm4/encrypt '{
  "algorithmName": "SM4",
  "mode": "CBC",
  "key": "0123456789abcdef",
  "keyType": "TEXT",
  "data": "Hello, CipherKit",
  "dataType": "TEXT"
}' || true

# ---------- 15. 不支持的工作模式 ----------
title "15. 错误用例：不支持的 mode (CFB) -> 应失败"
printf '预期：success=false，msg 含「不支持」\n\n'
call POST /sm4/encrypt '{
  "algorithmName": "SM4",
  "mode": "CFB",
  "key": "0123456789abcdef",
  "keyType": "TEXT",
  "data": "Hello, CipherKit",
  "dataType": "TEXT"
}' || true

# ---------- 16. 不支持的算法（AES-CTR，枚举未定义）----------
title "16. 错误用例：AES + CTR (AES 不支持 CTR 模式) -> 应失败"
printf '预期：success=false\n\n'
call POST /sm4/encrypt "$(cat <<EOF
{
  "algorithmName": "AES",
  "mode": "CTR",
  "key": "$KEY",
  "keyType": "TEXT",
  "iv": "$IV",
  "data": "$PLAINTEXT",
  "dataType": "TEXT"
}
EOF
)" || true

# ---------- 17. 不支持的算法名 (DES) ----------
title "17. 错误用例：不支持的算法 DES -> 应失败"
printf '预期：success=false\n\n'
call POST /sm4/encrypt '{
  "algorithmName": "DES",
  "mode": "ECB",
  "key": "0123456789abcdef",
  "keyType": "TEXT",
  "data": "Hello, CipherKit",
  "dataType": "TEXT"
}' || true

# ---------- 18. 错误 HTTP Method (GET) ----------
title "18. 错误用例：GET 请求加密接口 -> 应失败"
printf '预期：success=false\n\n'
call GET /sm4/encrypt '{}' || true

# =============================================================================
# 汇总
# =============================================================================
hr
printf '结果汇总：%d 通过 / %d 失败\n' "$PASS" "$FAIL"
hr

[ "$FAIL" -eq 0 ] && exit 0 || exit 1
