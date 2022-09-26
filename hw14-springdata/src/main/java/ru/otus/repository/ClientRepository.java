package ru.otus.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.model.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Iterable<Client> findAll();

    @Query("select * from client where name = :name")
    Optional<Client> findByName(@Param("name") String name);

    @Query("select * from client where id = :id")
    Optional<Client> findById(@Param("id") Long id);

    @Modifying
    Client save(Client client);
}