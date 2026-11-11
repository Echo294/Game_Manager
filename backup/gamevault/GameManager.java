
package com.gamevault.service;

import com.gamevault.model.Game;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final List<Game> games;

    public GameManager() {
        games = new ArrayList<>();
    }

    public void addGame(Game game) {
        if (game == null) {
            throw new IllegalArgumentException(
                "Game cannot be null"
            );
        }
        games.add(game);
    }

    public boolean removeGame(Game game) {
        return games.remove(game);
    }

    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    public List<Game> searchGames(String title) {
        List<Game> results = new ArrayList<>();

        if (title == null) {
            return results;
        }

        for (Game game : games) {
            if (game.getTitle().toLowerCase()
                    .contains(title.toLowerCase())) {
                results.add(game);
            }
        }

        return results;
    }

    public int getTotalGames() {
        return games.size();
    }
}
