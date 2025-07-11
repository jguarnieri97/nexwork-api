package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyData {

    private String companyName;
    private String email;
    private String phone;
    private String address;
    private String cuit;

}
