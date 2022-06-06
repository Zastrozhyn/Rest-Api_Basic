package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private int duration;
    private Set<Tag> tags;

    public GiftCertificateDto(GiftCertificate certificate) {
        this.id = certificate.getId();
        this.name = certificate.getName();
        this.description = certificate.getDescription();
        this.price = certificate.getPrice();
        this.createDate = certificate.getCreateDate();
        this.lastUpdateDate = certificate.getLastUpdateDate();
        this.duration = certificate.getDuration();
        this.tags = certificate.getTags();
    }
}
