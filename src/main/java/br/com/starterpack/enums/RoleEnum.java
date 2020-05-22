package br.com.starterpack.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum RoleEnum {

    ROLE_ADMIN("Admin", "ROLE_ADMIN"),
    ROLE_USER("Usuário", "ROLE_USER");

    private String label;
    private String value;

    public static List<RoleEnum> getValue(){
        return  Arrays.asList(RoleEnum.values());
    }

}
