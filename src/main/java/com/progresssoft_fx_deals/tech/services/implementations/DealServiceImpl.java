package com.progresssoft_fx_deals.tech.services.implementations;

import lombok.extern.slf4j.Slf4j;
import com.progresssoft_fx_deals.tech.dto.DealRequestDto;
import com.progresssoft_fx_deals.tech.dto.DealResponseDto;
import com.progresssoft_fx_deals.tech.exceptions.DealNotFoundException;
import com.progresssoft_fx_deals.tech.exceptions.DuplicateDealException;
import com.progresssoft_fx_deals.tech.models.Deal;
import com.progresssoft_fx_deals.tech.repositories.DealRepository;
import com.progresssoft_fx_deals.tech.services.DealService;
import com.progresssoft_fx_deals.tech.utils.mapper.DealMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;

    @Override
    public DealResponseDto createDeal(DealRequestDto dealRequest) {
        log.info("Creating deal with ID: {}", dealRequest.id());

        if (dealRepository.existsById(dealRequest.id())) {
            log.warn("Duplicate deal attempt for ID: {}", dealRequest.id());
            throw new DuplicateDealException(dealRequest.id());
        }

        Deal deal = dealMapper.toEntity(dealRequest);
        Deal savedDeal = dealRepository.save(deal);

        log.info("Successfully created deal with ID: {}", dealRequest.id());
        return dealMapper.toDto(savedDeal);
    }

    @Override
    public DealResponseDto getDealById(String dealId) {
        log.debug("Fetching deal with ID: {}", dealId);

        return dealRepository.findById(dealId)
                .map(deal -> {
                    log.debug("Found deal with ID: {}", dealId);
                    return dealMapper.toDto(deal);
                })
                .orElseThrow(() -> {
                    log.error("Deal not found with ID: {}", dealId);
                    return new DealNotFoundException(dealId);
                });
    }

    @Override
    public List<DealResponseDto> getAllDeals() {
        log.debug("Fetching all deals");
        List<Deal> deals = dealRepository.findAll();
        log.info("Retrieved {} deals", deals.size());
        return dealMapper.toDtoList(deals);
    }
}

