package com.sleepypoem.testplatform.config;

import org.modelmapper.ModelMapper;

public class MapperProvider {

    private static ModelMapper instance;

    private MapperProvider() {
    }

    public static ModelMapper getMapper() {
        if (instance == null) {
            instance = new ModelMapper();

        }
        return instance;
    }

}
