package ru.otus.crm.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<Phone> phones = new ArrayList<>();

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones.addAll(phones);
        for (Phone phone: phones) {
            phone.setClient(this);
        }
    }

    @Override
    public Client clone() {
        var newClient = new Client(this.id, this.name);
        var newAddress = new Address(this.address.getId(), this.address.getStreet());
        newClient.setAddress(newAddress);

        List<Phone> newPhones = this.phones.stream()
                .map(phone -> {
                    var newPhone = new Phone(phone.getId(), phone.getNumber());
                    newPhone.setClient(newClient);
                    return newPhone;
                })
                .toList();
        newClient.setPhones(newPhones);
        return newClient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
