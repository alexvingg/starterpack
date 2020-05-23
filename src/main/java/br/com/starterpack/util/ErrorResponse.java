package br.com.starterpack.util;

import lombok.Data;

@Data
public class ErrorResponse {

    private String error;

    private int status;

}
