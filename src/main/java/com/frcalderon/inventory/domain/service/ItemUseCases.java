package com.frcalderon.inventory.domain.service;

import com.frcalderon.inventory.domain.command.CreateItemCommand;
import com.frcalderon.inventory.domain.command.UpdateItemCommand;
import com.frcalderon.inventory.domain.model.Item;

import java.util.List;

public interface ItemUseCases {

    List<Item> getAllItems();
    List<Item> searchItemsByName(String name);
    Item getItemById(Long id);
    Item createItem(CreateItemCommand createItemCommand);
    Item updateItem(Long id, UpdateItemCommand updateItemCommand);
    void deleteItem(Long id);
}
