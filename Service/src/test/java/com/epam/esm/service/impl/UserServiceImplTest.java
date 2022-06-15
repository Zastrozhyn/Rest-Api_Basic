package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.validator.UserValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private static final long USER_ID = 1;
    private static final String USER_NAME = "User";
    private static final String WRONG_USER_NAME = "U";
    private static final int EXPECTED_ERROR_CODE_WRONG_NAME = ExceptionCode.NOT_VALID_USER_NAME.getErrorCode();
    private static final int EXPECTED_ERROR_CODE_USER_NOT_FOUND = ExceptionCode.USER_NOT_FOUND.getErrorCode();
    private static User user;
    private UserServiceImpl service;
    private UserValidator validator;
    private UserDaoImpl userDao;

    @BeforeEach
    void setUp(){
        userDao = Mockito.mock(UserDaoImpl.class);
        validator = new UserValidator();
        service = new UserServiceImpl(userDao, validator);
    }

    @BeforeAll
    static void init(){
        user = new User(USER_ID, USER_NAME);
    }

    @AfterEach
    void afterEachTest(){
        verifyNoMoreInteractions(userDao);
    }


    @Test
    void createTest(){
        when(userDao.create(user)).thenReturn(user);
        User actual = service.create(user);
        assertThat(actual, is(equalTo(user)));
        verify(userDao).create(user);
    }

    @Test
    void createTestThrows(){
        when(userDao.create(user)).thenReturn(user);
        user.setName(WRONG_USER_NAME);
        EntityException actualException = assertThrows(EntityException.class, () -> service.create(user));
        assertThat(actualException.getErrorCode(), is(equalTo(EXPECTED_ERROR_CODE_WRONG_NAME)));
    }

    @Test
    void FindUserTest(){
        when(userDao.findUser(USER_ID)).thenReturn(user);
        User actual = service.findUser(USER_ID);
        assertThat(actual, is(equalTo(user)));
        verify(userDao).findUser(USER_ID);
    }

    @Test
    void FindUserTestThrows(){
        when(userDao.findUser(USER_ID)).thenReturn(null);
        EntityException actualException = assertThrows(EntityException.class, () -> service.findUser(USER_ID));
        assertThat(actualException.getErrorCode(), is(equalTo(EXPECTED_ERROR_CODE_USER_NOT_FOUND)));
        verify(userDao).findUser(USER_ID);
    }

    @Test
    void updateTest(){
        when(userDao.update(user)).thenReturn(user);
        when(userDao.findUser(USER_ID)).thenReturn(user);
        User actual = service.update(user, USER_ID);
        assertThat(actual, is(equalTo(user)));
        verify(userDao).update(user);
        verify(userDao).findUser(USER_ID);
    }

    @Test
    void updateUserTestThrows(){
        when(userDao.findUser(USER_ID)).thenReturn(null);
        EntityException actualException = assertThrows(EntityException.class, () -> service.update(user,USER_ID));
        assertThat(actualException.getErrorCode(), is(equalTo(EXPECTED_ERROR_CODE_USER_NOT_FOUND)));
        verify(userDao).findUser(USER_ID);
    }

    @Test
    void deleteUserTestThrows(){
        when(userDao.findUser(USER_ID)).thenReturn(null);
        EntityException actualException = assertThrows(EntityException.class, () -> service.delete(USER_ID));
        assertThat(actualException.getErrorCode(), is(equalTo(EXPECTED_ERROR_CODE_USER_NOT_FOUND)));
        verify(userDao).findUser(USER_ID);
    }
}
