package me.imnotconsider.bedwarstest.listener.listeners.player;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.GameStatus;
import me.imnotconsider.bedwarstest.gameplay.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

@RequiredArgsConstructor
public class PlayerRespawnListener implements Listener {
    private final GameManager gameManager;

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (gameManager.getGameStatus() == GameStatus.WAITING) {
            return;
        }
        Player player = event.getPlayer();
        Team team = gameManager.getTeamManager().getTeamByPlayer(player);
        if (!team.isHasBed()) {
            player.kickPlayer("Для вас игра завершена.");
        }
        team.removePlayer(player);
    }
}
