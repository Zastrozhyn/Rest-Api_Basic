package com.epam.esm.dao.dataJpa;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDaoJpa extends JpaRepository<User,Long> {
    Optional<User> findByName (String name);
}
