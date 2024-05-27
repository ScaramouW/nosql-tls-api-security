#!/usr/bin/env python3
"""
AES-256-GCM Data-at-Rest Local Encryption Demo
Author: Fahd BELHIBA
Description: Authenticated encryption utility for protecting sensitive session tokens
and local NoSQL/JSON cache stores.
"""

import os
import base64

try:
    from cryptography.hazmat.primitives.ciphers.aead import AESGCM
    HAS_CRYPTO = True
except ImportError:
    HAS_CRYPTO = False

def mock_encrypt(plaintext: str) -> str:
    encoded = base64.b64encode(plaintext.encode('utf-8')).decode('utf-8')
    return f"AES256GCM_ENC:{encoded}"

if __name__ == "__main__":
    sensitive_token = '{"session_id": "usr_99812", "jwt": "eyJhbGciOiJIUzI1NiIsIn...", "role": "admin"}'

    print(f"[*] Sensitive Local Token: {sensitive_token}")
    if HAS_CRYPTO:
        key = AESGCM.generate_key(bit_length=256)
        aesgcm = AESGCM(key)
        nonce = os.urandom(12)
        ct = aesgcm.encrypt(nonce, sensitive_token.encode('utf-8'), None)
        encrypted_blob = base64.b64encode(nonce + ct).decode('utf-8')
        print(f"[✓] Encrypted Payload (AES-256-GCM): {encrypted_blob}")
    else:
        encrypted_blob = mock_encrypt(sensitive_token)
        print(f"[✓] Encrypted Payload (Demo Base64 Mock): {encrypted_blob}")
