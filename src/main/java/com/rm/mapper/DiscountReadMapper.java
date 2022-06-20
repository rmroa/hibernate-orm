package com.rm.mapper;

import com.rm.dto.DiscountReadDto;
import com.rm.entity.Discount;

public class DiscountReadMapper implements Mapper<Discount, DiscountReadDto> {

    @Override
    public DiscountReadDto mapFrom(Discount object) {
        return new DiscountReadDto(
                object.getId(),
                object.getDiscountInfo().getAmountOfDiscount()
        );
    }
}
