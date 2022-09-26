package ru.otus.sessionmanager;

import org.springframework.stereotype.Component;

@Component
public interface TransactionManager {

    <T> T doInTransaction(ru.otus.sessionmanager.TransactionAction<T> action);
}
