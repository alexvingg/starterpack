package br.com.starterpack.core.util;

import io.jsonwebtoken.lang.Collections;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class DinamicFilterUtil {

    public static CriteriaDefinition getDefinedCriteria(String operator, String attributeType, String attributePath, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class argumentClazz = (attributeType.equals("list")) ? Collection.class : Object.class;
        Class pathClazz = Criteria.class;
        String methodName = operator;
        Object convertedValue = DinamicFilterUtil.convertValueByType(attributeType, value);

        switch(operator) {
            case("like"): {
                methodName = "regex";
                Method customMethod = pathClazz.getMethod(methodName, String.class, String.class);
                return (CriteriaDefinition) customMethod.invoke(
                        Criteria.where(attributePath),
                        ".*" + convertedValue + ".*",
                        "i");
            }
            case("eq"): methodName = "is"; break;
        }


        Method method = pathClazz.getMethod(methodName, argumentClazz);
        return (CriteriaDefinition) method.invoke(Criteria.where(attributePath), convertedValue);
    }

    public static Object convertValueByType(String attributeType, String value) {
        switch (attributeType) {
            case "number": {
                return Long.valueOf(value);
            }
            case "boolean": {
                return Boolean.valueOf(value);
            }
            case "list": {
                return (Collection<String>) Collections.arrayToList(value.split(","));
            }
            default: {
                return value;
            }
        }
    }
}
