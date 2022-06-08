package com.epam.esm.converter.impl;

import com.epam.esm.converter.DtoConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDtoConverter implements DtoConverter<OrderDto, Order> {
    private final GiftCertificateDtoConverter certificateDtoConverter;
    private final UserDtoConverter userDtoConverter;

    @Autowired
    public OrderDtoConverter(GiftCertificateDtoConverter certificateDtoConverter, UserDtoConverter userDtoConverter) {
        this.certificateDtoConverter = certificateDtoConverter;
        this.userDtoConverter = userDtoConverter;
    }


    @Override
    public Order convertFromDto(OrderDto orderDto) {
        List<GiftCertificate> certificates =
                orderDto.getCertificateList().stream()
                .map(certificateDtoConverter::convertFromDto).toList();
        return Order.builder()
                .orderDate(orderDto.getOrderDate())
                .cost(orderDto.getCost())
                .id(orderDto.getId())
                .certificateList(certificates)
                .user(userDtoConverter.convertFromDto(orderDto.getUser()))
                .build();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        List<GiftCertificateDto> certificates = order.getCertificateList().stream()
                .map(certificateDtoConverter::convertToDto).toList();
        return OrderDto.builder()
                .orderDate(order.getOrderDate())
                .cost(order.getCost())
                .id(order.getId())
                .certificateList(certificates)
                .user(userDtoConverter.convertToDto(order.getUser()))
                .build();
    }
}
