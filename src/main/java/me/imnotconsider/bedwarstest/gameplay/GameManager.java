package me.imnotconsider.bedwarstest.gameplay;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import me.imnotconsider.bedwarstest.arena.ArenaManager;
import me.imnotconsider.bedwarstest.gameplay.shop.ShopManager;
import me.imnotconsider.bedwarstest.gameplay.team.Team;
import me.imnotconsider.bedwarstest.gameplay.team.TeamManager;
import me.imnotconsider.bedwarstest.gameplay.waiting.WaitingManager;
import me.imnotconsider.bedwarstest.user.User;
import me.imnotconsider.bedwarstest.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@Log4j2
public class GameManager {
    private final BedwarsTestPlugin plugin;
    @Getter
    private final Game game;
    @Getter
    private GameStatus gameStatus;
    @Getter
    private final ShopManager shopManager;
    @Getter
    private final WaitingManager waitingManager;
    @Getter
    private final TeamManager teamManager;
    private final UserManager userManager;

    public GameManager(BedwarsTestPlugin plugin, ArenaManager arenaManager, UserManager userManager) {
        gameStatus = GameStatus.WAITING;
        this.plugin = plugin;
        this.game = new Game(arenaManager.getArena());
        this.shopManager = new ShopManager(plugin);
        this.waitingManager = new WaitingManager(plugin, this);
        this.teamManager = new TeamManager(plugin);
        this.userManager = userManager;
        log.info("game manager is loaded");
    }

    public void startGame() {
        game.getArena().getSpawners().forEach(arenaMaterialSpawner -> arenaMaterialSpawner.start(plugin));
        log.info("resource spawners have been started");
        shopManager.spawnShops();
        log.info("villages-shops are flooded");
        gameStatus = GameStatus.PLAYING;
        log.info("gamestatus changed to " + gameStatus);
        teamManager.balancePlayers(waitingManager.getWaiting().getPlayers());
        log.info("players are divided into teams");
        waitingManager.getWaiting().clear();
        log.info("waiting has been cleared");

        teamManager.placeBeds();
        log.info("beds have been placed");

        Bukkit.broadcastMessage(" [§3!§f] Игра запущена. Удачи!");
        log.info("THE GAME IS RUNNING!");

    }

    public void stopGame() throws SQLException, InterruptedException { // все само пропадет после перезагрузки
        Bukkit.broadcastMessage(" [§3!§f] Игра завершена. Спасибо!");

        Bukkit.broadcastMessage("[§3!§f] Сервер будет перезагружен через 5 секунд.");

        for (User user : userManager.getUsers().values()) {
            userManager.unloadUser(user.getUuid());
        }

        scheduleServerReload();

        log.info("THE GAME IS STOPPED!");
    }

    public void playerLose(Player player) {
        Team team = teamManager.getTeamByPlayer(player);
        team.removePlayer(player);
        player.setGameMode(GameMode.SPECTATOR);
        Bukkit.broadcastMessage(String.format("Игрок [%s] §f %s покидает состязание!", team.getDisplayName(), player.getName()));
    }

    private void scheduleServerReload() {
        Bukkit.getScheduler().runTaskLater(plugin, Bukkit::reload, 100L);
    }
}
