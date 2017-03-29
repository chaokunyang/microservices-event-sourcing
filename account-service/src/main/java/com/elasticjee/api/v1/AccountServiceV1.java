package com.elasticjee.api.v1;

import com.elasticjee.account.Account;
import com.elasticjee.account.AccountRepository;
import com.elasticjee.user.User;
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
    private OAuth2RestTemplate oAuth2RestTemplate;

    public List<Account> getUserAccount() {
        List<Account> accounts = null;
        User user = oAuth2RestTemplate.getForObject("http://user-service/auth/v1/me", User.class);
        if(user != null) {
            accounts = accountRepository.findAccountsByUserId(user.getId());
        }

        // 掩盖信用卡除最后四位以外的数字
        if(accounts != null) {
            accounts.forEach(account -> account.getCreditCards()
                    .forEach(creditCard -> creditCard.setNumber(creditCard.getNumber().replaceAll("[\\d]{4}(?!$)", "****-"))));
        }
        return accounts;
    }
}
