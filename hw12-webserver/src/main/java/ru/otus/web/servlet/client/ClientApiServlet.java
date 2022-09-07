package ru.otus.web.servlet.client;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.database.crm.model.Client;
import ru.otus.web.dao.client.ClientDao;

import java.io.IOException;

public class ClientApiServlet extends HttpServlet {

    private final ClientDao clientDao;
    private final Gson gson;

    public ClientApiServlet(ClientDao clientDao, Gson gson) {
        this.clientDao = clientDao;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var body = request.getReader();
        var client = gson.fromJson(body, Client.class);
        clientDao.addClient(client);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }
}
