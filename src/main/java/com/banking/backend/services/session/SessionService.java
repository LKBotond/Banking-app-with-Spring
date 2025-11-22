package com.banking.backend.services.session;

import com.banking.backend.dto.access.AccessToken;

public interface SessionService {

    public void validateSession(String sessionToken);

    public void createSessionToken(AccessToken incompleteAccessToken, long loginId);

    public void endSession(String sessionToken);

    public Long getUserIdBySession(String sessionToken);

}
