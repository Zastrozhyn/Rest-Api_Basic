package com.epam.esm.entity.dto;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@SqlResultSetMapping(name = "UserDtoMapping",
classes = {
        @ConstructorResult(
                targetClass = UserDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "totalCost", type = BigDecimal.class)
                }
        )
})
public class UserDto {
    @Id
    private Long id;
    private String name;
    private BigDecimal totalCost;
}
