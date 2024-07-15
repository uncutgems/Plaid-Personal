package com.application.mintplaid.controller;

import com.application.mintplaid.dto.LinkPlaidRequest;
import com.application.mintplaid.dto.LinkPlaidResponse;
import com.application.mintplaid.plaid.exchange_token.PublicTokenRequest;
import com.application.mintplaid.service.PlaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/plaidSync")
@RequiredArgsConstructor
public class PlaidSyncController {
    private final PlaidService plaidService;
    @GetMapping("/initial")
    public ResponseEntity<LinkPlaidResponse> initialPlaidLink() throws URISyntaxException {

        LinkPlaidResponse linkPlaidResponse = plaidService.initialLink();
        return new ResponseEntity<>(linkPlaidResponse, HttpStatus.OK);
    }

    @PostMapping("/fix")
    public ResponseEntity<LinkPlaidResponse> fixPlaidLink(@RequestBody LinkPlaidRequest linkPlaidRequest) throws URISyntaxException {
        LinkPlaidResponse linkPlaidResponse = plaidService.fixLink(LinkPlaidRequest.builder()
                .item(linkPlaidRequest.getItem()).build());
        return new ResponseEntity<>(linkPlaidResponse, HttpStatus.OK);
    }

    @PostMapping("/exchangeToken")
    public ResponseEntity<?> exchangeToken(@RequestBody PublicTokenRequest publicTokenRequest) throws URISyntaxException {
        if (plaidService.exchangePublicToken(publicTokenRequest))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
