package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetData;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import java.util.List;

public class BudgetsDataHelper {

    public static BudgetResponse buildBudgetResponse(String id) {
        return BudgetResponse.builder()
                .id(id)
                .applicantId(1L)
                .applicantName("Solicitante Test")
                .date("2025-05-04T11:00:00Z")
                .build();
    }

    public static BudgetResponseDetail buildBudgetResponseDetail(String id) {
        return BudgetResponseDetail.builder()
                .id(id)
                .createdAt("2025-05-04T11:00:00Z")
                .files(List.of("archivo1.jpg", "archivo2.png"))
                .detail(BudgetDetail.builder()
                        .workResume("Instalación de red")
                        .workDetail("Tendido de cables y configuración")
                        .build())
                .budgets(List.of(
                        BudgetData.builder()
                                .supplierId(1L)
                                .supplierName("Proveedor Uno")
                                .price(120000.0)
                                .daysCount(3)
                                .workerCount(2)
                                .build()
                ))
                .build();
    }

    public static List<BudgetResponse> buildBudgetList() {
        return List.of(
                buildBudgetResponse("budget123"),
                buildBudgetResponse("budget456")
        );
    }
}