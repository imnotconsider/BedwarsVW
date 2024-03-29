package me.imnotconsider.bedwarstest;

import lombok.SneakyThrows;
import me.imnotconsider.bedwarstest.arena.ArenaManager;
import me.imnotconsider.bedwarstest.command.CommandManager;
import me.imnotconsider.bedwarstest.command.commands.TestCommand;
import me.imnotconsider.bedwarstest.database.DatabaseConnection;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.listener.ListenerManager;
import me.imnotconsider.bedwarstest.user.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedwarsTestPlugin extends JavaPlugin {
    private ArenaManager arenaManager;
    private GameManager gameManager;
    private DatabaseConnection databaseConnection;
    private UserManager userManager;

    @Override
    @SneakyThrows
    public void onEnable() {
        saveDefaultConfig();
        databaseConnection = new DatabaseConnection(this); // работа с бд
        userManager = new UserManager(databaseConnection); // взаимодействие с данными игроков (нужна бд)
        arenaManager = new ArenaManager(this); // загружает мин/макс кол-во игроков, спавнера ресурсов
        gameManager = new GameManager(this, arenaManager, userManager); // самый сок. магазины, очередь, старт/стоп игры
        new ListenerManager(this, gameManager.getShopManager(), gameManager, userManager); // ивенты
        new CommandManager(this, gameManager, arenaManager); // команды
        this.getCommand("test").setExecutor(new TestCommand(userManager, gameManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
