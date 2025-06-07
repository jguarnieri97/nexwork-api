package ar.edu.unlam.tpi.nexwork_api.dto.request;

import ar.edu.unlam.tpi.nexwork_api.dto.CompanyData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryNoteRequest {
    private Long contractId;
    private CompanyData supplierData;
    private CompanyData applicantData;
}
