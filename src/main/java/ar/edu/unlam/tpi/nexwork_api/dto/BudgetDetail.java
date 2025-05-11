package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDetail {
    private boolean isUrgent;
    private String estimatedDate;
    private String workResume;
    private String workDetail;
}
