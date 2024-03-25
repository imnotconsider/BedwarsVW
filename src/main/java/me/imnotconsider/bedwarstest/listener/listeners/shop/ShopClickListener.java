package me.imnotconsider.bedwarstest.listener.listeners.shop;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.shop.ShopGui;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class ShopClickListener implements Listener {
    private final ShopGui shopGui;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals(ChatColor.BOLD + "Магазин предметов")) {
            return;
        }
        event.setCancelled(true);

        ItemStack itemStack = event.getCurrentItem();
        net.minecraft.server.v1_8_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsCopy.getTag();
        if (nbtTagCompound == null) {
            return;
        }
        Material material = Material.matchMaterial(nbtTagCompound.getString("price_material"));
        int price = nbtTagCompound.getInt("price");

        if (!isPurchasable(player, material, price)) {
            player.sendMessage(" §c недостаточно материалов для покупки");
            return;
        }

        if (isInventoryFull(player)) {
            player.sendMessage(" §c ваш инвентарь заполнен");
            return;
        }

        player.getInventory().addItem(shopGui.getItemBySlot(event.getSlot()));
        removeMaterials(player, material, price);
        player.sendMessage(" §a успешная покупка");
    }

    private boolean isPurchasable(Player player, Material material, int price) {
        int found = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() != material) {
                continue;
            }
            found += itemStack.getAmount();
        }
        return found >= price;
    }

    private void removeMaterials(Player player, Material material, int price) {
        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack itemStack = inventory.getItem(slot);
            if (itemStack != null && itemStack.getType() == material) {
                int stackAmount = itemStack.getAmount();
                if (stackAmount <= price) {
                    inventory.clear(slot);
                    price -= stackAmount;
                } else {
                    itemStack.setAmount(stackAmount - price);
                    break;
                }
            }
        }
    }

    private boolean isInventoryFull(Player p) {
        return p.getInventory().firstEmpty() == -1;
    }
}
