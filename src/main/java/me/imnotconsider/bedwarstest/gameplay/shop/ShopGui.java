package me.imnotconsider.bedwarstest.gameplay.shop;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Log4j2
public class ShopGui {
    private final Inventory shop;
    private final Map<Integer, ItemStack> items;

    public ShopGui(ConfigurationSection configurationSection) {
        shop = Bukkit.createInventory(null, 18, ChatColor.BOLD + "Магазин предметов");
        items = new HashMap<>();

        for (String item : configurationSection.getKeys(false)) {
            int slot = Integer.parseInt(item);

            ConfigurationSection section = configurationSection.getConfigurationSection(item);
            ItemStack itemStack = new ItemStack(Material.matchMaterial(section.getString("item", "AIR")));
            int amount = section.getInt("amount");
            int price = section.getInt("price");
            Material material = Material.matchMaterial(section.getString("material"));

            itemStack.setAmount(amount);
            items.put(slot, itemStack);

            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "Стоимость:");
            lore.add(String.format(" §6 %s %s", price, material));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            net.minecraft.server.v1_8_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
            NBTTagCompound nbtTagCompound = nmsCopy.getTag();
            if (nbtTagCompound == null) {
                nbtTagCompound = new NBTTagCompound();
            }

            nbtTagCompound.setInt("price", price);
            nbtTagCompound.setString("price_material", material.name());

            itemStack = CraftItemStack.asBukkitCopy(nmsCopy);

            shop.setItem(slot, itemStack);
        }
    }

    public void openShop(Player player) {
        player.openInventory(shop);
    }

    public ItemStack getItemBySlot(int slot) {
        return items.get(slot);
    }
}
