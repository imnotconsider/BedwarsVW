package me.imnotconsider.bedwarstest.command;

import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import me.imnotconsider.bedwarstest.arena.ArenaManager;
import me.imnotconsider.bedwarstest.command.commands.*;
import me.imnotconsider.bedwarstest.gameplay.GameManager;

public class CommandManager {
    public CommandManager(BedwarsTestPlugin plugin, GameManager gameManager, ArenaManager arenaManager) {
        plugin.getCommand("gameinfo").setExecutor(new GameInfoCommand(arenaManager, gameManager));
        plugin.getCommand("game").setExecutor(new GameCommand(gameManager));
    }
}
