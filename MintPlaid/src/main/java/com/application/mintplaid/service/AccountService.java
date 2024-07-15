package com.application.mintplaid.service;

import com.application.mintplaid.entity.Account;
import com.application.mintplaid.entity.Item;
import com.application.mintplaid.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceInterface{
    private final AccountRepository accountRepository;
    @Override
    public List<Account> getAllAccountsDetails(List<String> items) {
        return accountRepository.getAccountsByItemIdIn(items);
    }
}
