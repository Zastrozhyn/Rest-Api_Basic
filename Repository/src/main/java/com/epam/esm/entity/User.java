package com.epam.esm.entity;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;
}
