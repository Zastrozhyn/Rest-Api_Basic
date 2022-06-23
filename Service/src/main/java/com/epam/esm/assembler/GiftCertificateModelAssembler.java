package com.epam.esm.assembler;

import com.epam.esm.dto.GiftCertificateModel;
import com.epam.esm.dto.TagModel;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class GiftCertificateModelAssembler
        implements RepresentationModelAssembler<GiftCertificate, GiftCertificateModel> {

    private final TagModelAssembler converter;

    @Autowired
    public GiftCertificateModelAssembler(TagModelAssembler converter) {
        this.converter = converter;
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificate entity) {
        List<TagModel> tagList = entity.getTags().stream().map(converter::toModel).toList();
        return GiftCertificateModel.builder()
                .duration(entity.getDuration())
                .createDate(entity.getCreateDate())
                .description(entity.getDescription())
                .lastUpdateDate(entity.getLastUpdateDate())
                .tags(new HashSet<>(tagList))
                .price(entity.getPrice())
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public CollectionModel<GiftCertificateModel> toCollectionModel(Iterable<? extends GiftCertificate> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
