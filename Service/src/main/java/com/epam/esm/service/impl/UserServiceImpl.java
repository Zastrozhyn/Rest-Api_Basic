package com.epam.esm.service.impl;

import com.epam.esm.dao.dataJpa.UserDaoJpa;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.util.PaginationUtil.checkPage;
import static com.epam.esm.util.PaginationUtil.checkPageSize;

@Service
public class UserServiceImpl implements UserService {
    private final UserDaoJpa userDao;
    private final UserValidator validator;

    public UserServiceImpl(UserDaoJpa userDao, UserValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    @Override
    @Transactional
    public User create(User user) {
        isUserNameValid(user);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        return userDao.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public User findById(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode()));
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name)
                .orElseThrow(() -> new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode()));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findAll(Integer pageSize, Integer page) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return userDao.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long userId) {
        if (isUserExist(userId)){
            userDao.deleteById(userId);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User update(User user, Long userId) {
        if (isUserExist(userId) && isUserNameValid(user)){
            user.setId(userId);
        }
        return userDao.save(user);
    }

    @Override
    public boolean isUserExist(Long userId){
        if (!userDao.existsById(userId)){
            throw new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    private boolean isUserNameValid(User user){
        if(!validator.isValid(user)){
            throw new EntityException(ExceptionCode.NOT_VALID_USER_NAME.getErrorCode());
        }
        return true;
    }
}
