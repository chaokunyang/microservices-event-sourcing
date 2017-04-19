package com.timeyang.config;

import com.timeyang.account.AccountRepository;
import com.timeyang.customer.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by chaokunyang on 2017/4/5.
 */
//@SpringBootTest(classes = {AccountServiceApplication.class})
@SpringBootTest
@RunWith(SpringRunner.class)
public class DatabaseInitializerTest {

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void test() {
        databaseInitializer.populate();
        System.out.println(accountRepository.findAll());
        System.out.println(customerRepository.findAll());
    }
}