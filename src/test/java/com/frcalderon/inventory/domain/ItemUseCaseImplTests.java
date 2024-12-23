package com.frcalderon.inventory.domain;

import com.frcalderon.inventory.domain.command.CreateItemCommand;
import com.frcalderon.inventory.domain.command.UpdateItemCommand;
import com.frcalderon.inventory.domain.exception.ItemNotFoundException;
import com.frcalderon.inventory.domain.model.Item;
import com.frcalderon.inventory.domain.model.Location;
import com.frcalderon.inventory.domain.repository.ItemRepository;
import com.frcalderon.inventory.domain.service.ItemUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemUseCaseImplTests {

    private ItemRepository itemRepository;
    private ItemUseCaseImpl tested;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        tested = new ItemUseCaseImpl(itemRepository);
    }

    @Test
    void getAllItems_shouldReturnNonEmptyListWhenInventoryIsNotEmpty() {
        Item fooItem = new Item(1L, "foo", 1, new Location(1L, "foo"));
        when(itemRepository.findAll()).thenReturn(List.of(fooItem));

        List<Item> items = tested.getAllItems();

        verify(itemRepository).findAll();
        Assertions.assertFalse(items.isEmpty());
    }

    @Test
    void getAllItems_shouldReturnEmptyListWhenInventoryIsEmpty() {
        when(itemRepository.findAll()).thenReturn(List.of());

        List<Item> items = tested.getAllItems();

        verify(itemRepository).findAll();
        Assertions.assertTrue(items.isEmpty());
    }

    @Test
    void searchItemsByName_shouldReturnNonEmptyListWhenNameMatchesSearch() {
        Item fooItem = new Item(1L, "foo", 1, new Location(1L, "foo"));
        when(itemRepository.findByName(anyString())).thenReturn(List.of(fooItem));

        List<Item> items = tested.searchItemsByName("foo");

        verify(itemRepository).findByName("foo");
        Assertions.assertFalse(items.isEmpty());
    }

    @Test
    void searchItemsByName_shouldReturnEmptyListWhenNameMatchesSearch() {
        when(itemRepository.findAll()).thenReturn(List.of());

        List<Item> items = tested.getAllItems();

        verify(itemRepository).findAll();
        Assertions.assertTrue(items.isEmpty());
    }

    @Test
    void getItemById_shouldReturnItemWhenIdExists() {
        Item fooItem = new Item(1L, "foo", 1, new Location(1L, "foo"));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(fooItem));

        Item item = tested.getItemById(1L);

        verify(itemRepository).findById(1L);
        Assertions.assertEquals(fooItem, item);
    }

    @Test
    void getItemById_shouldThrowItemNotFoundExceptionWhenIdNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNotFoundException.class, () -> tested.getItemById(1L));

        verify(itemRepository).findById(1L);
    }

    @Test
    void createItem_shouldSaveItemWhenInputDataIsValid() {
        CreateItemCommand createItemCommand = new CreateItemCommand(
                "foo", 1, new Location(1L, "foo")
        );
        Item fooItem = new Item(1L, "foo", 1, new Location(1L, "foo"));
        when(itemRepository.save(isA(Item.class))).thenReturn(fooItem);

        Item item = tested.createItem(createItemCommand);

        verify(itemRepository).save(any(Item.class));
        Assertions.assertNotNull(item.getId());
        Assertions.assertEquals(createItemCommand.getName(), item.getName());
        Assertions.assertEquals(createItemCommand.getQuantity(), item.getQuantity());
        Assertions.assertEquals(createItemCommand.getLocation().getId(), item.getLocation().getId());
        Assertions.assertEquals(createItemCommand.getLocation().getName(), item.getLocation().getName());
    }

    @Test
    void updateItem_shouldUpdateItemWhenInputDataIsValid() {
        UpdateItemCommand updateItemCommand = new UpdateItemCommand(
                "new foo", 2, new Location(2L, "new foo")
        );
        Item fooItem = new Item(1L, "foo", 1, new Location(1L, "foo"));
        Item updatedItem = new Item(1L, "new foo", 2, new Location(2L, "new foo"));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(fooItem));
        when(itemRepository.save(isA(Item.class))).thenReturn(updatedItem);

        Item item = tested.updateItem(1L, updateItemCommand);

        verify(itemRepository).findById(1L);
        verify(itemRepository).save(any(Item.class));
        Assertions.assertNotNull(item.getId());
        Assertions.assertEquals(updateItemCommand.getName(), item.getName());
        Assertions.assertEquals(updateItemCommand.getQuantity(), item.getQuantity());
        Assertions.assertEquals(updateItemCommand.getLocation().getId(), item.getLocation().getId());
        Assertions.assertEquals(updateItemCommand.getLocation().getName(), item.getLocation().getName());
    }

    @Test
    void updateItem_shouldThrowItemNotFoundExceptionWhenIdNotFound() {
        UpdateItemCommand updateItemCommand = new UpdateItemCommand(
                "new foo", 2, new Location(2L, "new foo")
        );
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNotFoundException.class, () -> tested.updateItem(1L, updateItemCommand));

        verify(itemRepository).findById(1L);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void deleteItem_shouldDeleteItemWhenIdExists() {
        Item fooItem = new Item(1L, "foo", 1, new Location(1L, "foo"));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(fooItem));

        tested.deleteItem(1L);

        verify(itemRepository).findById(1L);
        verify(itemRepository).delete(1L);
    }

    @Test
    void deleteItem_shouldThrowItemNotFoundExceptionWhenIdNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNotFoundException.class, () -> tested.deleteItem(1L));

        verify(itemRepository).findById(1L);
        verify(itemRepository, never()).delete(1L);
    }
}
