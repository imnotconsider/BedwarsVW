package me.imnotconsider.bedwarstest.listener;

import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.shop.ShopManager;
import me.imnotconsider.bedwarstest.listener.listeners.*;
import me.imnotconsider.bedwarstest.listener.listeners.player.*;
import me.imnotconsider.bedwarstest.listener.listeners.shop.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    public ListenerManager(BedwarsTestPlugin plugin, ShopManager shopManager, GameManager gameManager) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerAttackListener(gameManager), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(gameManager), plugin);
        pluginManager.registerEvents(new ShopOpenListener(shopManager), plugin);
        pluginManager.registerEvents(new ShopClickListener(shopManager.getShopGui()), plugin);
        pluginManager.registerEvents(new BlockBreakListener(gameManager), plugin);
        pluginManager.registerEvents(new PlayerQuitEvent(), plugin);
    }
}
