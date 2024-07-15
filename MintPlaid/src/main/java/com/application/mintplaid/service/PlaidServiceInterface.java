package com.application.mintplaid.service;

import com.application.mintplaid.dto.LinkPlaidRequest;
import com.application.mintplaid.dto.LinkPlaidResponse;

import java.net.URISyntaxException;

public interface PlaidServiceInterface {
    LinkPlaidResponse initialLink() throws URISyntaxException;
    LinkPlaidResponse fixLink(LinkPlaidRequest plaidRequest) throws URISyntaxException;
}
