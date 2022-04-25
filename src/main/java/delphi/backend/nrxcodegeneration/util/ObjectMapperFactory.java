package delphi.backend.nrxcodegeneration.util;

import javax.inject.Singleton;

import io.micronaut.context.annotation.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;

@Factory
public class ObjectMapperFactory {

    @Singleton
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns instance of Object Mapper
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getInstance() {
        return mapper;
    }
}
