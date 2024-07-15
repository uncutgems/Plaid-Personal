package com.producer.plaidclient.repository;

import com.producer.plaidclient.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByItemId(String itemId);

    @Modifying
    @Query("update Item i set i.actionRequired = :action_required where i.itemId = :item_id")
    void notifyValidityByItemId(@Param(value = "item_id") String itemId,
                                @Param(value = "action_required") String actionRequired);
}
