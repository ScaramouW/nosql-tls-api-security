# 🔐 Encrypted NoSQL Storage & TLS Network Transport Security

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Security: AES-256-GCM / TLS 1.3](https://img.shields.io/badge/Security-AES--256--GCM_%7C_TLS_1.3-brightgreen.svg)]()

## 📌 Project Overview
This repository contains reference code and security patterns for securing **REST API network transport (HTTPS/TLS 1.3)** and enforcing **authenticated Data-at-Rest encryption (AES-256-GCM)** over local NoSQL/JSON data stores.

---

## 🛠️ Key Security Mechanisms
1. **HTTPS/TLS 1.3 Enforcement**: SSL Pinning & SHA-256 certificate fingerprint validation.
2. **Payload Integrity Signing**: HMAC-SHA256 request payload verification preventing tamper/injection attacks.
3. **Data-at-Rest Protection**: Local database encryption with key derivation (PBKDF2) and AES-256-GCM authenticated encryption mode.
4. **Session Token Hardening**: Secure memory storage for auth tokens avoiding plain-text leakage in shared preferences or local logs.

---

## 📁 Repository Structure
```
nosql-tls-api-security/
├── README.md
├── src/
│   └── EncryptedNoSqlStore.kt     # Authenticated AES-256-GCM local storage wrapper
└── .gitignore
```

---

## 👤 Author
- **Author:** Fahd BELHIBA
- **GitHub:** [@ScaramouW](https://github.com/ScaramouW)
