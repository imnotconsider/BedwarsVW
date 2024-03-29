package me.imnotconsider.bedwarstest.user;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
public class UserManager {
    @Getter
    Map<UUID, User> users;
    final DatabaseConnection databaseConnection;

    public UserManager(DatabaseConnection databaseConnection) {
        users = new HashMap<>();
        this.databaseConnection = databaseConnection;
    }

    public void loadUser(UUID uuid) throws SQLException, InterruptedException { // загружает данные пользователя из бд
        String query = "SELECT * FROM users WHERE uuid = ?";
        ResultSet resultSet = databaseConnection.query(query, uuid.toString());
        User user;
        if (resultSet.next()) {
            user = rsToUser(resultSet);
        } else {
            user = createNewUser(uuid);
        }
        users.put(uuid, user);
    }

    public void saveUser(User user) { // сохраняет данные пользователя в память
        users.put(user.getUuid(), user);
    }

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    private User rsToUser(ResultSet rs) throws SQLException {
        return User.builder()
                .uuid(UUID.fromString(rs.getString("uuid")))
                .kills(rs.getInt("kills"))
                .deaths(rs.getInt("deaths"))
                .brokenBeds(rs.getInt("broken_beds"))
                .build();
    }

    private User createNewUser(UUID uuid) throws SQLException, InterruptedException {
        User user = DefaultElements.createNewUser(uuid);
        String query = "INSERT INTO users(uuid, kills, deaths, broken_beds) VALUES (?, ?, ?, ?)";
        databaseConnection.query(query, uuid.toString(), user.getKills(), user.getDeaths(), user.getBrokenBeds());
        return user;
    }

    public void unloadUser(UUID uuid) throws SQLException, InterruptedException { // сохраняет данные пользователя в бд
        User user = users.get(uuid);
        String query = "UPDATE users SET kills = ?, deaths = ?, broken_beds = ? WHERE uuid = ?";
        databaseConnection.query(query, user.getKills(), user.getDeaths(), user.getBrokenBeds(), user.getUuid().toString());
        users.remove(uuid);
    }
}
