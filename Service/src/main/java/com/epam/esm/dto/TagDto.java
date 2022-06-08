package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto extends RepresentationModel<TagDto> {
    private Long id;
    private String name;

}