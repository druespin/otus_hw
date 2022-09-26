package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("phone")
public class Phone {

    private Long clientId;

    @NonNull
    private String number;

    public Phone() {
    }

    public Phone(String number) {
        this(null, number);
    }

    @PersistenceConstructor
    public Phone(Long clientId, String number) {
        this.clientId = clientId;
        this.number = number;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) { this.number = number; }
}
