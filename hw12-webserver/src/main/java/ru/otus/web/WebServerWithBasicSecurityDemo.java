package ru.otus.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.database.core.repository.DataTemplateHibernate;
import ru.otus.database.core.repository.HibernateUtils;
import ru.otus.database.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.database.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.database.crm.model.Address;
import ru.otus.database.crm.model.Client;
import ru.otus.database.crm.model.Phone;
import ru.otus.database.crm.service.DbServiceClientImpl;
import ru.otus.web.dao.client.ClientDao;
import ru.otus.web.dao.client.InMemoryClientDao;
import ru.otus.web.helpers.FileSystemHelper;
import ru.otus.web.server.client.ClientWebServer;
import ru.otus.web.server.client.ClientsWebServerWithBasicSecurity;
import ru.otus.web.services.TemplateProcessor;
import ru.otus.web.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";
    private static final Logger log = LoggerFactory.getLogger(WebServerWithBasicSecurityDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        ClientDao clientDao = new InMemoryClientDao(dbServiceClient);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        //LoginService loginService = new InMemoryLoginServiceImpl(userDao);

        ClientWebServer clientWebServer = new ClientsWebServerWithBasicSecurity(
                WEB_SERVER_PORT, loginService, clientDao, gson, templateProcessor);

        clientWebServer.start();
        clientWebServer.join();
    }
}
