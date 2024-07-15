package com.application.mintplaid.service;

import com.application.mintplaid.dto.TransactionRequest;
import com.application.mintplaid.entity.Transaction;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface TransactionServiceInterface {
    List<Transaction> findTransactionByDate(TransactionRequest transactionRequest) throws ParseException;

//    String populateTransaction(String accessToken) throws URISyntaxException, ParseException;
}
