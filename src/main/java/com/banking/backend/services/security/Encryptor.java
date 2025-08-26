package com.banking.backend.services.security;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class Encryptor {

    // AES-GCM param
    private static final int BITLENGTH = 128;

    Encryptor() {
    };

    public String encryptWithAESGCM(String data, SecretKey key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcm_Spec = new GCMParameterSpec(BITLENGTH, iv);

            cipher.init(Cipher.ENCRYPT_MODE, key, gcm_Spec);

            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("AES-GCM encryption failed", e);
        }
    }

    public String decryptGCM(String encryptedData, byte[] iv, SecretKey key) {
        try {
            byte[] encrypted = Base64.getDecoder().decode(encryptedData);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(BITLENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (AEADBadTagException e) {
            throw new RuntimeException("Decryption failed: data may have been tampered with or key is incorrect.", e);
        } catch (Exception e) {
            throw new RuntimeException("AES-GCM decryption failed", e);
        }
    }

}
