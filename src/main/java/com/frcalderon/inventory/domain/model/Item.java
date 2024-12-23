package com.frcalderon.inventory.domain.model;

public class Item {

    private Long id;

    // Should contain between 0 and 50 characters
    private String name;

    private Integer quantity;

    private Location location;

    public static int NAME_MAX_LENGTH = 50;

    public Item() {
    }

    public Item(Long id, String name, Integer quantity, Location location) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
