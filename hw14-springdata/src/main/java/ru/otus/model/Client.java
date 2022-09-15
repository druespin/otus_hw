package ru.otus.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("client")
public class Client implements Persistable<Long> {

    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @MappedCollection(idColumn = "client_id")
    private Address address;

    @NonNull
    @MappedCollection(idColumn = "client_id")
    private Phone phone;

    @Transient
    private boolean isNew;

    public Client() {
        this.isNew = true;
    }

    public Client(String name, String street, String number) {
        this(null, name, new Address(street), new Phone(number));
        this.isNew = true;
    }

    @PersistenceConstructor
    public Client(Long id, String name, Address address, Phone phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isNew = false;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public Phone getPhone() {
        return phone;
    }
    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
