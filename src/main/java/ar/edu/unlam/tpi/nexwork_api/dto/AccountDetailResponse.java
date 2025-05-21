package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Data;

@Data
public class AccountDetailResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String cuit;
}
