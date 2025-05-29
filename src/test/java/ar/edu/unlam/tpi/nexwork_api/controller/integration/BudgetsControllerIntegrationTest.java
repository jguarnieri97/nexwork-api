package ar.edu.unlam.tpi.nexwork_api.controller.integration;


import ar.edu.unlam.tpi.nexwork_api.dto.BudgetData;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BudgetsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetsService budgetsService;

    private BudgetResponse mockBudget;

    @BeforeEach
    void setUp() {
        mockBudget = BudgetResponse.builder()
            .id("budget123")
            .applicantId(1L)
            .applicantName("Nombre del solicitante")
            .date("2025-05-04T11:00:00Z")
            .build();
    }

    @Test
    void getBudgetsByApplicantId_shouldReturnList() throws Exception {
        when(budgetsService.getBudgets(eq(1L), isNull()))
            .thenReturn(List.of(mockBudget));

        mockMvc.perform(get("/nexwork-api/v1/budgets")
                .param("applicantId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].id").value("budget123"))
            .andExpect(jsonPath("$.data[0].applicantName").value("Nombre del solicitante"));
    }

    @Test
void getBudgetsBySupplierId_shouldReturnList() throws Exception {
    when(budgetsService.getBudgets(isNull(), eq(1L)))
        .thenReturn(List.of(BudgetResponse.builder()
            .applicantId(2L)
            .applicantName("Cliente de proveedor")
            .build()));

    mockMvc.perform(get("/nexwork-api/v1/budgets")
            .param("supplierId", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.message").value("SUCCESS"))
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data[0].applicantId").value(2))
        .andExpect(jsonPath("$.data[0].applicantName").value("Cliente de proveedor"));
}
@Test
void getBudgetDetail_shouldReturnBudgetDetail() throws Exception {
    String budgetId = "id123";

    BudgetResponseDetail detailResponse = BudgetResponseDetail.builder()
        .id(budgetId)
        .createdAt("2025-05-04T11:00:00Z")
        .files(List.of("img1Base64", "img2Base64"))
        .detail(BudgetDetail.builder()
            .workResume("Some work resume")
            .workDetail("Some work detail")
            .build())
        .budgets(List.of(
            BudgetData.builder()
                .supplierId(1L)
                .supplierName("Proveedor X")
                .price(150000.0)
                .daysCount(2)
                .workerCount(1)
                .detail("some work detail")
                .build()
        ))
        .build();

    when(budgetsService.getBudget(budgetId)).thenReturn(detailResponse);

    mockMvc.perform(get("/nexwork-api/v1/budgets/" + budgetId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.message").value("SUCCESS"))
        .andExpect(jsonPath("$.data.id").value("id123"))
        .andExpect(jsonPath("$.data.budgets[0].supplierName").value("Proveedor X"))
        .andExpect(jsonPath("$.data.detail.workResume").value("Some work resume"));
}



}