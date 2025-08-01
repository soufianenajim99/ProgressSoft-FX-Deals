package com.progresssoft_fx_deals.tech.exceptions;

import lombok.Getter;

@Getter
public class DuplicateDealException extends RuntimeException {
    private final String dealId;

    public DuplicateDealException(String dealId) {
        super(String.format("Deal with ID '%s' already exists", dealId));
        this.dealId = dealId;
    }
}
