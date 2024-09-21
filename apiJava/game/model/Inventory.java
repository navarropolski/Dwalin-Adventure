package model;

public class Inventory {
    private int id;
    private int itemId;
    private int sceneId;
    private int usedInSceneId;
    private boolean isUsed;

    public Inventory(int id, int itemId, int sceneId, int usedInSceneId, boolean isUsed) {
        this.id = id;
        this.itemId = itemId;
        this.sceneId = sceneId;
        this.usedInSceneId = usedInSceneId;
        this.isUsed = isUsed;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public int getUsedInSceneId() {
        return usedInSceneId;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public void setUsedInSceneId(int usedInSceneId) {
        this.usedInSceneId = usedInSceneId;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}

