package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDetail {
    private String workResume;
    private String workDetail;
}
