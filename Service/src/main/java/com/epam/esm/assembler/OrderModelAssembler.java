package com.epam.esm.assembler;

import com.epam.esm.dto.GiftCertificateModel;
import com.epam.esm.dto.OrderModel;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, OrderModel> {
    private final GiftCertificateModelAssembler certificateDtoConverter;
    private final UserModelAssembler userDtoConverter;

    @Autowired
    public OrderModelAssembler(GiftCertificateModelAssembler certificateDtoConverter, UserModelAssembler userDtoConverter) {
        this.certificateDtoConverter = certificateDtoConverter;
        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public OrderModel toModel(Order entity) {
        List<GiftCertificateModel> certificates = entity.getCertificateList().stream()
                .map(certificateDtoConverter::toModel).toList();
        return OrderModel.builder()
                .orderDate(entity.getOrderDate())
                .cost(entity.getCost())
                .id(entity.getId())
                .certificateList(certificates)
                .user(userDtoConverter.toModel(entity.getUser()))
                .build();
    }

    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends Order> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
