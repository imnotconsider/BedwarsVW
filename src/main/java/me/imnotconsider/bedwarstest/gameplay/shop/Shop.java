package me.imnotconsider.bedwarstest.gameplay.shop;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

@Log4j2
@Data
public class Shop {
    private final Location location;

    public Shop(ConfigurationSection configurationSection) {
        this.location = LocationUtil.getLocation(configurationSection.getString("location"));
    }
}
