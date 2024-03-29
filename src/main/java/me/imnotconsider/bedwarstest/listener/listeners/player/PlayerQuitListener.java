package me.imnotconsider.bedwarstest.listener.listeners.player;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.team.Team;
import me.imnotconsider.bedwarstest.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {
    private final UserManager userManager;
    private final GameManager gameManager;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws SQLException, InterruptedException {
        Player player = event.getPlayer();
        userManager.unloadUser(player.getUniqueId());
        gameManager.getWaitingManager().getWaiting().remove(player);
        event.setQuitMessage(String.format(" [§c-§f]  Игрок %s вышел из игры", player.getName()));

        ArrayList<Team> teams = gameManager.getTeamManager().getTeams();

        for (int i = 0; i < teams.size() - 1; i++) {
            if ((!teams.get(i).isHasBed() && teams.get(i).getPlayers().isEmpty()) || teams.get(i).getPlayers().isEmpty()) {
                Team team = teams.get(teams.size() - i);
                Bukkit.broadcastMessage(String.format("Команда %s победила!", team.getDisplayName()));
                gameManager.stopGame();
            }
        }

        for (Team team : gameManager.getTeamManager().getTeams()) {
            if (!team.isHasBed() && team.getPlayers().isEmpty()) {
                gameManager.stopGame();
            }
        }
     }
}
