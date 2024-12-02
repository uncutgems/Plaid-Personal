package com.application.mintplaid.service;

import com.application.mintplaid.dto.TransactionRequest;
import com.application.mintplaid.entity.Account;
import com.application.mintplaid.entity.Item;
import com.application.mintplaid.entity.Transaction;
import com.application.mintplaid.entity.User;
import com.application.mintplaid.repository.AccountRepository;
import com.application.mintplaid.repository.ItemRepository;
import com.application.mintplaid.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionServiceInterface{
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    private final ItemRepository itemRepository;
    private static final String dateFormat = "dd-MM-yyyy";

    @Override
    public List<Transaction> findTransactionByDate(TransactionRequest transactionRequest) throws ParseException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int page = transactionRequest.getPage() != null ? transactionRequest.getPage() : 0;
        int size = transactionRequest.getSize() != null ? transactionRequest.getSize() : 25;
        Pageable pageable = PageRequest.of(page, size);

        List<Item> items = itemRepository.findAllByUserId(user.getId());
        List<String> itemIds = new ArrayList<>();
        for (Item item : items) {
            itemIds.add(item.getItemId());
        }

        List<Account> accounts = accountRepository.getAccountsByItemIdIn(itemIds);
        List<String> accountIds = new ArrayList<>();
        for (Account account : accounts) {
            accountIds.add(account.getPlaidAccountId());
        }
        if (transactionRequest.getStartDate() != null && transactionRequest.getEndDate() != null) {
            Date startDate = new SimpleDateFormat(dateFormat).parse(transactionRequest.getStartDate());
            Date endDate = new SimpleDateFormat(dateFormat).parse(transactionRequest.getEndDate());

            return transactionRepository.findAllByPlaidAccountInAndDateBetween(accountIds, startDate, endDate, pageable);
        }
        return transactionRepository.findAllByPlaidAccountIn(accountIds, pageable);
    }


//    @Override
//    public String populateTransaction(String accessToken) throws URISyntaxException, ParseException {
//        PlaidTransactionRequest plaidTransactionRequest = new PlaidTransactionRequest();
//        plaidTransactionRequest.setAccessToken(accessToken);
//        plaidTransactionRequest.setClientId(Constant.sandboxClientId);
//        plaidTransactionRequest.setSecret(Constant.sandboxSecret);
//        plaidTransactionRequest.setStartDate("2021-08-23");
//        plaidTransactionRequest.setEndDate("2023-04-11");
//        RestTemplate restTemplate = new RestTemplate();
//        URI uri = new URI(Constant.sandboxEnv + "/transactions/get");
//        ResponseEntity<PlaidTransactionResponse> response =
//                restTemplate.postForEntity(uri, plaidTransactionRequest, PlaidTransactionResponse.class);
//        if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
//            for (PlaidAccountContract plaidAccount : response.getBody().getAccounts()) {
//                Account account = accountRepository.getAccountByPlaidAccountId(plaidAccount.getAccountId());
//                if (account != null) {
//                    account.setCurrentBalance(plaidAccount.getBalances().getCurrent());
//                    account.setAvailableBalance(plaidAccount.getBalances().getAvailable());
//                    account.setName(plaidAccount.getName());
//                    account.setType(plaidAccount.getType());
//                    account.setSubtype(plaidAccount.getSubtype());
//                    account.setItemId(response.getBody().getItem().getItemId());
//                } else {
//                    account = Account.builder()
//                            .plaidAccountId(plaidAccount.getAccountId())
//                            .currentBalance(plaidAccount.getBalances().getCurrent())
//                            .availableBalance(plaidAccount.getBalances().getAvailable())
//                            .name(plaidAccount.getName())
//                            .type(plaidAccount.getType())
//                            .subtype(plaidAccount.getSubtype())
//                            .itemId(response.getBody().getItem().getItemId())
//                            .build();
//                }
//                accountRepository.save(account);
//            }
//            for (PlaidTransactionContract plaidTransaction : response.getBody().getTransactions()) {
//                Transaction transaction = transactionRepository.
//                        findByPlaidTransactionId(plaidTransaction.getTransactionId());
//                if (transaction != null) {
//                    transaction.setDate(new SimpleDateFormat(dateFormat).parse(plaidTransaction.getDate()));
//                    transaction.setAmount(plaidTransaction.getAmount());
//                    transaction.setPending(plaidTransaction.getPending());
//                    transaction.setMerchantName(plaidTransaction.getMerchantName());
//                    transaction.setPaymentChannel(plaidTransaction.getPaymentChannel());
//                } else {
//                    transaction = Transaction.builder()
//                            .plaidTransactionId(plaidTransaction.getTransactionId())
//                            .date(new SimpleDateFormat(dateFormat).parse(plaidTransaction.getDate()))
//                            .amount(plaidTransaction.getAmount())
//                            .pending(plaidTransaction.getPending())
//                            .merchantName(plaidTransaction.getMerchantName())
//                            .paymentChannel(plaidTransaction.getPaymentChannel())
//                            .plaidAccount(plaidTransaction.getAccountId())
//                            .build();
//                }
//                transactionRepository.save(transaction);
//            }
//            return "The transactions and the accounts are fetched successfully";
//        }
//
//        return "Error has occurred";
//    }
}