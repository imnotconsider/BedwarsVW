package me.imnotconsider.bedwarstest.listener.listeners.player;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {
    private final GameManager gameManager;
    private final UserManager userManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException, InterruptedException {
        Player player = event.getPlayer();
        userManager.loadUser(player.getUniqueId());
        gameManager.getWaitingManager().addPlayer(player);
        player.teleport(gameManager.getWaitingManager().getWaiting().getLocation());
        player.setHealth(20);
        event.setJoinMessage(String.format(" [§a+§f]  Игрок %s зашел в игру", player.getName()));
    }
}
