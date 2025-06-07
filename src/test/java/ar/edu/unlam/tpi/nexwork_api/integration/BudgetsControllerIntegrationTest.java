package ar.edu.unlam.tpi.nexwork_api.integration;

import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetSupplierResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.BudgetDataHelper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@AutoConfigureMockMvc
public class BudgetsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BudgetsService budgetsService;


    @Test
    void givenApplicantId_whenGetBudgets_thenReturns200AndExpectedData() throws Exception {
        List<BudgetResponse> budgets = BudgetDataHelper.createBudgetResponseList();
    
        when(budgetsService.getBudgets(1L, null)).thenReturn(new ArrayList<>(budgets));
    
        mockMvc.perform(get("/nexwork-api/v1/budgets")
                .param("applicantId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].id").value("budget123"))
            .andExpect(jsonPath("$.data[0].applicantName").value("Solicitante Test"));
    }
    
    @Test
    void givenSupplierId_whenGetBudgets_thenReturns200AndExpectedSupplierData() throws Exception {
        List<BudgetSupplierResponse> budgets = BudgetDataHelper.createBudgetSupplierResponseList();

        when(budgetsService.getBudgets(null, 2L)).thenReturn(new ArrayList<>(budgets));

        mockMvc.perform(get("/nexwork-api/v1/budgets")
                .param("supplierId", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].id").value("budget123"))
            .andExpect(jsonPath("$.data[0].budgetState").value("ACCEPTED"))
            .andExpect(jsonPath("$.data[0].budgetRequestState").value("INITIATED"));
    }


@Test
    void givenBudgetId_whenGetBudgetDetail_thenReturns200AndDetailData() throws Exception {
        BudgetResponseDetail detail = BudgetDataHelper.createBudgetDetailResponse("budget123");
        when(budgetsService.getBudget("budget123")).thenReturn(detail);

        mockMvc.perform(get("/nexwork-api/v1/budgets/budget123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("budget123"))
                .andExpect(jsonPath("$.data.detail.workResume").value("Instalación de red"))
                .andExpect(jsonPath("$.data.budgets[0].supplierName").value("Proveedor Uno"));
    } 

    @Test
    void givenValidRequest_whenFinalizeBudget_thenReturns200AndSuccessResponse() throws Exception {
        Long budgetId = 123L;
        BudgetFinalizeRequest request = BudgetDataHelper.createBudgetFinalizeRequest();

        mockMvc.perform(post("/nexwork-api/v1/budgets/{id}/finalize", budgetId)
                .contentType(APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    
    @Test
    void givenValidRequest_whenUpdateBudget_thenReturns200AndSuccess() throws Exception {

        mockMvc.perform(put("/nexwork-api/v1/budgets/budget123/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "price": 12313,
                        "daysCount": 123,
                        "workerCount": 123,
                        "detail": "DQWDQWDWQDWQDWQDWQDQWDQWD"
                        }
                """))
            .andExpect(status().isOk());
    }
}   