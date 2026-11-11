public class Game {

    private String title;
    private String genre;
    private String platform;

    public Game(String title, String genre, String platform) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Genre: " + genre);
        System.out.println("Platform: " + platform);
    }
}
