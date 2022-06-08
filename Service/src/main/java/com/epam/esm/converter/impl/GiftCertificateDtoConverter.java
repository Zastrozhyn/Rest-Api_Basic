package com.epam.esm.converter.impl;

import com.epam.esm.converter.DtoConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class GiftCertificateDtoConverter implements DtoConverter<GiftCertificateDto, GiftCertificate> {
    private final TagDtoConverter converter;

    @Autowired
    public GiftCertificateDtoConverter(TagDtoConverter converter) {
        this.converter = converter;
    }


    @Override
    public GiftCertificate convertFromDto(GiftCertificateDto giftCertificateDto) {
        List<Tag> tagList = giftCertificateDto.getTags().stream().map(converter::convertFromDto).toList();
        return GiftCertificate.builder()
                .duration(giftCertificateDto.getDuration())
                .createDate(giftCertificateDto.getCreateDate())
                .description(giftCertificateDto.getDescription())
                .lastUpdateDate(giftCertificateDto.getLastUpdateDate())
                .tags(new HashSet<>(tagList))
                .price(giftCertificateDto.getPrice())
                .id(giftCertificateDto.getId())
                .name(giftCertificateDto.getName())
                .build();
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate certificate) {
        List<TagDto> tagList = certificate.getTags().stream().map(converter::convertToDto).toList();
        return GiftCertificateDto.builder()
                .duration(certificate.getDuration())
                .createDate(certificate.getCreateDate())
                .description(certificate.getDescription())
                .lastUpdateDate(certificate.getLastUpdateDate())
                .tags(new HashSet<>(tagList))
                .price(certificate.getPrice())
                .id(certificate.getId())
                .name(certificate.getName())
                .build();
    }
}
