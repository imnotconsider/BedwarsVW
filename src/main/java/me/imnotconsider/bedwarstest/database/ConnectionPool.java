package me.imnotconsider.bedwarstest.database;

import lombok.extern.log4j.Log4j2;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Log4j2
public class ConnectionPool {
    private final String url;
    private final String username;
    private final String password;
    private static final int POOL_SIZE = 10;
    private final BlockingQueue<PGSimpleDataSource> connections = new ArrayBlockingQueue<>(POOL_SIZE);

    public ConnectionPool(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        initializePool();
    }

    private void initializePool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            PGSimpleDataSource connection = new PGSimpleDataSource();
            connection.setUrl(url);
            connection.setUser(username);
            connection.setPassword(password);
            connections.add(connection);
            log.info("pool initialized ");
        }
    }

    public PGSimpleDataSource getConnection() throws InterruptedException {
        if (connections.isEmpty()) {
            log.warn("connection pool is empty");
            return null;
        }
        return connections.take();
    }

    public void releaseConnection(PGSimpleDataSource connection) {
        try {
            connections.put(connection);
            log.info("1 connection received. total connections: {}", connections.size());
        } catch (InterruptedException e) {
            log.warn("log put error: {}", e.getMessage());
        }
    }
}