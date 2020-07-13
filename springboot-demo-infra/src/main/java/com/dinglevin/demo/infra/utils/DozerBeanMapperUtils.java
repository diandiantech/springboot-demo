/*
 * Copyright 2019-2020 [Levin]
 */
package com.dinglevin.demo.infra.utils;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class DozerBeanMapperUtils {
    private DozerBeanMapperUtils() { }

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    /**
     * Bean映射
     *
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        checkNotNull(destinationClass, "destinationClass is null");
        return source == null ? null : MAPPER.map(source, destinationClass);
    }

    /**
     * Bean映射
     *
     * @param source
     * @param target
     */
    public static void map(Object source, Object target) {
        checkNotNull(source, "source is null");
        checkNotNull(target, "target is null");
        MAPPER.map(source, target);
    }

    public static <T, S> List<T> mapList(Collection<S> sourceList, Class<T> destinationClass) {
        checkNotNull(destinationClass, "destinationClass is null");

        if (sourceList == null) {
            return Lists.newArrayList();
        }

        return sourceList.stream()
            .map(source -> MAPPER.map(source, destinationClass))
            .collect(Collectors.toList());
    }
}