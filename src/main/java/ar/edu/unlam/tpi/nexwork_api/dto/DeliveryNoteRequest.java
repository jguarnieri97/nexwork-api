package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryNoteRequest {

    private Long contractId;
    private CompanyData supplierData;
    private CompanyData applicantData;
    private BodyData bodyData;
    private FootData footData;
}
