package com.gamemanager;

public class PhysicalGame extends Game {

    private String condition;

    public PhysicalGame(String title, String genre,
                        String platform, String condition) {
        super(title, genre, platform);
        this.condition = condition;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Condition: " + condition);
    }
}
