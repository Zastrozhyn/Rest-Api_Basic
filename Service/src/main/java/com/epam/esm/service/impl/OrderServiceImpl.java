package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateDtoConverter;
import com.epam.esm.converter.impl.OrderDtoConverter;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.util.ApplicationUtil.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserService userService;
    private final GiftCertificateService certificateService;
    private final UserDao userDao;
    private final GiftCertificateDtoConverter certificateConverter;
    private final OrderDtoConverter converter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserService userService, GiftCertificateService certificateService,
                            UserDao userDao, GiftCertificateDtoConverter certificateConverter,
                            OrderDtoConverter converter) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.certificateService = certificateService;
        this.userDao = userDao;
        this.certificateConverter = certificateConverter;
        this.converter = converter;
    }

    @Override
    @Transactional
    public OrderDto create(Long userId, List<Long> certificates) {
        Order order = new Order();
        User user = userDao.findUser(userId);
        if (userService.isUserExist(user)){
            order.setUser(user);
            certificates.stream()
                    .map(certificateService::findById)
                    .map(certificateConverter::convertFromDto)
                    .forEach(order::addCertificate);
        }
        return converter.convertToDto(orderDao.create(order));
    }

    @Override
    public OrderDto findOrder(Long id) {
        return converter.convertToDto(orderDao.findOrder(id));
    }

    @Override
    public List<OrderDto> findAll(Integer page, Integer pageSize) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return orderDao.findAll(calculateOffset(pageSize,page), pageSize)
                .stream()
                .map(converter::convertToDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        orderDao.delete(id);
    }

    @Override
    @Transactional
    public List<OrderDto> findAllUsersOrder(Long id, Integer page, Integer pageSize) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        User user = userDao.findUser(id);
        userService.isUserExist(user);
        return orderDao.findAllUsersOrder(user, calculateOffset(pageSize,page), pageSize)
                .stream()
                .map(converter::convertToDto)
                .toList();
    }

    @Override
    public OrderDto update(OrderDto orderDto, Long id) {
        orderDto.setId(id);
        Order order = converter.convertFromDto(orderDto);
        return converter.convertToDto(orderDao.update(order));
    }

}
