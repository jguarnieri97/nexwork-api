package ar.edu.unlam.tpi.nexwork_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetRequest {

    @NotNull(message = "Applicant ID cannot be null")
    private Long applicantId;

    @NotNull(message = "Applicant name cannot be null")
    @NotBlank(message = "Applicant name cannot be blank")
    private String applicantName;

    private String workResume;

    private String workDetail;

    @NotNull(message = "Category cannot be null")
    @NotBlank(message = "Category cannot be blank")
    private String category;

    private List<String> files;

    private List<BudgetDataRequest> suppliers;

}
