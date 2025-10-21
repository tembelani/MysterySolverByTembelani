package Data;

import java.sql.*;

import Model.Clue;
import Model.Player;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String DB_DIR = "database";
    private static final String DB_URL = "jdbc:sqlite:database/detective.db";
    private Connection conn;

    public DBManager() throws SQLException {
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        File dbDir = new File(DB_DIR);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            if (isValidDatabase()) {
                createTables();
            } else {
                handleCorruptedDatabase();
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("not a database") || e.getMessage().contains("SQLITE_NOTADB")) {
                handleCorruptedDatabase();
            } else {
                throw e;
            }
        }
    }

    private boolean isValidDatabase() {
        try {
            if (conn == null || conn.isClosed()) {
                return false;
            }
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SELECT 1 FROM sqlite_master LIMIT 1");
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void handleCorruptedDatabase() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        File dbFile = new File("database/detective.db");
        if (dbFile.exists()) {
            dbFile.delete();
        }
        conn = DriverManager.getConnection(DB_URL);
        createTables();
    }

    private void createTables() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Enhanced players table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS players (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL,
                    difficulty TEXT DEFAULT 'MEDIUM',
                    current_case INTEGER DEFAULT 1,
                    score INTEGER DEFAULT 0,
                    games_played INTEGER DEFAULT 0,
                    cases_solved INTEGER DEFAULT 0,
                    created_date DATETIME DEFAULT CURRENT_TIMESTAMP
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS player_clues (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player_id INTEGER,
                    clue_id INTEGER,
                    clue_text TEXT,
                    discovered_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (player_id) REFERENCES players (id) ON DELETE CASCADE
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS game_saves (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player_id INTEGER,
                    case_id INTEGER,
                    save_data TEXT,
                    save_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (player_id) REFERENCES players (id) ON DELETE CASCADE
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS suspects (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    description TEXT,
                    questioned INTEGER DEFAULT 0
                );
            """);
        }
    }

    // Player Profile Methods
    public void savePlayerProfile(Player player) throws SQLException {
        String sql = """
            INSERT INTO players (name, difficulty, current_case, score, games_played, cases_solved) 
            VALUES (?, ?, ?, ?, ?, ?)
            ON CONFLICT(name) DO UPDATE SET 
                difficulty = excluded.difficulty,
                current_case = excluded.current_case,
                score = excluded.score,
                games_played = excluded.games_played,
                cases_solved = excluded.cases_solved
        """;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getName());
            ps.setString(2, player.getDifficulty().toString());
            ps.setInt(3, player.getCurrentCaseId());
            ps.setInt(4, player.getScore());
            ps.setInt(5, player.getGamesPlayed());
            ps.setInt(6, player.getCasesSolved());
            ps.executeUpdate();
            
            // Get the generated ID
            if (player.getId() == 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        player.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    public Player loadPlayerProfile(String playerName) throws SQLException {
        String sql = "SELECT * FROM players WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, playerName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Player player = new Player(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("difficulty"),
                        rs.getInt("current_case"),
                        rs.getInt("score"),
                        rs.getInt("games_played"),
                        rs.getInt("cases_solved")
                    );
                    
                    // Load player's discovered clues
                    loadPlayerClues(player);
                    return player;
                }
            }
        }
        return null;
    }

    public List<Player> getAllPlayers() throws SQLException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players ORDER BY score DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Player player = new Player(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("difficulty"),
                    rs.getInt("current_case"),
                    rs.getInt("score"),
                    rs.getInt("games_played"),
                    rs.getInt("cases_solved")
                );
                players.add(player);
            }
        }
        return players;
    }

    private void loadPlayerClues(Player player) throws SQLException {
        String sql = "SELECT clue_text FROM player_clues WHERE player_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, player.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // You might want to reconstruct Clue objects here
                    // For now, we'll just note that clues are loaded
                }
            }
        }
    }

    public void savePlayerClue(int playerId, Clue clue) throws SQLException {
        String sql = "INSERT OR IGNORE INTO player_clues (player_id, clue_id, clue_text) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            ps.setInt(2, clue.getId());
            ps.setString(3, clue.getText());
            ps.executeUpdate();
        }
    }

    // Mark suspect as questioned
    public void markSuspectQuestioned(int suspectId) throws SQLException {
        String sql = "UPDATE suspects SET questioned = 1 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, suspectId);
            ps.executeUpdate();
        }
    }

    public Connection getConnection() {
        return conn;
    }
}