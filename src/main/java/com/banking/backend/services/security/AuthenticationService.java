package com.banking.backend.services.security;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class AuthenticationService {
    // Argon2 params
    private static final int ITERATIONS = 4;
    private static final int MEMORY = 131072;
    private static final int PARALLELISM = 2;

    private final Argon2 argon2;

    public AuthenticationService() {
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    public String hashWithArgon2(char[] passArray) {
        return this.argon2.hash(ITERATIONS, MEMORY, PARALLELISM, passArray);
    }

    public boolean verifyPass(String hashOnRecord, char[] passArray) {
        return this.argon2.verify(hashOnRecord, passArray);
    }

    public void wipeSensitiveMemory(Object... arrays) {
        for (Object array : arrays) {
            if (array instanceof char[] chars) {
                Arrays.fill(chars, '\0');
            } else if (array instanceof byte[] bytes) {
                Arrays.fill(bytes, (byte) 0);
            } else if (array instanceof int[] ints) {
                Arrays.fill(ints, 0);
            } else if (array instanceof long[] longs) {
                Arrays.fill(longs, 0L);
            } else if (array instanceof Object[] objs) {
                Arrays.fill(objs, null);
            } else {
                throw new IllegalArgumentException("Unsupported array type: " + array.getClass());
            }
        }
    }
}
