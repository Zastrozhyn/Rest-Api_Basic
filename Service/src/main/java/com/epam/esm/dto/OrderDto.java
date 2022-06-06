package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto extends RepresentationModel<OrderDto> {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal cost;
    private User user;
    private List<GiftCertificate> certificateList;

    public OrderDto (Order order){
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.cost = order.getCost();
        this.user = order.getUser();
        this.certificateList = order.getCertificateList();
    }

}
