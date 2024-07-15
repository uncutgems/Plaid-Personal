package com.application.mintplaid.repository;

import com.application.mintplaid.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByItemId(String itemId);
    List<Item> findAllByUserId(Long id);
}
