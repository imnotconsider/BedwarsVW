package me.imnotconsider.bedwarstest.listener.listeners.player;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.GameStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@RequiredArgsConstructor
public class PlayerPreLoginListener implements Listener {
    private final GameManager gameManager;

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (gameManager.getGameStatus() == GameStatus.PLAYING) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "На данном сервере сейчас идет игра.");
        }
    }
}
