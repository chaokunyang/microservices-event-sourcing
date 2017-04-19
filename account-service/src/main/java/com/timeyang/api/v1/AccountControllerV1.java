package com.timeyang.api.v1;

import com.timeyang.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author chaokunyang
 */
@RestController
@RequestMapping("v1")
public class AccountControllerV1 {
    private AccountServiceV1 accountService;

    @Autowired
    public AccountControllerV1(AccountServiceV1 accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "accounts", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getUserAccounts() throws Exception {
        return Optional.ofNullable(accountService.getUserAccount()).map(account -> new ResponseEntity<>(account, HttpStatus.OK)).orElseThrow(() -> new Exception("用户账户不存在"));
    }
}
