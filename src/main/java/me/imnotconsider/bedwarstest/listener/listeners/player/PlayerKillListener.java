package me.imnotconsider.bedwarstest.listener.listeners.player;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.team.Team;
import me.imnotconsider.bedwarstest.user.User;
import me.imnotconsider.bedwarstest.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

@RequiredArgsConstructor
public class PlayerKillListener implements Listener {
    private final UserManager userManager;
    private final GameManager gameManager;

    @EventHandler
    @SneakyThrows
    public void onPlayerKill(PlayerDeathEvent event) {
        User killedUser = userManager.getUser(event.getEntity().getUniqueId()); // убитый
        User killerUser = userManager.getUser(event.getEntity().getKiller().getUniqueId()); //убийца
        killedUser.addDeath();
        killerUser.addKill();
        userManager.saveUser(killedUser);
        userManager.saveUser(killerUser);

        Player killedPlayer = event.getEntity();
        event.setKeepInventory(true);
        killedPlayer.getInventory().clear(); // чтобы не выпали вещи

        Team teamKilledPlayer = gameManager.getTeamManager().getTeamByPlayer(killedPlayer);
        if (!teamKilledPlayer.isHasBed()) {
            gameManager.playerLose(killedPlayer);
        }

        if (teamKilledPlayer.getPlayers().isEmpty()) { // команда убитого проебала)
            ArrayList<Team> teams = gameManager.getGame().getTeams();

            for (int i = 0; i < teams.size() - 1; i++) { // получаем команду победителей
                if (!(teams.get(i) == teamKilledPlayer)) {
                    Team teamWin = teams.get(i);
                    Bukkit.broadcastMessage(String.format("Команда %s победила!", teamWin.getDisplayName()));
                }
            }
            gameManager.stopGame();
        }
    }
}
