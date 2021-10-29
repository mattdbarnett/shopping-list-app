package com.example.cm6226;

public class Item {

    private String name;
    private String quantity;
    private Boolean finished;

    public Item(String name, String quantity, Boolean finished) {
        this.name = name;
        this.quantity = quantity;
        this.finished = finished;
    }

    public Item(String name, String quantity) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
