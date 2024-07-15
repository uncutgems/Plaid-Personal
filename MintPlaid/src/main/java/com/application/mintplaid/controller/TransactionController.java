package com.application.mintplaid.controller;

import com.application.mintplaid.dto.TransactionRequest;
import com.application.mintplaid.entity.Transaction;
import com.application.mintplaid.service.TransactionServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/get-transaction")
@RequiredArgsConstructor
public class TransactionController {
    private static final String dateFormat = "dd-MM-yyyy";

    private final TransactionServiceInterface transactionService;
    @PostMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@RequestBody TransactionRequest transactionRequest)
            throws ParseException {

        List<Transaction> transactions = transactionService.findTransactionByDate(transactionRequest);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

//    @GetMapping("/populate-transaction")
//    public ResponseEntity<String> populateTransaction() throws URISyntaxException, ParseException {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String accessToken = user.getAccessToken();
//        String message = transactionService.populateTransaction(accessToken);
//        return new ResponseEntity<>(message, HttpStatus.OK);
//    }
}
