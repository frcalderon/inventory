package com.frcalderon.inventory.domain.service;

import com.frcalderon.inventory.domain.command.CreateItemCommand;
import com.frcalderon.inventory.domain.command.UpdateItemCommand;
import com.frcalderon.inventory.domain.exception.ItemNotFoundException;
import com.frcalderon.inventory.domain.model.Item;
import com.frcalderon.inventory.domain.repository.ItemRepository;

import java.util.List;

public class ItemUseCaseImpl implements ItemUseCases {

    private final ItemRepository repository;

    public ItemUseCaseImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Item> getAllItems() {
        return this.repository.findAll();
    }

    @Override
    public List<Item> searchItemsByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public Item getItemById(Long id) {
        return this.repository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public Item createItem(CreateItemCommand createItemCommand) {
        Item itemToSave = new Item(
                null, createItemCommand.getName(), createItemCommand.getQuantity(), createItemCommand.getLocation()
        );

        return this.repository.save(itemToSave);
    }

    @Override
    public Item updateItem(Long id, UpdateItemCommand updateItemCommand) {
        Item itemToUpdate = this.repository.findById(id).orElseThrow(ItemNotFoundException::new);

        itemToUpdate.setName(updateItemCommand.getName());
        itemToUpdate.setQuantity(updateItemCommand.getQuantity());
        itemToUpdate.setLocation(updateItemCommand.getLocation());

        return this.repository.save(itemToUpdate);
    }

    @Override
    public void deleteItem(Long id) {
        this.repository.findById(id).orElseThrow(ItemNotFoundException::new);

        this.repository.delete(id);
    }
}
