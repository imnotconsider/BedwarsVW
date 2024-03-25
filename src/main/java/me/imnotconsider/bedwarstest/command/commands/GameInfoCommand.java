package me.imnotconsider.bedwarstest.command.commands;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.arena.ArenaManager;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class GameInfoCommand implements CommandExecutor {
    private final ArenaManager arenaManager;
    private final GameManager gameManager;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("недостаточно прав.");
            return true;
        }

        sender.sendMessage("world: " + arenaManager.getArena().getWorld());
        sender.sendMessage("min_players: " + arenaManager.getArena().getMinPlayers());
        sender.sendMessage("max_players: " + arenaManager.getArena().getMaxPlayers());
        sender.sendMessage("spawners: " + arenaManager.getArena().getSpawners());
        sender.sendMessage("shops: " + gameManager.getShopManager().getShops());
        sender.sendMessage("teams: " + gameManager.getGame().getTeams());
        sender.sendMessage("gamestatus: " + gameManager.getGameStatus());
        sender.sendMessage("waiting: " + gameManager.getWaitingManager().getWaiting().getPlayers());
        return true;
    }
}
