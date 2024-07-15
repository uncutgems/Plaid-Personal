package com.application.mintplaid.service;

import com.application.mintplaid.entity.Item;
import com.application.mintplaid.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService implements ItemServiceInterface{

    private final ItemRepository itemRepository;
    @Override
    public List<Item> getAllItems(Long userId) {
        return itemRepository.findAllByUserId(userId);
    }
}
