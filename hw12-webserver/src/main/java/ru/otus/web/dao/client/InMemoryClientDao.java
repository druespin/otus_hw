package ru.otus.web.dao.client;

import ru.otus.model.Client;
import ru.otus.web.service.DBServiceClient;

import java.util.List;

public class InMemoryClientDao implements ClientDao {

    private final DBServiceClient dbServiceClient;

    public InMemoryClientDao(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public void addClient(Client client) {
        dbServiceClient.saveClient(client);
    }

    @Override
    public List<Client> findAll() {
        return dbServiceClient.findAll();
    }
}
