package me.imnotconsider.bedwarstest.gameplay;

import lombok.Data;
import me.imnotconsider.bedwarstest.arena.Arena;
import me.imnotconsider.bedwarstest.gameplay.team.Team;

import java.util.ArrayList;

@Data
public class Game {
    private final Arena arena;
    private final ArrayList<Team> teams;

    public Game(Arena arena) {
        this.arena = arena;
        teams = new ArrayList<>();
    }
}
