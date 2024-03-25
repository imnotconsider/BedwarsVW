package me.imnotconsider.bedwarstest.gameplay;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.imnotconsider.bedwarstest.BedwarsTestPlugin;
import me.imnotconsider.bedwarstest.arena.ArenaManager;
import me.imnotconsider.bedwarstest.arena.MaterialSpawner;
import me.imnotconsider.bedwarstest.gameplay.shop.ShopManager;
import me.imnotconsider.bedwarstest.gameplay.waiting.WaitingManager;

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

    public GameManager(BedwarsTestPlugin plugin, ArenaManager arenaManager) {
        gameStatus = GameStatus.WAITING;
        this.plugin = plugin;
        this.game = new Game(arenaManager.getArena());
        this.shopManager = new ShopManager(plugin);
        this.waitingManager = new WaitingManager(plugin, this);
        log.info("Игра загружена.");
    }

    public void startGame() {
        game.getArena().getSpawners().forEach(arenaMaterialSpawner -> arenaMaterialSpawner.start(plugin));
        log.info("Спавнеры ресурсов запущены.");
        shopManager.spawnShops();
        log.info("Жители-магазины заспавнены.");
        gameStatus = GameStatus.PLAYING;
        log.info("Статус игры изменен на " + gameStatus);

        log.info("ИГРА ЗАПУЩЕНА!");
    }

    public void stopGame() {
        game.getArena().getSpawners().forEach(MaterialSpawner::stop);
        log.info("Спавнеры ресурсов остановлены.");
        gameStatus = GameStatus.WAITING;
        log.info("Статус игры изменен на " + gameStatus);

        log.info("ИГРА ОСТАНОВЛЕНА!");
    }
}
