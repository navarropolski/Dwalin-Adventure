package controller;

import com.google.gson.Gson;
import model.Scene;
import service.Database;
import spark.Request;
import spark.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static spark.Spark.*;

public class GameController {

    private static Gson gson = new Gson();

    public static void main(String[] args) {
        port(4567); // Porta para o Spark rodar

        get("/scene/:id", (req, res) -> {
            int sceneId = Integer.parseInt(req.params(":id"));
            Scene scene = getSceneById(sceneId);
            if (scene != null) {
                return gson.toJson(scene);
            } else {
                res.status(404);
                return "Cena não encontrada";
            }
        });

        // Endpoint para pegar um item
        post("/item/get", (req, res) -> {
            String itemName = req.queryParams("item_name");
            int sceneId = Integer.parseInt(req.queryParams("scene_id"));
            return getItem(itemName, sceneId);
        });

        post("/item/check", (req, res) -> {
            String itemName = req.queryParams("item_name");
            int sceneId = Integer.parseInt(req.queryParams("scene_id"));

            // Verifica se o item está disponível na cena
            String query = "SELECT description FROM itens WHERE name = '" + itemName + "' AND scene_id = " + sceneId;
            try (Connection connection = Database.getConnection()) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    return "Descrição do item: " + rs.getString("description");
                } else {
                    return "Item não encontrado na cena!";
                }
            }
        });


        // Rota de ajuda
        get("/help", (req, res) -> {
            return "Comandos disponíveis: USE, CHECK, GET, INVENTORY, SAVE, LOAD, RESTART";
        });

        post("/item/use", (req, res) -> {
            String itemName = req.queryParams("item_name");
            int sceneId = Integer.parseInt(req.queryParams("scene_id"));

            // Verifica se o item está disponível na cena
            String query = "SELECT * FROM itens WHERE name = '" + itemName + "' AND scene_id = " + sceneId;
            try (Connection connection = Database.getConnection()) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    // Lógica para interagir com o item
                    return "Você usou " + itemName + " na cena!";
                } else {
                    return "Item não encontrado na cena!";
                }
            }
        });

        get("/inventory", (req, res) -> {
            StringBuilder inventoryList = new StringBuilder("Itens no inventário:\n");

            String query = "SELECT i.name FROM inventory inv JOIN itens i ON inv.item_id = i.id";
            try (Connection connection = Database.getConnection()) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    inventoryList.append("- ").append(rs.getString("name")).append("\n");
                }
            }
            return inventoryList.length() > 0 ? inventoryList.toString() : "Inventário vazio.";
        });

        post("/save", (req, res) -> {
            // Lógica para salvar o estado do jogo, como a cena atual e o inventário
            return "Jogo salvo com sucesso!";
        });

        post("/load", (req, res) -> {
            // Lógica para carregar o estado do jogo a partir do banco de dados
            return "Jogo carregado com sucesso!";
        });

        post("/restart", (req, res) -> {
            // Lógica para reiniciar o jogo, limpar inventário e reiniciar a cena
            return "Jogo reiniciado!";
        });


        post("/item/use-with", (req, res) -> {
            String inventoryItemName = req.queryParams("inventory_item");
            String sceneItemName = req.queryParams("scene_item");
            int sceneId = Integer.parseInt(req.queryParams("scene_id"));

            // Verifica se o item do inventário está presente
            String invQuery = "SELECT * FROM inventory inv JOIN itens i ON inv.item_id = i.id WHERE i.name = '" + inventoryItemName + "'";

            // Verifica se o item da cena está presente
            String sceneQuery = "SELECT * FROM itens WHERE name = '" + sceneItemName + "' AND scene_id = " + sceneId;

            try (Connection connection = Database.getConnection()) {
                Statement stmt = connection.createStatement();
                ResultSet invRs = stmt.executeQuery(invQuery);
                if (invRs.next()) {
                    ResultSet sceneRs = stmt.executeQuery(sceneQuery);
                    if (sceneRs.next()) {
                        // Lógica para interagir com os itens
                        return "Você usou " + inventoryItemName + " com " + sceneItemName + " na cena!";
                    } else {
                        return "Item da cena não encontrado!";
                    }
                } else {
                    return "Item do inventário não encontrado!";
                }
            }
        });

    }

    // Método para obter uma cena do banco
    private static Scene getSceneById(int id) {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            System.out.println(id);
            ResultSet rs = stmt.executeQuery("SELECT * FROM scene WHERE id = " + id);
            if (rs.next()) {
                return new Scene(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para pegar um item em uma cena
    private static String getItem(String itemName, int sceneId) {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM itens WHERE name = '" + itemName + "' AND scene_id = " + sceneId;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                // Lógica para inserir no inventário
                stmt.execute("INSERT INTO inventory (item_id, scene_id) VALUES (" + rs.getInt("id") + ", " + sceneId + ")");
                return "Item adicionado ao inventário!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Item não encontrado na cena!";
    }

    // Novo método para usar um item do inventário com um item da cena
    private static String useItemWith(String inventoryItemName, String sceneItemName, int sceneId) {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();

            // Verifica se o item está no inventário
            ResultSet inventoryRs = stmt.executeQuery("SELECT * FROM inventory INNER JOIN itens ON inventory.item_id = itens.id WHERE itens.name = '" + inventoryItemName + "' AND inventory.is_used = false");
            if (!inventoryRs.next()) {
                return "Item '" + inventoryItemName + "' não está no inventário ou já foi usado.";
            }

            // Verifica se o item da cena existe
            ResultSet sceneItemRs = stmt.executeQuery("SELECT * FROM itens WHERE name = '" + sceneItemName + "' AND scene_id = " + sceneId);
            if (!sceneItemRs.next()) {
                return "Item '" + sceneItemName + "' não foi encontrado na cena.";
            }

            // Verifica se há uma ação correspondente no banco
            ResultSet actionRs = stmt.executeQuery("SELECT * FROM actions WHERE required_item_id = " + inventoryRs.getInt("item_id") + " AND scene_id = " + sceneId + " AND item_id = " + sceneItemRs.getInt("id"));
            if (actionRs.next()) {
                // Atualiza o inventário para marcar o item como usado
                stmt.executeUpdate("UPDATE inventory SET is_used = true, used_in_scene_id = " + sceneId + " WHERE item_id = " + inventoryRs.getInt("item_id"));

                // Retorna a mensagem de sucesso e avança para a próxima cena se houver
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