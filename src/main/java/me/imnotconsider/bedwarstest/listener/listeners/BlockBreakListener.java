package me.imnotconsider.bedwarstest.listener.listeners;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.GameStatus;
import me.imnotconsider.bedwarstest.gameplay.team.Team;
import me.imnotconsider.bedwarstest.user.User;
import me.imnotconsider.bedwarstest.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@RequiredArgsConstructor
public class BlockBreakListener implements Listener {
    private final UserManager userManager;
    private final GameManager gameManager;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (gameManager.getGameStatus() == GameStatus.WAITING) {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();
        if (block.getType() == Material.BED_BLOCK) {
            Player player = event.getPlayer();
            Team team = gameManager.getTeamManager().getTeamByPlayer(player);
            Team bedTeam = gameManager.getTeamManager().getTeamByBedLocation(block.getLocation());

            if (team == bedTeam) { // если игрок сломал свою же кровать
                event.setCancelled(true);
            } else { // ебать! кровать разрушена!
                User user = userManager.getUser(player.getUniqueId());
                user.addBrokenBeds();
                bedTeam.setHasBed(false);
                Bukkit.broadcastMessage(String.format(" [§C⚔§f]  Кровать команды %s §fбыла разрушена игроком %s%s§f!",
                        bedTeam.getDisplayName(), team.getPrefix(), player.getName()));
            }

        }
    }
}
