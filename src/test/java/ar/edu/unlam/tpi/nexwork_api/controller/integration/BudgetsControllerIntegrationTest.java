package ar.edu.unlam.tpi.nexwork_api.controller.integration;

import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.BudgetsDataHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BudgetsControllerIntegrationTest {
/* 
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BudgetsService budgetsService;

    @Test
    void getBudgetsReturns200AndExpectedData() throws Exception {
        List<BudgetResponse> budgets = BudgetsDataHelper.buildBudgetList();
        when(budgetsService.getBudgets(1L, null)).thenReturn(budgets);
    
        mockMvc.perform(get("/nexwork-api/v1/budgets")
                .param("applicantId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].id").value("budget123"));
    }
    

    @Test
    void getBudgetDetailReturns200AndDetailData() throws Exception {
        BudgetResponseDetail detail = BudgetsDataHelper.buildBudgetResponseDetail("budget123");
        when(budgetsService.getBudget("budget123")).thenReturn(detail);

        mockMvc.perform(get("/nexwork-api/v1/budgets/budget123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("budget123"))
                .andExpect(jsonPath("$.data.detail.workResume").value("Instalación de red"))
                .andExpect(jsonPath("$.data.budgets[0].supplierName").value("Proveedor Uno"));
    }*/
}