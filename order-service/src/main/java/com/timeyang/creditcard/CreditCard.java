package com.timeyang.creditcard;

import com.timeyang.data.BaseEntityDto;

/**
 * @author chaokunyang
 */
public class CreditCard extends BaseEntityDto {

    private Long id;
    private String number;
    private CreditCardType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type=" + type +
                '}';
    }
}
