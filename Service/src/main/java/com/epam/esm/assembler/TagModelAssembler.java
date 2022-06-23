package com.epam.esm.assembler;

import com.epam.esm.dto.TagModel;
import com.epam.esm.entity.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<Tag, TagModel> {

    @Override
    public TagModel toModel(Tag entity) {
        return TagModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public CollectionModel<TagModel> toCollectionModel(Iterable<? extends Tag> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
