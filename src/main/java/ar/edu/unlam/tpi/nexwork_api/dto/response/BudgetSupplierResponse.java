package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetSupplierResponse {
    private String id; // ID de la solicitud de presupuesto (BudgetRequestEntity)
    private String budgetNumber;
    private Boolean isRead;
    private Long applicantId;
    private String applicantName;
    private String category;
    private String budgetState; // Estado del presupuesto individual (Budget)
    private String budgetRequestState; // Estado de la solicitud de presupuesto (BudgetRequest)
    private Boolean isHired;
    private String date;
}
