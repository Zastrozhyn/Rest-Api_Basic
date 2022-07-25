package com.epam.esm.service.impl;

import com.epam.esm.dao.dataJpa.OrderDaoJpa;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.util.PaginationUtil.checkPage;
import static com.epam.esm.util.PaginationUtil.checkPageSize;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDaoJpa orderDao;
    private final UserService userService;
    private final GiftCertificateService certificateService;

    @Autowired
    public OrderServiceImpl(OrderDaoJpa orderDao, UserService userService, GiftCertificateService certificateService) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.certificateService = certificateService;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Order create(Long userId, List<Long> certificates) {
        userService.isUserExist(userId);
        Order order = new Order();
        order.setUser(userService.findById(userId));
        order.setCertificateList(certificateService.findAllById(certificates));
        return orderDao.save(order);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Order findById(Long id) {
        return orderDao.findById(id).
                orElseThrow(() -> new EntityException(ExceptionCode.ORDER_NOT_FOUND.getErrorCode()));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<Order> findAll(Integer page, Integer pageSize) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return orderDao.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {
        isOrderExist(id);
        orderDao.deleteById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Order> findAllUsersOrder(Long userId, Integer page, Integer pageSize) {
        userService.isUserExist(userId);
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return orderDao.findAllByUser(userService.findById(userId), PageRequest.of(page, pageSize)).getContent();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Order update(Order order, Long orderId) {
        isOrderExist(orderId);
        order.setId(orderId);
        return orderDao.save(order);
    }

    private void isOrderExist (Long orderId){
        if (!orderDao.existsById(orderId)){
            throw new EntityException(ExceptionCode.ORDER_NOT_FOUND.getErrorCode());
        }
    }
}
