package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomPage<T> extends RepresentationModel<CustomPage<T>> {
    private List<T> content;
    private int currentPage;
    private int pageSize;
}
