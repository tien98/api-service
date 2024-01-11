package com.service.apiservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtils {
    static ObjectMapper mapper = new ObjectMapper();

    public static String convertObjectToJsonString(Object obj) {
        String jsonString = "";
        try {
            if (obj != null) {
                jsonString = mapper.writeValueAsString(obj);
                return jsonString;
            }
        } catch (JsonProcessingException e) {
            log.error("Error while convert object to json!");
            throw new RuntimeException(e);
        }
        return null;
    }

    public static <T> T convertJsonToObject(String jsonString, Class<T> clazz) {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Error while convert object to json!");
            throw new RuntimeException(e);
        }
    }
}
