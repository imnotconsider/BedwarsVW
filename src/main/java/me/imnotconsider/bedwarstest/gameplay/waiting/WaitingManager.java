package me.imnotconsider.bedwarstest.gameplay.waiting;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

@Log4j2
public class WaitingManager {
    @Getter
    private final Waiting waiting;
    private final GameManager gameManager;

    public WaitingManager(BedwarsTestPlugin plugin, GameManager gameManager) {
        File file = new File(plugin.getDataFolder() + "\\arena.yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configurationSection = arenaConfig.getConfigurationSection("game.waiting");

        waiting = new Waiting(configurationSection);
        this.gameManager = gameManager;

        log.info("Система очереди загружена.");
    }

    public void addPlayerToWaiting(Player player) {
        if (waiting.getPlayers().size() < gameManager.getGame().getArena().getMaxPlayers()) {
            waiting.addPlayerToWaiting(player);
        }

        if (waiting.getPlayers().size() >= gameManager.getGame().getArena().getMinPlayers()) {
            gameManager.startGame();
        }
    }
}
