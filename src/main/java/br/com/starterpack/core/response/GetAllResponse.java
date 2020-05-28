package br.com.starterpack.core.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetAllResponse {

    private List items;

    private Long total;
}
