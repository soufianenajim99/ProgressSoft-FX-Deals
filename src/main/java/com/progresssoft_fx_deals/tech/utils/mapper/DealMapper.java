package com.progresssoft_fx_deals.tech.utils.mapper;

import com.progresssoft_fx_deals.tech.dto.DealRequestDto;
import com.progresssoft_fx_deals.tech.dto.DealResponseDto;
import com.progresssoft_fx_deals.tech.models.Deal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DealMapper {

    Deal toEntity(DealRequestDto dto);

    DealResponseDto toDto(Deal entity);

    List<DealResponseDto> toDtoList(List<Deal> entities);

}
