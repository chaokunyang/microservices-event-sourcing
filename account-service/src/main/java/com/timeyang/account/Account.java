package com.timeyang.account;

import com.timeyang.address.Address;
import com.timeyang.creditcard.CreditCard;
import com.timeyang.data.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chaokunyang
 */
@Entity
public class Account extends BaseEntity {
    private Long id;
    /**
     * 用户唯一标识。如登录用的用户名。ps: 用户名作为userId是合理的，因为登录时使用的用户名是唯一的
     */
    private String userId;
    /**
     * 账户号，即账号
     */
    @Column(unique = true)
    private String accountNumber;
    private Boolean defaultAccount;
    private Set<CreditCard> creditCards;
    private Set<Address> addresses;

    public Account() {
    }

    public Account(String userId, String accountNumber) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.creditCards = new HashSet<>();
        this.addresses = new HashSet<>();
        this.defaultAccount = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Boolean getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(Boolean defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", defaultAccount=" + defaultAccount +
                ", creditCards=" + creditCards +
                ", addresses=" + addresses +
                '}';
    }
}
