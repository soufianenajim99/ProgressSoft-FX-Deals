package com.progresssoft_fx_deals.tech.services;

import com.progresssoft_fx_deals.tech.dto.DealRequestDto;
import com.progresssoft_fx_deals.tech.dto.DealResponseDto;

import java.util.List;

public interface DealService {
    DealResponseDto createDeal(DealRequestDto dealRequest);
    DealResponseDto getDealById(String dealId);
    List<DealResponseDto> getAllDeals();
}
