package ru.otus.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;
import ru.otus.model.Client;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public ClientServiceImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("clientList:{}", clientList);
        return clientList;
    }

    @Override
    public Client findById(long id) {
        var clientOptional = clientRepository.findById(id);
        log.info("client: {}", clientOptional);
        return clientOptional.orElseThrow();
    }

    @Override
    public Client findByName(String name) {
        var clientOptional = clientRepository.findByName(name);
        log.info("client: {}", clientOptional);
        return clientOptional.orElseThrow();
    }

    @Override
    public Client save(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(
                    new Client(client.getName(), client.getAddress().getStreet(), client.getPhone().getNumber())
            );
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Client findRandom() {
        return null;
    }
}
