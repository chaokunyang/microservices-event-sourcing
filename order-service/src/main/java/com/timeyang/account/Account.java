package com.timeyang.account;

import com.timeyang.address.Address;
import com.timeyang.creditcard.CreditCard;
import com.timeyang.data.BaseEntityDto;

import java.util.Set;

/**
 * @author chaokunyang
 */
public class Account extends BaseEntityDto {
    private Long id;
    private String userId;
    private String accountNumber;
    private Boolean defaultAccount;
    private Set<CreditCard> creditCards;
    private Set<Address> addresses;

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

    public Boolean isDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(Boolean defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

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
