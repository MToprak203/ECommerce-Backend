package com.ecommerce.website.dtos.mappers;

import org.modelmapper.ModelMapper;

public abstract class Mapper<A, B> {

    protected final ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public abstract B mapTo(A a);

    public abstract A mapFrom(B b);
}
