package me.imnotconsider.bedwarstest.user;

import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.database.DatabaseConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
public class UserManager {
    Map<UUID, User> users;
    DatabaseConnection databaseConnection;

    public UserManager(DatabaseConnection databaseConnection) {
        users = new HashMap<>();
        this.databaseConnection = databaseConnection;
    }

    public User loadUser(UUID uuid) {
        User user = new User(uuid);
        String query = String.format("SELECT * FROM users LEFT JOIN stats ON users.uuid = stats.user_uuid WHERE users.uuid =%s;", uuid);
        return user;
    }
}
