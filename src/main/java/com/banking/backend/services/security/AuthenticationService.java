package com.banking.backend.services.security;

import org.springframework.stereotype.Service;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class AuthenticationService {
    // Argon2-jvm params
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

    public void wipePass(char[] toBeWiped) {
        this.argon2.wipeArray(toBeWiped);
    }
}
