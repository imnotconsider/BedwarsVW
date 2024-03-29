package me.imnotconsider.bedwarstest.gameplay.waiting;

import lombok.Data;
import me.imnotconsider.bedwarstest.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Data
public class Waiting {
    private final ArrayList<Player> players;
    private final Location location;

    public Waiting(ConfigurationSection configurationSection) {
        this.players = new ArrayList<>();
        this.location = LocationUtil.getLocation(configurationSection.getString("location"));
    }

    public void addPlayerToWaiting(Player player) {
        players.add(player);
    }

    public void clear() {
        players.clear();
    }

    public void remove(Player player) {
        players.remove(player);
    }
}
