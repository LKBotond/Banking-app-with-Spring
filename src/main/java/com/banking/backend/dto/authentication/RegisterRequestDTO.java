package com.banking.backend.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data transfer object for registration requests.
 * <p>
 * Contains:
 * </p>
 * <ul>
 * <li><b>email</b> the user's email address ({@code String})</li>
 * <li><b>password</b> the user's password ({@code char[]})</li>
 * <li><b>password</b> the user's name ({@code String})</li>
 * <li><b>password</b> the user's family name ({@code String})</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class RegisterRequestDTO {
    String email;
    char[] password;
    String firstName;
    String lastName;
    
}
