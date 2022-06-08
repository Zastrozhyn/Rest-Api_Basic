package com.epam.esm.converter.impl;

import com.epam.esm.converter.DtoConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDtoConverter implements DtoConverter<TagDto, Tag> {
    @Override
    public Tag convertFromDto(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }

    @Override
    public TagDto convertToDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
