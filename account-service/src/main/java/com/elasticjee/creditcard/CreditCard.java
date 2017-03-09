package com.elasticjee.creditcard;

import com.elasticjee.data.BaseEntity;

import javax.persistence.*;

/**
 * @author chaokunyang
 * @create 2017/3/9
 */
@Entity
public class CreditCard extends BaseEntity {

    private Long id;
    private String number;
    @Enumerated(EnumType.STRING)
    private CreditCardType type;

    public CreditCard() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
