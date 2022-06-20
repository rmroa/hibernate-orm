package com.rm.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
