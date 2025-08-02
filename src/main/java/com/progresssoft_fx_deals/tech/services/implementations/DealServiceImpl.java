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

/**
 * Implementation of DealService interface.
 * Handles business logic for creating and retrieving deals.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;

    /**
     * Creates a new deal if it doesn't already exist.
     *
     * @param dealRequest the deal data from the client
     * @return the created deal as a DTO
     * @throws DuplicateDealException if a deal with the same ID already exists
     */
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

    /**
     * Retrieves a deal by its ID.
     *
     * @param dealId the ID of the deal to retrieve
     * @return the corresponding deal as a DTO
     * @throws DealNotFoundException if no deal is found with the given ID
     */
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

    /**
     * Retrieves all deals from the repository.
     *
     * @return a list of all deals as DTOs
     */
    @Override
    public List<DealResponseDto> getAllDeals() {
        log.debug("Fetching all deals");
        List<Deal> deals = dealRepository.findAll();
        log.info("Retrieved {} deals", deals.size());
        return dealMapper.toDtoList(deals);
    }
}

