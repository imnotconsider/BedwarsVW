package me.imnotconsider.bedwarstest.listener.listeners.player;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.GameStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


@RequiredArgsConstructor
public class PlayerAttackListener implements Listener {
    private final GameManager gameManager;

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (gameManager.getGameStatus() == GameStatus.WAITING) {
            event.setCancelled(true);
        }
    }
}
