package ru.otus.web.service;


import ru.otus.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();
    Client findById(long id);
    Client findByName(String name);
    Client findRandom();
    Client save(Client client);
}
