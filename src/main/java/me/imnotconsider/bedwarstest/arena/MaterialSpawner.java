package me.imnotconsider.bedwarstest.arena;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import me.imnotconsider.bedwarstest.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
@Data
public class MaterialSpawner {
    private final int delay;
    private final Location location;
    private final String name;
    private final Material material;
    private int taskId;

    public MaterialSpawner(ConfigurationSection configurationSection) {
        this.delay = configurationSection.getInt("delay", 20);
        this.location = LocationUtil.getLocation(configurationSection.getString("location"));
        this.name = configurationSection.getString("name");
        this.material = Material.matchMaterial(configurationSection.getString("material", "AIR"));
        this.taskId = -1;
    }

    public void start(BedwarsTestPlugin plugin) {
        taskId = new BukkitRunnable() {
            public void run() {
                ItemStack itemStack = new ItemStack(material);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(name);
                itemStack.setItemMeta(itemMeta);
                location.getWorld().dropItemNaturally(location, itemStack);
            }
        }.runTaskTimer(plugin, 0, delay).getTaskId();
    }

    public void stop() {
        if (taskId == -1)
            return;
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = -1;
    }
}
