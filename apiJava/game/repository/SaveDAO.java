package repository;

import model.Save;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaveDAO {
    private Connection connection;

    public SaveDAO(Connection connection) {
        this.connection = connection;
    }

    public void addSave(Save save) throws SQLException {
        String sql = "INSERT INTO saves (game_state, timestamp) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, save.getGameState());
            stmt.setString(2, save.getTimestamp());
            stmt.executeUpdate();
        }
    }

    public Save getSaveById(int id) throws SQLException {
        String sql = "SELECT * FROM saves WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Save(
                        rs.getInt("id"),
                        rs.getString("game_state"),
                        rs.getString("timestamp")
                );
            }
        }
        return null;
    }

    public List<Save> getAllSaves() throws SQLException {
        String sql = "SELECT * FROM saves";
        List<Save> saves = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                saves.add(new Save(
                        rs.getInt("id"),
                        rs.getString("game_state"),
                        rs.getString("timestamp")
                ));
            }
        }
        return saves;
    }

    public void updateSave(Save save) throws SQLException {
        String sql = "UPDATE saves SET game_state = ?, timestamp = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, save.getGameState());
            stmt.setString(2, save.getTimestamp());
            stmt.setInt(3, save.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteSave(int id) throws SQLException {
        String sql = "DELETE FROM saves WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
