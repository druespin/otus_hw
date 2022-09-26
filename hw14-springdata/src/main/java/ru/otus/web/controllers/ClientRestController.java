package ru.otus.web.controllers;

import org.springframework.web.bind.annotation.*;
import ru.otus.model.Client;
import ru.otus.web.service.ClientService;

import java.util.List;

@RestController
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/clients")
    public List<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/api/client/{id}")
    public Client getClientById(@PathVariable(name = "id") long id) {
        return clientService.findById(id);
    }

    @GetMapping("/api/client")
    public Client getClientByName(@RequestParam(name = "name") String name) {
        return clientService.findByName(name);
    }

    @PostMapping("/client/api/client/save")
    public Client saveClient(@RequestBody Client client) {
        return clientService.save(client);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/client/random")
    public Client findRandomClient() {
        return clientService.findRandom();
    }

}
