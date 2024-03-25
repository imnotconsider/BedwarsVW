package me.imnotconsider.bedwarstest.arena;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@Data
@Log4j2
public class Arena {
    private final World world;
    private final List<MaterialSpawner> spawners;
    private final int minPlayers;
    private final int maxPlayers;

    public Arena(ConfigurationSection configurationSection) {
        this.world = Bukkit.getWorld(configurationSection.getString("world", "world"));
        this.maxPlayers = configurationSection.getInt("max_players");
        this.minPlayers = configurationSection.getInt("min_players");
        this.spawners = new ArrayList<>();

        ConfigurationSection spawnersSection = configurationSection.getConfigurationSection("spawners");

        for (String spawnerSection : spawnersSection.getKeys(false)) {
            spawners.add(new MaterialSpawner(spawnersSection.getConfigurationSection(spawnerSection)));
        }

        log.info("Спавнеры ресурсов загружены.");
    }
}
