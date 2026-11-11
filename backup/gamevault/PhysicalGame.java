
package com.gamevault;
import com.gamevault.service.Game;

public class PhysicalGame extends Game {

    private String condition;

    public PhysicalGame(String title, String genre,
                        String platform, int releaseYear,
                        String condition) {

        super(title, genre, platform, releaseYear);
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String getGameType() {
        return "Physical";
    }

    @Override
    public String getDetails() {
        return super.getDetails()
                + " | Condition: " + condition;
    }
}
