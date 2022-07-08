package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.util.PaginationUtil.*;

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
        userService.isUserExist(userId);
        Order order = new Order();
        order.setUser(userService.findById(userId));
        order.setCertificateList(certificateService.findAllById(certificates));
        return orderDao.create(order);
    }

    @Override
    public Order findById(Long id) {
        Order order = orderDao.findById(id);
        if(order == null){
            throw new EntityException(ExceptionCode.ORDER_NOT_FOUND.getErrorCode());
        }
        return order;
    }

    @Override
    public List<Order> findAll(Integer page, Integer pageSize) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return orderDao.findAll(calculateOffset(pageSize,page), pageSize);
    }

    @Override
    public void delete(Long id) {
        isOrderExist(id);
        orderDao.delete(id);
    }

    @Override
    @Transactional
    public List<Order> findAllUsersOrder(Long userId, Integer page, Integer pageSize) {
        userService.isUserExist(userId);
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return orderDao.findAllUsersOrder(userService.findById(userId), calculateOffset(pageSize,page), pageSize);
    }

    @Override
    @Transactional
    public Order update(Order order, Long orderId) {
        isOrderExist(orderId);
        order.setId(orderId);
        return orderDao.update(order);
    }

    private void isOrderExist (Long orderId){
        if (!orderDao.exists(orderId)){
            throw new EntityException(ExceptionCode.ORDER_NOT_FOUND.getErrorCode());
        }
    }
}
