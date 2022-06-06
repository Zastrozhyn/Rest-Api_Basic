package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class TagDto extends RepresentationModel<TagDto> {
    private Long id;
    private String name;

    public TagDto (Tag tag){
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
