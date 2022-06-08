package com.epam.esm.converter;

public interface DtoConverter<T, E> {
    E convertFromDto(T t);
    T convertToDto(E e);
}
