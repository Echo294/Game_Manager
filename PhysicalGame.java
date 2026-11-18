
public class PhysicalGame extends Game {

    private String condition;

    public PhysicalGame(String title, String genre,
                        String platform, String condition) {
        super(title, genre, platform);
        this.condition = condition;
    }

    public String getCondition() { return condition; }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Condition: " + condition);
    }
}
