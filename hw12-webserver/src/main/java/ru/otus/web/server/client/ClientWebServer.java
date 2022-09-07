package ru.otus.web.server.client;

public interface ClientWebServer {

    void start() throws Exception;

    void join() throws Exception;

    void stop() throws Exception;
}
