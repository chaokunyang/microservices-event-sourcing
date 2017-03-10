package com.elasticjee.api.v1;

import com.elasticjee.account.Account;
import com.elasticjee.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chaokunyang
 */
@Service
public class AccountServiceV1 {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OAuth2RestTemplate restTemplate;

    public List<Account> getUserAccount() {
        List<Account> accounts = null;
        return accounts;
    }
}
