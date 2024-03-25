package me.imnotconsider.bedwarstest.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private final UUID uuid;
    private int kills;
    private int deaths;
    private int brokenBeds;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.kills = 0;
        this.deaths = 0;
        this.brokenBeds = 0;
    }
}
