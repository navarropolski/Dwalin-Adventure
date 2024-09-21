package model;

public class Actions {
    private int id;
    private int itemId;
    private int sceneId;
    private boolean isSuccess;
    private String message;
    private int nextSceneId;
    private boolean requiresItem;
    private int requiredItemId;

    public Actions(int id, int itemId, int sceneId, boolean isSuccess, String message, int nextSceneId, boolean requiresItem, int requiredItemId) {
        this.id = id;
        this.itemId = itemId;
        this.sceneId = sceneId;
        this.isSuccess = isSuccess;
        this.message = message;
        this.nextSceneId = nextSceneId;
        this.requiresItem = requiresItem;
        this.requiredItemId = requiredItemId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public int getNextSceneId() {
        return nextSceneId;
    }

    public boolean isRequiresItem() {
        return requiresItem;
    }

    public int getRequiredItemId() {
        return requiredItemId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNextSceneId(int nextSceneId) {
        this.nextSceneId = nextSceneId;
    }

    public void setRequiresItem(boolean requiresItem) {
        this.requiresItem = requiresItem;
    }

    public void setRequiredItemId(int requiredItemId) {
        this.requiredItemId = requiredItemId;
    }
}
