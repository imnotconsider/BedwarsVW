package me.imnotconsider.bedwarstest.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
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

    public void addDeath() {
        deaths++;
    }

    public void addKill() {
        kills++;
    }

    public void addBrokenBeds() {
        brokenBeds++;
    }
}
