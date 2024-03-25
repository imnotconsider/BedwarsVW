package me.imnotconsider.bedwarstest.listener.listeners;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.GameStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@RequiredArgsConstructor
public class BlockBreakListener implements Listener {
    private final GameManager gameManager;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (gameManager.getGameStatus() == GameStatus.WAITING) {
            event.setCancelled(true);
        }
    }
}
