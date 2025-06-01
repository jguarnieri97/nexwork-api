package ar.edu.unlam.tpi.nexwork_api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetRequest {

    private Long applicantId;
    private String applicantName;
    private String workResume;
    private String workDetail;
    private String category;
    private List<String> files;
    private List<BudgetDataRequest> suppliers;

}
