package com.epam.esm.assembler;

import com.epam.esm.dto.UserModel;
import com.epam.esm.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {

    @Override
    public UserModel toModel(User entity) {
        return UserModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
