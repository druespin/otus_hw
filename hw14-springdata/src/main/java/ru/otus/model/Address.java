package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("address")
public class Address {

    private Long clientId;

    @NonNull
    private String street;

    public Address() {
    }

    public Address(String street) {
        this(null, street);
    }

    @PersistenceConstructor
    public Address(Long clientId, String street) {
        this.clientId = clientId;
        this.street = street;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) { this.street = street; }
}
