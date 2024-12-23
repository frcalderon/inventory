package com.frcalderon.inventory.domain.repository;

import com.frcalderon.inventory.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<Item> findAll();
    List<Item> findByName(String name);
    Optional<Item> findById(Long id);
    Item save(Item spaceship);
    void delete(Long id);
}
