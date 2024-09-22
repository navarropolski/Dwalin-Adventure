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
        port(3306); // Porta para o Spark rodar

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

        // Rota de ajuda
        get("/help", (req, res) -> {
            return "Comandos disponíveis: USE, CHECK, GET, INVENTORY, SAVE, LOAD, RESTART";
        });
    }

    // Método para obter uma cena do banco
    private static Scene getSceneById(int id) {
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
}
