package com.royal.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.royal.core.entity.Item;
import com.royal.core.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> update(Long id, Item newItem) {
        return itemRepository.findById(id).map(item -> {
            item.setName(newItem.getName());
            item.setDescription(newItem.getDescription());
            return itemRepository.save(item);
        });
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}

