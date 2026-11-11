
package com.gamevault.service;

public abstract class Game {

    private String title;
    private String genre;
    private String platform;
    private int releaseYear;
    private GameStatus status;

    public Game(String title, String genre,
                String platform, int releaseYear) {
        setTitle(title);
        setGenre(genre);
        setPlatform(platform);
        setReleaseYear(releaseYear);
        this.status = GameStatus.NOT_STARTED;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                "Title cannot be empty"
            );
        }
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear < 1950) {
            throw new IllegalArgumentException(
                "Invalid release year"
            );
        }
        this.releaseYear = releaseYear;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                "Status cannot be null"
            );
        }
        this.status = status;
    }

    public abstract String getGameType();

    public String getDetails() {
        return title + " | " + genre + " | "
                + platform + " | " + releaseYear
                + " | " + status;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
