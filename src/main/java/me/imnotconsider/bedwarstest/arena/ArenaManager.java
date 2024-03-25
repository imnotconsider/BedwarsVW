package me.imnotconsider.bedwarstest.arena;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
@Log4j2
public class ArenaManager {
    private final Arena arena;

    public ArenaManager(BedwarsTestPlugin plugin) {
        File file = new File(plugin.getDataFolder() + "\\arena.yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configurationSection = arenaConfig.getConfigurationSection("game");

        arena = new Arena(configurationSection);
        log.info("Арена создана.");
    }
}
