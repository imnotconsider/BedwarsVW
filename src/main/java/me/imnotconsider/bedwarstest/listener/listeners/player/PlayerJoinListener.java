package me.imnotconsider.bedwarstest.listener.listeners.player;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {
    private final GameManager gameManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        gameManager.getWaitingManager().addPlayerToWaiting(player);
        player.teleport(gameManager.getWaitingManager().getWaiting().getLocation());
        event.setJoinMessage(String.format(" [§a+§f]  Игрок %s зашел в игру", player.getName()));
    }
}
