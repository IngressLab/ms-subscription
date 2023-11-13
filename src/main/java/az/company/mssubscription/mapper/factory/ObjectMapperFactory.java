package az.company.mssubscription.mapper.factory;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public enum ObjectMapperFactory {
    OBJECT_MAPPER_FACTORY;

    public ObjectMapper getLogIgnoreObjectMapper() {
        return JsonMapper.builder()
                .configure(MapperFeature.USE_ANNOTATIONS, false)
                .build();
    }
}