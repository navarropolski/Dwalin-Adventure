package service;

import model.Scene;
import model.Item;
import repository.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class GameService {

    public Scene getSceneById(int id) {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM scene WHERE id = " + id);
            if (rs.next()) {
                return new Scene(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getItem(String itemName, int sceneId) {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM itens WHERE name = '" + itemName + "' AND scene_id = " + sceneId + " AND is_in_inventory = FALSE";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                stmt.execute("INSERT INTO inventory (item_id, scene_id) VALUES (" + rs.getInt("id") + ", " + sceneId + ")");
                stmt.execute("UPDATE itens SET is_in_inventory = TRUE WHERE id = " + rs.getInt("id"));
                return "Item '" + itemName + "' adicionado ao inventário!";
            } else {
                return "Item não encontrado na cena ou já está no inventário!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao pegar o item.";
        }
    }

    public String checkItem(String itemName, int sceneId) {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            String query = "SELECT description FROM itens WHERE name = '" + itemName + "' AND scene_id = " + sceneId;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return "Descrição do item: " + rs.getString("description");
            } else {
                return "Item não encontrado na cena!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao verificar o item.";
        }
    }

    public String useItem(String itemName, int sceneId) {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM itens WHERE name = '" + itemName + "' AND scene_id = " + sceneId;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return "Você usou " + itemName + " na cena!";
            } else {
                return "Item não encontrado na cena!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao usar o item.";
        }
    }

    public String listInventory() {
        StringBuilder inventoryList = new StringBuilder("Itens no inventário:\n");
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT i.name FROM inventory inv JOIN itens i ON inv.item_id = i.id");
            while (rs.next()) {
                inventoryList.append("- ").append(rs.getString("name")).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventoryList.length() > 0 ? inventoryList.toString() : "Inventário vazio.";
    }

    public String useItemWith(String inventoryItemName, String sceneItemName, int sceneId) {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet inventoryRs = stmt.executeQuery("SELECT * FROM inventory INNER JOIN itens ON inventory.item_id = itens.id WHERE itens.name = '" + inventoryItemName + "' AND inventory.is_used = false");
            if (!inventoryRs.next()) {
                return "Item '" + inventoryItemName + "' não está no inventário ou já foi usado.";
            }

            ResultSet sceneItemRs = stmt.executeQuery("SELECT * FROM itens WHERE name = '" + sceneItemName + "' AND scene_id = " + sceneId);
            if (!sceneItemRs.next()) {
                return "Item '" + sceneItemName + "' não foi encontrado na cena.";
            }

            ResultSet actionRs = stmt.executeQuery("SELECT * FROM actions WHERE required_item_id = " + inventoryRs.getInt("item_id") + " AND scene_id = " + sceneId + " AND item_id = " + sceneItemRs.getInt("id"));
            if (actionRs.next()) {
                stmt.executeUpdate("UPDATE inventory SET is_used = true, used_in_scene_id = " + sceneId + " WHERE item_id = " + inventoryRs.getInt("item_id"));
                String message = actionRs.getString("message");
                int nextSceneId = actionRs.getInt("nextScene_id");
                if (nextSceneId > 0) {
                    message += " Você avança para a próxima cena.";
                }
                return message;
            } else {
                return "Não há nenhuma ação correspondente para '" + inventoryItemName + "' com '" + sceneItemName + "' nesta cena.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocorreu um erro ao tentar usar os itens.";
        }
    }
}
