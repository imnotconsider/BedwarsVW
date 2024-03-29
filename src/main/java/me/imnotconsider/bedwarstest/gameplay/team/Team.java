package me.imnotconsider.bedwarstest.gameplay.team;

import lombok.Data;
import me.imnotconsider.bedwarstest.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Data
public class Team {
    private final String name;
    private final char color;
    private final Location spawnLocation;
    private final Location firstBedLocation;
    private final BlockFace bedFacing;
    private final Location secondBedLocation;
    private final ArrayList<Player> players;
    private boolean hasBed;

    public Team(ConfigurationSection configurationSection) {
        this.name = configurationSection.getString("name");
        this.color = configurationSection.getString("color").charAt(0);
        this.spawnLocation = LocationUtil.getLocation(configurationSection.getString("location"));

        this.bedFacing = BlockFace.valueOf(configurationSection.getString("bed_facing"));
        this.firstBedLocation = LocationUtil.getLocation(configurationSection.getString("head_bed_location"));
        this.secondBedLocation = getFootBedLocation(firstBedLocation, bedFacing);

        this.players = new ArrayList<>();
        this.hasBed = true;
    }

    private Location getFootBedLocation(Location headBedLocation, BlockFace bedFacing) {
        int footX = headBedLocation.getBlockX();
        int footY = headBedLocation.getBlockY();
        int footZ = headBedLocation.getBlockZ();

        switch (bedFacing) {
            case NORTH:
                footX++;
                break;
            case EAST:
                footZ--;
                break;
            case SOUTH:
                footX--;
                break;
            case WEST:
                footZ++;
                break;
            default:
                // Если сторона не определена, возвращаем null
                return null;
        }

        World world = headBedLocation.getWorld();
        return new Location(world, footX, footY, footZ);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public String getDisplayName() {
        return getPrefix() + name;
    }

    public String getPrefix() {
        return "§" + color;
    }
}
