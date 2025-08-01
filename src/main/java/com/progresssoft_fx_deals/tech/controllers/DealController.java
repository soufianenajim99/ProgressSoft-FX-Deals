package com.progresssoft_fx_deals.tech.controllers;

import com.progresssoft_fx_deals.tech.dto.DealRequestDto;
import com.progresssoft_fx_deals.tech.dto.DealResponseDto;
import com.progresssoft_fx_deals.tech.services.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @PostMapping
    public ResponseEntity<DealResponseDto> createDeal(@RequestBody @Valid DealRequestDto dealRequest) {
        DealResponseDto response = dealService.createDeal(dealRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealResponseDto> getDealById(@PathVariable String id) {
        DealResponseDto response = dealService.getDealById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DealResponseDto>> getAllDeals() {
        List<DealResponseDto> response = dealService.getAllDeals();
        return ResponseEntity.ok(response);
    }
}
