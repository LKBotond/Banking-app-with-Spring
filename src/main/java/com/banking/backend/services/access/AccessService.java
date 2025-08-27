package com.banking.backend.services.access;

public interface AccessService {

    public boolean login(String email, char[] password);

    public void logout(long sessionID);

    public void register(String email, String fullName, char[] password);

    public void delete(long userID, char[] password);
}
