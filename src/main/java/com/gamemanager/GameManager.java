package com.gamemanager;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<Game> games = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
        System.out.println("Game added successfully!");
    }

    public void viewGames() {
        if (games.isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        for (Game game : games) {
            System.out.println("--------------------");
            game.displayInfo();
        }
    }

    public void removeGame(String title) {
        boolean removed = games.removeIf(
            game -> game.getTitle().equalsIgnoreCase(title)
        );

        if (removed) {
            System.out.println("Game removed successfully!");
        } else {
            System.out.println("Game not found.");
        }
    }
}
