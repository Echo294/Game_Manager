
package com.gamevault.model;

public class DigitalGame extends Game {

    private double downloadSize;

    public DigitalGame(String title, String genre,
                       String platform, int releaseYear,
                       double downloadSize) {

        super(title, genre, platform, releaseYear);
        setDownloadSize(downloadSize);
    }

    public double getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(double downloadSize) {
        if (!Double.isFinite(downloadSize)
                || downloadSize < 0) {
            throw new IllegalArgumentException(
                "Invalid download size"
            );
        }
        this.downloadSize = downloadSize;
    }

    @Override
    public String getGameType() {
        return "Digital";
    }

    @Override
    public String getDetails() {
        return super.getDetails()
                + " | Download: " + downloadSize
                + " GB";
    }
}
