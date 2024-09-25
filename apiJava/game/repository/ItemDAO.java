package repository;

import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private Connection connection;

    public ItemDAO(Connection connection) {
        this.connection = connection;
    }

    public void addItem(Item item) throws SQLException {
        String sql = "INSERT INTO itens (name, description, is_consumable) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setBoolean(3, item.isConsumable());
            stmt.executeUpdate();
        }
    }

    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT * FROM itens WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("is_consumable")
                );
            }
        }
        return null;
    }

    public List<Item> getItemsBySceneId(int sceneId) throws SQLException {
        String sql = "SELECT * FROM itens WHERE scene_id = ?";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sceneId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("is_consumable")
                ));
            }
        }
        return items;
    }

    public void updateItem(Item item) throws SQLException {
        String sql = "UPDATE itens SET name = ?, description = ?, is_consumable = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setBoolean(3, item.isConsumable());
            stmt.setInt(4, item.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM itens WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
