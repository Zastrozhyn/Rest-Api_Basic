package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto extends RepresentationModel<OrderDto> {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal cost;
    private UserDto user;
    private List<GiftCertificateDto> certificateList;

}
