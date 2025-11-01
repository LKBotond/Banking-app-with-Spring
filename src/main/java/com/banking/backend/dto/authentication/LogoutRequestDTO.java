package com.banking.backend.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Data transfer object for logout requests.
 * <p>
 * Contains:
 * </p>
 * <ul>
 * <li><b>sessionId</b> session Id ({@code String})</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class LogoutRequestDTO {
    String sessionId;
}
