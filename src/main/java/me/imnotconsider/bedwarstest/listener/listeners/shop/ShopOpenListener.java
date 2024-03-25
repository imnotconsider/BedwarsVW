package me.imnotconsider.bedwarstest.listener.listeners.shop;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.shop.ShopManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

@RequiredArgsConstructor
public class ShopOpenListener implements Listener {
    private final ShopManager shopManager;

    @EventHandler
    private void onInteract(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity.getType() != EntityType.VILLAGER && !entity.getCustomName().equals("Магазин предметов")) {
            return;
        }
        event.setCancelled(true);
        shopManager.openShop(event.getPlayer());
    }
}
