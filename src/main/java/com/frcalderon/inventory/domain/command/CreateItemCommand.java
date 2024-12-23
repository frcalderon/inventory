package com.frcalderon.inventory.domain.command;

import com.frcalderon.inventory.domain.model.Item;
import com.frcalderon.inventory.domain.model.Location;

public class CreateItemCommand {

    private final String name;

    private final Integer quantity;

    private final Location location;

    public CreateItemCommand(String name, Integer quantity, Location location) {
        if (name == null || name.isBlank() || name.isEmpty() || name.length() > Item.NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Name is required and should contain between 0 and 50 characters");
        }

        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Quantity is required and cannot be negative");
        }

        if (location == null) {
            throw new IllegalArgumentException("Location is required");
        }

        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Location getLocation() {
        return location;
    }
}
