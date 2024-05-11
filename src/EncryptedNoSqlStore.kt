package com.belhiba.nosqlsec

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.security.SecureRandom
import android.util.Base64

/**
 * Encrypted NoSQL Local Storage Wrapper (AES-256-GCM)
 * Author: Fahd BELHIBA
 */
class EncryptedNoSqlStore(private val masterKeyBytes: ByteArray) {

    companion object {
        private const val AES_GCM_NOPADDING = "AES/GCM/NoPadding"
        private const val GCM_TAG_LENGTH_BITS = 128
        private const val GCM_NONCE_LENGTH_BYTES = 12
    }

    /**
     * Encrypts plaintext JSON payload for Data-at-Rest local storage.
     */
    fun encryptPayload(plaintext: String): String {
        val nonce = ByteArray(GCM_NONCE_LENGTH_BYTES)
        SecureRandom().nextBytes(nonce)

        val secretKey: SecretKey = SecretKeySpec(masterKeyBytes, "AES")
        val cipher = Cipher.getInstance(AES_GCM_NOPADDING)
        val spec = GCMParameterSpec(GCM_TAG_LENGTH_BITS, nonce)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

        val ciphertext = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
        val combined = nonce + ciphertext
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    /**
     * Decrypts local storage payload with authentication tag check.
     */
    fun decryptPayload(encodedBlob: String): String {
        val combined = Base64.decode(encodedBlob, Base64.DEFAULT)
        val nonce = combined.copyOfRange(0, GCM_NONCE_LENGTH_BYTES)
        val ciphertext = combined.copyOfRange(GCM_NONCE_LENGTH_BYTES, combined.size)

        val secretKey: SecretKey = SecretKeySpec(masterKeyBytes, "AES")
        val cipher = Cipher.getInstance(AES_GCM_NOPADDING)
        val spec = GCMParameterSpec(GCM_TAG_LENGTH_BITS, nonce)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val plaintextBytes = cipher.doFinal(ciphertext)
        return String(plaintextBytes, Charsets.UTF_8)
    }
}
