package model;

public class Item {
    private int id;
    private String name;
    private String description;
    private boolean isConsumable;

    public Item(int id, String name, String description, boolean isConsumable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isConsumable = isConsumable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isConsumable() {
        return isConsumable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConsumable(boolean isConsumable) {
        this.isConsumable = isConsumable;
    }
}
