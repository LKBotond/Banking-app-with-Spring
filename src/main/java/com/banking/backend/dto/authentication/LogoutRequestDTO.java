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
 * <li><b>email</b> session Id ({@code String})</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequestDTO {
    String sessionId;
}
