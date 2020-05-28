package br.com.starterpack.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum RoleEnum {

    ADMIN("Admin", "ADMIN"),
    USER("Usuário", "USER");

    private String label;
    private String val;

    public static List<RoleEnum> getValue(){
        return  Arrays.asList(RoleEnum.values());
    }

}
