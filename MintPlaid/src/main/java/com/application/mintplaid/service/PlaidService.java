package com.application.mintplaid.service;

import com.application.mintplaid.config.Constant;
import com.application.mintplaid.dto.LinkPlaidRequest;
import com.application.mintplaid.dto.LinkPlaidResponse;
import com.application.mintplaid.entity.Item;
import com.application.mintplaid.entity.User;
import com.application.mintplaid.plaid.exchange_token.*;
import com.application.mintplaid.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlaidService implements PlaidServiceInterface {
    private final ItemRepository itemRepository;
    @Value("${application.plaid.sandboxEnv}")
    private String sandboxEnv;
    @Value("${application.plaid.sandboxClientId}")
    private String sandboxClientId;
    @Value("${application.plaid.sandboxSecret}")
    private String sandboxSecret;

    @Override
    public LinkPlaidResponse initialLink() throws URISyntaxException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        URI uri = new URI(sandboxEnv + "/link/token/create");
        RestTemplate restTemplate = new RestTemplate();
        PlaidUserContract plaidUserContract = PlaidUserContract.builder()
                .clientUserId(user.getUsername()).build();
        LinkTokenRequest linkTokenRequest = LinkTokenRequest.builder()
                .clientId(sandboxClientId)
                .secret(sandboxSecret)
                .clientName(Constant.appName)
                .language("en")
                .countryCodes(Constant.countries)
                .plaidUserContract(plaidUserContract)
                .products(Constant.products)
                .build();
        ResponseEntity<LinkPlaidResponse> response =
                restTemplate.postForEntity(uri, linkTokenRequest, LinkPlaidResponse.class);
        System.out.println("Link token response: " + response.getBody());
        return response.getBody();
    }

    @Override
    public LinkPlaidResponse fixLink(@NotNull LinkPlaidRequest plaidRequest) throws URISyntaxException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item item = itemRepository.findByItemId(plaidRequest.getItem());
        LinkPlaidResponse linkPlaidResponse = new LinkPlaidResponse();
        if (Objects.equals(item.getUserId(), user.getId())) {
            URI uri = new URI(sandboxEnv + "/link/token/create");
            RestTemplate restTemplate = new RestTemplate();
            PlaidUserContract plaidUserContract = PlaidUserContract.builder()
                    .clientUserId(user.getUsername()).build();
            LinkTokenRequest linkTokenRequest = LinkTokenRequest.builder()
                    .clientId(sandboxClientId)
                    .secret(sandboxSecret)
                    .clientName(Constant.appName)
                    .language("en")
                    .countryCodes(Constant.countries)
                    .plaidUserContract(plaidUserContract)
                    .products(Constant.products)
                    .accessToken(item.getAccessToken())
                    .build();
            ResponseEntity<LinkPlaidResponse> response =
                    restTemplate.postForEntity(uri, linkTokenRequest, LinkPlaidResponse.class);
            linkPlaidResponse = response.getBody();
        }

        return linkPlaidResponse;
    }

    public boolean exchangePublicToken (@NotNull PublicTokenRequest publicTokenRequest) throws URISyntaxException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ExchangeTokenRequestContract exchangeTokenRequestContract = new ExchangeTokenRequestContract();
        exchangeTokenRequestContract.setClientId(sandboxClientId);
        exchangeTokenRequestContract.setSecret(sandboxSecret);
        exchangeTokenRequestContract.setPublicToken(publicTokenRequest.getPublicToken());
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(sandboxEnv + "/item/public_token/exchange");
        ResponseEntity<ExchangeTokenResponseContract> response =
                restTemplate.postForEntity(uri, exchangeTokenRequestContract, ExchangeTokenResponseContract.class);
        if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
            ExchangeTokenResponseContract exchangeTokenResponseContract = response.getBody();
            Item item = Item.builder()
                    .itemId(exchangeTokenResponseContract.getItemId())
                    .accessToken(exchangeTokenResponseContract.getAccessToken())
                    .userId(user.getId())
                    .actionRequired(Item.Action.None)
                    .build();
            itemRepository.save(item);
            return true;
        }
        return false;
    }
}
