package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetRequest {

    private Long applicantId;
    private boolean isUrgent;
    private String estimatedDate;
    private String workResume;
    private String workDetail;
    private List<String> files;
    private List<Integer> suppliers;

}
