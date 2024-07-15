package com.application.mintplaid.controller;

import com.application.mintplaid.entity.Account;
import com.application.mintplaid.entity.Item;
import com.application.mintplaid.entity.User;
import com.application.mintplaid.service.AccountServiceInterface;
import com.application.mintplaid.service.ItemServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/get-balance")
@RequiredArgsConstructor
public class BalanceController {
    private final AccountServiceInterface accountService;
    private final ItemServiceInterface itemServiceInterface;

    @GetMapping("/all-account")
    public ResponseEntity<List<Account>> getAllAccountDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Item> items = itemServiceInterface.getAllItems(user.getId());
        List<String> itemIds = new ArrayList<>();
        for (Item item : items) {
            itemIds.add(item.getItemId());
        }

        return new ResponseEntity<>(accountService.getAllAccountsDetails(itemIds), HttpStatus.OK);
    }
}
