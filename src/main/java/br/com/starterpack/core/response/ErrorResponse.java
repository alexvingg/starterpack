package br.com.starterpack.core.response;

import lombok.Data;

@Data
public class ErrorResponse {

    private String error;

    private int status;

}
