package com.timeyang.api.v1;

import com.timeyang.account.Account;
import com.timeyang.account.AccountRepository;
import com.timeyang.user.User;
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
            accounts = accountRepository.findAccountsByUserId(user.getUsername()); // 用户名作为userId是合理的，因为登录时使用的用户名是唯一的
        }

        // 掩盖信用卡除最后四位以外的数字
        if(accounts != null) {
            accounts.forEach(account -> account.getCreditCards()
                    .forEach(creditCard -> creditCard.setNumber(creditCard.getNumber().replaceAll("[\\d]{4}(?!$)", "****-"))));
        }
        return accounts;
    }
}
