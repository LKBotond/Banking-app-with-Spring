package com.banking.backend.services.security;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.SecureRandom;
import java.util.Arrays;

public class Argon2KDF {

    private static final int ITERATIONS = 10;
    private static final int MEMORY_KiB = 65536;
    private static final int PARALLELISM = 1;
    private static final int KEY_LENGTH_IN_BYTES = 32;

    public Argon2KDF() {
    };

    public char[] charArrayify(Object input) {
        if (input == null) {
            return new char[0];
        }
        return input.toString().toCharArray();
    }

    public byte[] getRandom(int size) {
        byte[] salt = new byte[size];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public SecretKey deriveKey(char[] passArray, byte[] salt) {

        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13)
                .withIterations(ITERATIONS)
                .withMemoryAsKB(MEMORY_KiB)
                .withParallelism(PARALLELISM)
                .withSalt(salt);

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());

        byte[] keyBytes = new byte[KEY_LENGTH_IN_BYTES];

        generator.generateBytes(passArray, keyBytes);

        SecretKey derivedKey = new SecretKeySpec(keyBytes, "AES");

        Arrays.fill(keyBytes, (byte) 0);

        return derivedKey;
    }
}
