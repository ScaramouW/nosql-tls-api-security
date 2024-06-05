"""
TLS Network Transport & Payload Signature Validator
Author: Fahd BELHIBA
Verifies SSL/TLS cipher suites and HMAC-SHA256 payload integrity.
"""
import hmac
import hashlib
import ssl
import socket

def verify_tls_cipher(hostname: str, port: int = 443) -> str:
    ctx = ssl.create_default_context()
    with socket.create_connection((hostname, port), timeout=5) as sock:
        with ctx.wrap_socket(sock, server_hostname=hostname) as ssock:
            cipher = ssock.cipher()
            return f"Cipher: {cipher[0]} | Version: {cipher[1]}"

def verify_payload_hmac(secret_key: bytes, payload: bytes, signature: str) -> bool:
    expected = hmac.new(secret_key, payload, hashlib.sha256).hexdigest()
    return hmac.compare_digest(expected, signature)
