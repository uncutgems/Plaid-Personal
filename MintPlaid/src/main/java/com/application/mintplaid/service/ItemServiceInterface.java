package com.application.mintplaid.service;

import com.application.mintplaid.entity.Item;

import java.util.List;

public interface ItemServiceInterface {
    List<Item> getAllItems(Long userId);
}
