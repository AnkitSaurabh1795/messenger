package org.example.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utility {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> convertObjectToMap(Object object) {
        return object instanceof Map ? (Map)object : (Map)objectMapper.convertValue(object, TypeFactory.defaultInstance()
                .constructMapLikeType(HashMap.class, String.class, Object.class));
    }

    public static <T> T readObject(String document, Class<T> cls) throws IOException {
        T result = null;
        if(!StringUtils.isEmpty(document)){
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
