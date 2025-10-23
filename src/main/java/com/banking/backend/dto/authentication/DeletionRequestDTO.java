package com.banking.backend.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data transfer object for login requests.
 * <p>
 * Contains:
 * </p>
 * <ul>
 * <li><b>email</b> Session Id ({@code String})</li>
 * <li><b>password</b> the user's password ({@code char[]})</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class DeletionRequestDTO {
    String sessionId;
    char[] password;

}
