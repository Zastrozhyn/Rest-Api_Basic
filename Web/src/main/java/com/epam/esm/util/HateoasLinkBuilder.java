package com.epam.esm.util;

public interface HateoasLinkBuilder<T> {
    String UPDATE = "update";
    String DELETE = "delete";
    String ALL = "all";
    void buildLinks(T t);
}
