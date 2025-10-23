package com.banking.backend.services.session;

import com.banking.backend.dto.access.AccessToken;

public interface SessionService {

    public boolean validateSession(String sessionToken);

    public void createSessionToken(AccessToken incompleteAccessToken, long loginId);

    public void endSession(String sessionToken);

}
