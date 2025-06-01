package ar.edu.unlam.tpi.nexwork_api.dto.request;

import ar.edu.unlam.tpi.nexwork_api.dto.BodyData;
import ar.edu.unlam.tpi.nexwork_api.dto.CompanyData;
import ar.edu.unlam.tpi.nexwork_api.dto.FootData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeliveryNoteRequest {

    private Long contractId;
    private List<CompanyData> supplierData;
    private List<CompanyData> applicantData;
    private BodyData bodyData;
    private FootData footData;
}
