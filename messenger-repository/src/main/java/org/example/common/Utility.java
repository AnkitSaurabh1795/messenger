package org.example.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utility {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> convertObjectToMap(Object object) {
        return object instanceof Map ? (Map)object : (Map)objectMapper.convertValue(object, TypeFactory.defaultInstance()
                .constructMapLikeType(HashMap.class, String.class, Object.class));
    }

    public static <T> T readObject(String document, Class<T> cls) throws JsonProcessingException {
        T result = null;
        if(Strings.isNotBlank(document)){
            try {
                result = cls == String.class ? (T) document : objectMapper.readValue(document, cls);
            }
            catch (IOException ex) {
                throw ex;
            }
        }
        return result;

    }

    public static String writeString(Object object) throws JsonProcessingException {
        try {
            return object instanceof String ? (String) object : objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
