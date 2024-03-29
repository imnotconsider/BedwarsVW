package me.imnotconsider.bedwarstest.gameplay.team;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.material.Bed;

import java.io.File;
import java.util.*;

@Log4j2
public class TeamManager {
    @Getter
    private ArrayList<Team> teams;
    private final BedwarsTestPlugin plugin;

    public TeamManager(BedwarsTestPlugin plugin) {
        this.teams = new ArrayList<>();
        this.plugin = plugin;
        File file = new File(plugin.getDataFolder() + "\\arena.yml");
        FileConfiguration teamConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection teamsSection = teamConfig.getConfigurationSection("arena.teams");

        for (String teamSection : teamsSection.getKeys(false)) {
            teams.add(new Team(teamsSection.getConfigurationSection(teamSection)));
        }

        log.info("team manager is loaded");
    }

    public void balancePlayers(List<Player> players) {
        Collections.shuffle(players);
        Queue<Player> balance = new LinkedList<>(players);
        while (!balance.isEmpty()) {
            for (Team team : teams) {
                Player player = balance.poll();
                team.addPlayer(player);
            }
        }
    }

    public Team getTeamByPlayer(Player player) {
        return teams.stream()
                .filter(team -> team.getPlayers().contains(player))
                .findFirst()
                .orElse(null);
    }

    public void placeBeds() {
        for (Team team : teams) {
            createBed(team.getFirstBedLocation(), team.getSecondBedLocation());
        }
    }

    public void createBed(Location headBedLocation, Location footBedLocation) { // https://rubukkit.org/threads/razmeschenie-krovati.170767/
        World world = headBedLocation.getWorld();
        Block blockHead = world.getBlockAt(headBedLocation);
        Block blockFoot = world.getBlockAt(footBedLocation);
        BlockState headState = blockHead.getState();
        BlockState feedState = blockFoot.getState();

        headState.setType(Material.BED_BLOCK);
        feedState.setType(Material.BED_BLOCK);
        headState.setRawData((byte) 0x0);
        feedState.setRawData((byte) 0x8);
        feedState.update(true, false);
        headState.update(true, false);

        Bed bedHead = (Bed) headState.getData();
        bedHead.setHeadOfBed(true);
        bedHead.setFacingDirection(blockHead.getFace(blockFoot).getOppositeFace());

        Bed bedFeed = (Bed) feedState.getData();
        bedFeed.setHeadOfBed(false);
        bedFeed.setFacingDirection(blockFoot.getFace(blockHead));

        feedState.update(true, false);
        headState.update(true, true);
    }

    public Team getTeamByBedLocation(Location location) {
        for (Team team : teams) {
            if (isWithinRange(location, team.getFirstBedLocation())) {
                return team;
            }
        }
        return null;
    }

    private boolean isWithinRange(Location loc1, Location loc2) { //учитывает погрешность в n блоков
        int range = 3;
        return loc1.getWorld() == loc2.getWorld()
                && Math.abs(loc1.getBlockX() - loc2.getBlockX()) <= range
                && Math.abs(loc1.getBlockY() - loc2.getBlockY()) <= range
                && Math.abs(loc1.getBlockZ() - loc2.getBlockZ()) <= range;
    }
}
