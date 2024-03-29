package me.imnotconsider.bedwarstest.user;

import java.util.UUID;

public class DefaultElements {
    public static User createNewUser(UUID uuid) {
        return new User(uuid, 0, 0, 0);
    }
}
