package me.imnotconsider.bedwarstest.gameplay.shop;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.io.File;
import java.util.ArrayList;

@Log4j2
public class ShopManager {
    private final BedwarsTestPlugin plugin;
    @Getter
    private final ArrayList<Shop> shops;
    @Getter
    private ShopGui shopGui;

    public ShopManager(BedwarsTestPlugin plugin) {
        this.plugin = plugin;
        shops = new ArrayList<>();

        loadShops();
        log.info("shops are loaded.");

        loadShopGui();
        log.info("shop gui is loaded.");
        log.info("shop manager is loaded");
    }

    private void loadShops() {
        File file = new File(plugin.getDataFolder() + "\\shop.yml");
        FileConfiguration shopConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection shopsSection = shopConfig.getConfigurationSection("shop.villages.items");

        for (String shopSection : shopsSection.getKeys(false)) {
            shops.add(new Shop(shopsSection.getConfigurationSection(shopSection)));
        }
    }

    public void spawnShops() {
        for (Shop shop : shops) {
            Location location = shop.getLocation();

            Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
            villager.setCustomName("Магазин предметов");
            villager.setCustomNameVisible(true);
            villager.setMaxHealth(2048);
            villager.setHealth(2048);
        }
    }

    private void loadShopGui() {
        File file = new File(plugin.getDataFolder() + "\\shop.yml");
        FileConfiguration shopGuiConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection shopGuiSection = shopGuiConfig.getConfigurationSection("shop.gui");
        shopGui = new ShopGui(shopGuiSection);
    }

    public void openShop(Player player) {
        shopGui.openShop(player);
    }

}
