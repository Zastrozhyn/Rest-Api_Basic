package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
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

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserService userService, GiftCertificateService certificateService) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.certificateService = certificateService;
    }

    @Override
    @Transactional
    public Order create(Long userId, List<Long> certificates) {
        Order order = new Order();
        User user = userService.findUser(userId);
        userService.isUserExist(user);
        certificates.forEach(certificateService::isGiftCertificateExist);
        return orderDao.create(order);
    }

    @Override
    public Order findOrder(Long id) {
        Order order = orderDao.findOrder(id);
        isOrderExist(order);
        return order;
    }

    @Override
    public List<Order> findAll(Integer page, Integer pageSize) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return orderDao.findAll(calculateOffset(pageSize,page), pageSize);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderDao.delete(id);
    }

    @Override
    @Transactional
    public List<Order> findAllUsersOrder(Long id, Integer page, Integer pageSize) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        User user = userService.findUser(id);
        userService.isUserExist(user);
        return orderDao.findAllUsersOrder(user, calculateOffset(pageSize,page), pageSize);
    }

    @Override
    @Transactional
    public Order update(Order order, Long id) {
        isOrderExist(findOrder(id));
        order.setId(id);
        return orderDao.update(order);
    }

    private boolean isOrderExist (Order order){
        if (order == null){
            throw new EntityException(ExceptionCode.ORDER_NOT_FOUND.getErrorCode());
        }
        return true;
    }
}
