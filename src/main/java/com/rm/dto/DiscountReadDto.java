package com.rm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class DiscountReadDto {

    private final Integer id;

    private final Long amountOfDiscount;
}
