package com.gamemanager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GameManager manager = new GameManager();

        manager.addGame(new Game(
            "Minecraft", "Sandbox", "PC"
        ));

        manager.addGame(new PhysicalGame(
            "Elden Ring", "RPG", "PS5", "Good"
        ));

        int choice = 0;

        while (choice != 4) {

            System.out.println("\n===== GAME MANAGER =====");
            System.out.println("1. Add Game");
            System.out.println("2. View Games");
            System.out.println("3. Remove Game");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Game title: ");
                    String title = scanner.nextLine();

                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();

                    System.out.print("Platform: ");
                    String platform = scanner.nextLine();

                    manager.addGame(
                        new Game(title, genre, platform)
                    );
                    break;

                case 2:
                    manager.viewGames();
                    break;

                case 3:
                    System.out.print("Title to remove: ");
                    String removeTitle = scanner.nextLine();
                    manager.removeGame(removeTitle);
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
