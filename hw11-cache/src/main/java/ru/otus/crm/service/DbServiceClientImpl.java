package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.cachehw.HwCache;
import ru.otus.cache.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> myCache;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, HwCache hwCache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.myCache = hwCache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);

            myCache.put(clientCloned.getId().toString(), clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        if (myCache.get(String.valueOf(id)) != null) {
            log.info("Getting client from cache with id = {}", id);
            return Optional.of(myCache.get(String.valueOf(id)));
        }
        else
            return transactionManager.doInReadOnlyTransaction(session -> {
                var clientOptional = clientDataTemplate.findById(session, id);
                log.info("client: {}", clientOptional);
                myCache.put(String.valueOf(id), clientOptional.get());
                return clientOptional;
            });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }
}
