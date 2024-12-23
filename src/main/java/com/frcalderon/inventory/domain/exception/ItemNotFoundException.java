package com.frcalderon.inventory.domain.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super("Item not found.");
    }
}
