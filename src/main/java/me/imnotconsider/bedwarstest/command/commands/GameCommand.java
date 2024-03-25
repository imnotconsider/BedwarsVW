package me.imnotconsider.bedwarstest.command.commands;

import lombok.RequiredArgsConstructor;
import me.imnotconsider.bedwarstest.gameplay.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class GameCommand implements CommandExecutor {
    private final GameManager gameManager;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("недостаточно прав");
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            gameManager.startGame();
            sender.sendMessage("Игра успешно запущена.");
        } else if (args[1].equalsIgnoreCase("stop")) {
            gameManager.stopGame();
            sender.sendMessage("Игра успешно остановлена.");
        }
        return true;
    }
}
