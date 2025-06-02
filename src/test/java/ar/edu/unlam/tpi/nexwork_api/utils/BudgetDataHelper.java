package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetData;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetDataRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import java.util.List;

public class BudgetDataHelper {

    public static BudgetResponse createBudgetResponse(String id) {
        return BudgetResponse.builder()
                .id(id)
                .applicantId(1L)
                .applicantName("Solicitante Test")
                .date("2025-05-04T11:00:00Z")
                .build();
    }

    public static BudgetResponseDetail createBudgetResponseDetail(String id) {
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

    public static BudgetDetailResponse createBudgetDetailResponse(String id) {
        return BudgetDetailResponse.builder()
                .id(id)
                .budgetNumber("BUDGET-" + id)
                .isRead(false)
                .applicants(AccountDataHelper.createUserResponse().getApplicants())
                .createdAt("2025-05-04T11:00:00Z")
                .category("Instalación")
                .state("ACTIVE")
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
                                .detail("Detalle del presupuesto")
                                .state("PENDING")
                                .hired(false)
                                .build()
                ))
                .build();
    }

    public static List<BudgetResponse> createBudgetResponseList() {
        return List.of(
                createBudgetResponse("budget123"),
                createBudgetResponse("budget456")
        );
    }

    public static BudgetRequest createBudgetRequest() {
        return BudgetRequest.builder()
                .applicantId(1L)
                .applicantName("Nombre del Solicitante")
                .workResume("Resumen del Trabajo")
                .workDetail("Detalle del Trabajo")
                .category("Categoría de Trabajo")
                .files(List.of("archivo1.pdf", "archivo2.jpg"))
                .suppliers(List.of(
                        BudgetDataRequest.builder()
                                .supplierId(1L)
                                .supplierName("Proveedor 1")
                                .build()
                ))
                .build();
    }

    public static BudgetFinalizeRequest createBudgetFinalizeRequest() {
        return BudgetFinalizeRequest.builder()
                .supplierHired(1L)
                .build();
    }
}