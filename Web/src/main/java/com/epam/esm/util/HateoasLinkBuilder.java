package com.epam.esm.util;

import com.epam.esm.dto.CustomPage;

public interface HateoasLinkBuilder<T> {
    String UPDATE = "update";
    String DELETE = "delete";
    String ALL = "all";
    String ID = "Id";
    void buildLinks(T t);
    void buildSelfLink(T t);
    void buildAllLinks(CustomPage<T> customPage);
}
