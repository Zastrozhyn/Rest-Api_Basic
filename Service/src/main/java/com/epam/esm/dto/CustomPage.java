package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomPage<T> extends RepresentationModel<CustomPage<T>> {
    private List<T> content;
    private int currentPage;
    private int pageSize;
}