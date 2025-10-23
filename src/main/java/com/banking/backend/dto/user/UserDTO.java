package com.banking.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.banking.backend.domain.accounts.Account;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String email;
    private String name;
    private ArrayList<Account> accounts;
}
