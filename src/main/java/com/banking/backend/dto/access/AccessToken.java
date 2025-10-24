package com.banking.backend.dto.access;

import java.util.ArrayList;

import com.banking.backend.domain.accounts.Account;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data transfer object used as a response for login/register requests.
 * <p>
 * Contains:
 * </p>
 * <ul>
 * <li><b>email</b> the session token ({@code String})</li>
 * <li><b>email</b> the user's name ({@code String})</li>
 * <li><b>email</b> the user's family name ({@code String})</li>
 * <li><b>password</b> the user's accounts ({@code ArrayList<Account>})</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class AccessToken {
    String SessionToken;
    String name;
    String familyName;
    ArrayList<Account> accounts;
}
