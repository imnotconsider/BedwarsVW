package me.imnotconsider.bedwarstest.command.commands;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.team.Team;
import me.imnotconsider.bedwarstest.user.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TestCommand implements CommandExecutor {
    private final UserManager userManager;
    private final GameManager gameManager;


    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;
        sender.sendMessage(String.valueOf(player.getLocation()));
        sender.sendMessage(String.valueOf(player.getUniqueId()));
        sender.sendMessage(ChatColor.RED + String.valueOf(gameManager.getShopManager().getShopGui().getItems().values()));
        for (Team team : gameManager.getTeamManager().getTeams()) {
            if (team.getPlayers().contains(player)) {
                sender.sendMessage("ты в команде " + team.getName());
            }
        }

        sender.sendMessage(" ");
        userManager.loadUser(player.getUniqueId());
        return true;
    }
}
