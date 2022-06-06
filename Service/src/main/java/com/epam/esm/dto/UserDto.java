package com.epam.esm.dto;

import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    private String name;

    public UserDto (User user){
        this.id = user.getId();
        this.name = user.getName();
    }

    public User convertToUser(){
        return User.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
