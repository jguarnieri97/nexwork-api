package ar.edu.unlam.tpi.nexwork_api.dto.request;

import ar.edu.unlam.tpi.nexwork_api.dto.CompanyData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeliveryNoteRequest {
    private Long contractId;
    private List<CompanyData> suppliersData;
    private List<CompanyData> applicantsData;
}
