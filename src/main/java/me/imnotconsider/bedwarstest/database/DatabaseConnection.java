package me.imnotconsider.bedwarstest.database;

import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

@Log4j2
public class DatabaseConnection {
    private final ConnectionPool connectionPool;
    private final BedwarsTestPlugin plugin;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL DataSource unable to load PostgreSQL JDBC Driver");
        }
    }

    public DatabaseConnection(BedwarsTestPlugin plugin) throws IOException {
        this.plugin = plugin;
        File file = new File(plugin.getDataFolder() + "\\config.yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configurationSection = arenaConfig.getConfigurationSection("database");

        connectionPool = new ConnectionPool("jdbc:postgresql://" + configurationSection.getString("url"),
                configurationSection.getString("username"),
                configurationSection.getString("password"));
        log.info("создан пул подключений");

        initializeDatabases();
        log.info("бд инициализирована");
    }

    public void executeQuery(String sqlQuery) {
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            if (conn != null) {
                conn.createStatement().execute(sqlQuery);
                log.info("sql query executed successfully: {}", sqlQuery);
            } else {
                log.warn("error: failed to get connection from the pool.");
            }
        } catch (SQLException | InterruptedException e) {
            log.error("error sqlQuery: {}", e.getMessage());
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
    }

    private void initializeDatabases() throws IOException { // похожая на спринг система
        BufferedReader reader = new BufferedReader(new FileReader(plugin.getDataFolder() + "\\schema.sql"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String[] queries = sb.toString().split(";");
        for (String query : queries) {
            executeQuery(query);
            log.info(query);
        }
    }
}
