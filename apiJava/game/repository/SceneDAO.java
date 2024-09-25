package repository;

import model.Scene;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SceneDAO {
    private Connection connection;

    public SceneDAO(Connection connection) {
        this.connection = connection;
    }

    public void addScene(Scene scene) throws SQLException {
        String sql = "INSERT INTO scene (name, description) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, scene.getName());
            stmt.setString(2, scene.getDescription());
            stmt.executeUpdate();
        }
    }

    public Scene getSceneById(int id) throws SQLException {
        String sql = "SELECT * FROM scene WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Scene(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
            }
        }
        return null;
    }

    public List<Scene> getAllScenes() throws SQLException {
        String sql = "SELECT * FROM scene";
        List<Scene> scenes = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                scenes.add(new Scene(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        }
        return scenes;
    }

    public void updateScene(Scene scene) throws SQLException {
        String sql = "UPDATE scene SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, scene.getName());
            stmt.setString(2, scene.getDescription());
            stmt.setInt(3, scene.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteScene(int id) throws SQLException {
        String sql = "DELETE FROM scene WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
