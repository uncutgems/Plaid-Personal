package com.application.mintplaid.service;

import com.application.mintplaid.entity.Account;

import java.util.List;

public interface AccountServiceInterface {
    List<Account> getAllAccountsDetails(List<String> items);
}
