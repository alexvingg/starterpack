package br.com.starterpack.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum RequestParamEnum {

    PER_PAGE("perPage"),
    ORDER_TYPE("orderType"),
    ORDER_BY("orderBy"),
    LIMIT("limit"),
    PAGE("page");

    private String value;

    public static List<String> getValues(){
        return  Arrays.asList(RequestParamEnum.values()).stream().map(
                RequestParamEnum::getValue).collect(Collectors.toList());
    }

}
