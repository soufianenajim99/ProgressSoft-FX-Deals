package com.progresssoft_fx_deals.tech.exceptions;

import lombok.Getter;

@Getter
public class DealNotFoundException extends RuntimeException {
    private final String dealId;

    public DealNotFoundException(String dealId) {
        super(String.format("Deal with ID '%s' not found", dealId));
        this.dealId = dealId;
    }
}
