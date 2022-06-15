package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {
    private static final long ORDER_ID = 1;
    private static final long USER_ID = 1;
    private static final long CERTIFICATE_ID = 1;
    private static final String USER_NAME = "User";
    private static final LocalDateTime ORDER_DATE = LocalDateTime.of(1990, 10, 10, 10, 10);
    public static final BigDecimal COST = BigDecimal.TEN;
    private static final int EXPECTED_ERROR_CODE_ORDER_NOT_FOUND = ExceptionCode.ORDER_NOT_FOUND.getErrorCode();

    private OrderDao orderDao;
    private UserService userService;
    private GiftCertificateService certificateService;
    private OrderServiceImpl service;
    private static Order order;
    private static User user;
    private static GiftCertificate certificate;

    @BeforeEach
    void setUp(){
        certificateService = Mockito.mock(GiftCertificateServiceImpl.class);
        orderDao = Mockito.mock(OrderDaoImpl.class);
        userService = Mockito.mock(UserServiceImpl.class);
        service = new OrderServiceImpl(orderDao,userService,certificateService);
    }

    @BeforeAll
    static void init(){
        user = new User(USER_ID, USER_NAME);
        certificate = new GiftCertificate();
        certificate.setId(CERTIFICATE_ID);
        order = Order.builder()
                .user(user)
                .orderDate(ORDER_DATE)
                .id(ORDER_ID)
                .cost(COST)
                .certificateList(List.of(certificate))
                .build();
    }

    @AfterEach
    void afterEachTest(){
        verifyNoMoreInteractions(orderDao);
        verifyNoMoreInteractions(certificateService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void findOrderTest(){
        when(orderDao.findOrder(ORDER_ID)).thenReturn(order);
        Order actual = service.findOrder(ORDER_ID);
        assertThat(actual, is(equalTo(order)));
        verify(orderDao).findOrder(ORDER_ID);
    }

    @Test
    void findOrderTestThrows(){
        when(orderDao.findOrder(ORDER_ID)).thenReturn(null);
        EntityException actualException = assertThrows(EntityException.class, () -> service.findOrder(ORDER_ID));
        assertThat(actualException.getErrorCode(), is(equalTo(EXPECTED_ERROR_CODE_ORDER_NOT_FOUND)));
        verify(orderDao).findOrder(ORDER_ID);
    }

    @Test
    void updateTest(){
        when(orderDao.update(order)).thenReturn(order);
        when(orderDao.findOrder(ORDER_ID)).thenReturn(order);
        Order actual = service.update(order, ORDER_ID);
        assertThat(actual, is(equalTo(order)));
        verify(orderDao).findOrder(ORDER_ID);
        verify(orderDao).update(order);
    }

    @Test
    void updateOrderTestThrows(){
        when(orderDao.findOrder(ORDER_ID)).thenReturn(null);
        EntityException actualException = assertThrows(EntityException.class, () -> service.update(order, ORDER_ID));
        assertThat(actualException.getErrorCode(), is(equalTo(EXPECTED_ERROR_CODE_ORDER_NOT_FOUND)));
        verify(orderDao).findOrder(ORDER_ID);
    }
}
