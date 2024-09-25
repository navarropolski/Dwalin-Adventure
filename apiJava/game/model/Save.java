package model;

public class Save {
    private int id;
    private String gameState;
    private String timestamp;

    public Save(int id, String gameState, String timestamp) {
        this.id = id;
        this.gameState = gameState;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getGameState() {
        return gameState;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
