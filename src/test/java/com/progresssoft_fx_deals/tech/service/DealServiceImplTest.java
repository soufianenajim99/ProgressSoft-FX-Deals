package com.progresssoft_fx_deals.tech.service;

import com.progresssoft_fx_deals.tech.dto.DealRequestDto;
import com.progresssoft_fx_deals.tech.dto.DealResponseDto;
import com.progresssoft_fx_deals.tech.exceptions.DealNotFoundException;
import com.progresssoft_fx_deals.tech.exceptions.DuplicateDealException;
import com.progresssoft_fx_deals.tech.models.Deal;
import com.progresssoft_fx_deals.tech.repositories.DealRepository;
import com.progresssoft_fx_deals.tech.services.implementations.DealServiceImpl;
import com.progresssoft_fx_deals.tech.utils.mapper.DealMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the DealServiceImpl class.
 *
 * Uses Mockito to mock dependencies DealRepository and DealMapper,
 * and verifies various functionalities of the deal management service.
 *
 * Covered tests:
 * - Successfully creating a new deal
 * - Handling duplicate deal ID on creation
 * - Retrieving a deal by ID (existing and not found cases)
 * - Retrieving all deals (with data and empty list)
 */
@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private DealMapper dealMapper;

    @InjectMocks
    private DealServiceImpl dealService;

    private final String DEAL_ID = "DEAL-123";
    private final LocalDateTime NOW = LocalDateTime.now();

    /**
     * Tests successful creation of a new deal.
     * Verifies the deal is saved if the ID does not exist,
     * and the response matches the expected data.
     */
    @Test
    void createDeal_shouldSuccessfullyCreateNewDeal() {
        DealRequestDto request = new DealRequestDto(
                DEAL_ID, "USD", "EUR", NOW, BigDecimal.valueOf(1000.50));
        Deal entity = new Deal();
        Deal savedEntity = new Deal();
        DealResponseDto expectedResponse = new DealResponseDto(
                DEAL_ID, "USD", "EUR", NOW, BigDecimal.valueOf(1000.50), NOW);

        when(dealRepository.existsById(DEAL_ID)).thenReturn(false);
        when(dealMapper.toEntity(request)).thenReturn(entity);
        when(dealRepository.save(entity)).thenReturn(savedEntity);
        when(dealMapper.toDto(savedEntity)).thenReturn(expectedResponse);

        DealResponseDto response = dealService.createDeal(request);

        assertNotNull(response);
        assertEquals(DEAL_ID, response.id());
        verify(dealRepository, times(1)).existsById(DEAL_ID);
        verify(dealRepository, times(1)).save(entity);
    }

    /**
     * Tests that creating a deal with an existing ID
     * throws a DuplicateDealException,
     * and that save is never called.
     */
    @Test
    void createDeal_shouldThrowWhenDuplicateId() {
        DealRequestDto request = new DealRequestDto(
                DEAL_ID, "USD", "EUR", NOW, BigDecimal.valueOf(1000.50));

        when(dealRepository.existsById(DEAL_ID)).thenReturn(true);

        assertThrows(DuplicateDealException.class, () -> dealService.createDeal(request));
        verify(dealRepository, never()).save(any());
    }

    @Test
    void getDealById_shouldReturnDealWhenExists() {
        Deal entity = new Deal();
        DealResponseDto expectedResponse = new DealResponseDto(
                DEAL_ID, "USD", "EUR", NOW, BigDecimal.valueOf(1000.50), NOW);

        when(dealRepository.findById(DEAL_ID)).thenReturn(Optional.of(entity));
        when(dealMapper.toDto(entity)).thenReturn(expectedResponse);

        DealResponseDto response = dealService.getDealById(DEAL_ID);

        assertNotNull(response);
        assertEquals(DEAL_ID, response.id());
        verify(dealRepository, times(1)).findById(DEAL_ID);
    }

    @Test
    void getDealById_shouldThrowWhenNotFound() {
        when(dealRepository.findById(DEAL_ID)).thenReturn(Optional.empty());

        assertThrows(DealNotFoundException.class, () -> dealService.getDealById(DEAL_ID));
    }

    @Test
    void getAllDeals_shouldReturnAllDeals() {
        Deal deal1 = new Deal();
        Deal deal2 = new Deal();
        List<Deal> entities = List.of(deal1, deal2);
        DealResponseDto dto1 = new DealResponseDto("1", "USD", "EUR", NOW, BigDecimal.ONE, NOW);
        DealResponseDto dto2 = new DealResponseDto("2", "GBP", "USD", NOW, BigDecimal.TEN, NOW);

        when(dealRepository.findAll()).thenReturn(entities);
        when(dealMapper.toDtoList(entities)).thenReturn(List.of(dto1, dto2));

        List<DealResponseDto> result = dealService.getAllDeals();

        assertEquals(2, result.size());
        verify(dealRepository, times(1)).findAll();
    }

    @Test
    void getAllDeals_shouldReturnEmptyListWhenNoDeals() {
        when(dealRepository.findAll()).thenReturn(List.of());

        List<DealResponseDto> result = dealService.getAllDeals();

        assertTrue(result.isEmpty());
    }
}
