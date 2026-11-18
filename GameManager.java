import java.util.*;
import java.io.*;
import java.nio.file.*;

public class GameManager {
    private final ArrayList<Game> games = new ArrayList<>();
    private final Path savePath = Paths.get(System.getProperty("user.home"), "gamevault-games.tsv");
    public GameManager() { load(); }
    public List<Game> getGames() { return Collections.unmodifiableList(games); }
    public void addGame(Game game) { games.add(game); save(); }
    public void removeGame(Game game) { games.remove(game); save(); }
    private String encode(String s) { return Base64.getEncoder().encodeToString(s.getBytes(java.nio.charset.StandardCharsets.UTF_8)); }
    private String decode(String s) { return new String(Base64.getDecoder().decode(s), java.nio.charset.StandardCharsets.UTF_8); }
    public void save() {
        try (BufferedWriter out = Files.newBufferedWriter(savePath)) {
            for (Game g : games) {
                String condition = g instanceof PhysicalGame ? ((PhysicalGame)g).getCondition() : "";
                out.write(String.join("\t", encode(g.getTitle()), encode(g.getGenre()), encode(g.getPlatform()), encode(condition)));
                out.newLine();
            }
        } catch (IOException e) { throw new UncheckedIOException("Could not save games", e); }
    }
    public void load() {
        if (!Files.exists(savePath)) return;
        try (BufferedReader in = Files.newBufferedReader(savePath)) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split("\t", -1);
                if (parts.length != 4) continue;
                String title = decode(parts[0]), genre = decode(parts[1]), platform = decode(parts[2]), condition = decode(parts[3]);
                games.add(condition.isEmpty() ? new Game(title,genre,platform) : new PhysicalGame(title,genre,platform,condition));
            }
        } catch (Exception e) { System.err.println("Could not load saved games: " + e.getMessage()); }
    }
}
