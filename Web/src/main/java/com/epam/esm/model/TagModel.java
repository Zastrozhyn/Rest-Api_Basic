package com.epam.esm.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagModel extends RepresentationModel<TagModel> {
    private Long id;
    private String name;
}
