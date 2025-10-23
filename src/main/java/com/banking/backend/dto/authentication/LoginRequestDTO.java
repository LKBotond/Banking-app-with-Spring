package com.banking.backend.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for login requests.
 * <p>
 * Contains:
 * </p>
 * <ul>
 * <li><b>email</b> the user's email address ({@code String})</li>
 * <li><b>password</b> the user's password ({@code char[]})</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    String email;
    char[] password;
}
