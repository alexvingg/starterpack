package br.com.starterpack.core.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PaginateResponse {

    private List items;

    private Long total;
}
