package me.imnotconsider.bedwarstest.listener.listeners.player;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import me.imnotconsider.bedwarstest.gameplay.GameStatus;
import me.imnotconsider.bedwarstest.gameplay.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@RequiredArgsConstructor
public class PlayerAttackListener implements Listener {
    private final GameManager gameManager;

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (gameManager.getGameStatus() == GameStatus.WAITING) {
            event.setCancelled(true);
        }
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) {
            return;
        }

        Team damagerTeam = gameManager.getTeamManager().getTeamByPlayer((Player) event.getDamager());
        Team playerTeam = gameManager.getTeamManager().getTeamByPlayer((Player) event.getEntity());

        if (damagerTeam == playerTeam) { // если игроки из одной команды
            event.setCancelled(true);
        }

    }
}
