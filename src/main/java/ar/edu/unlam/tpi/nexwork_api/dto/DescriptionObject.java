package ar.edu.unlam.tpi.nexwork_api.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DescriptionObject {

    private String detail;
    private Double price;

}
