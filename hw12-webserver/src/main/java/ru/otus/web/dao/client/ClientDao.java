package ru.otus.web.dao.client;

import ru.otus.model.Client;

import java.util.List;

public interface ClientDao {

    void addClient(Client client);

    List<Client> findAll();
}
