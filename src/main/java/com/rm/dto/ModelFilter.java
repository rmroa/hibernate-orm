package com.rm.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ModelFilter {

    String vehicleType;

    String brand;
}
