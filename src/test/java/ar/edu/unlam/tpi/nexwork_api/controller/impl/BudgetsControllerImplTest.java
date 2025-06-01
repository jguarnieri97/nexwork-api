package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.BudgetsClientException;
import ar.edu.unlam.tpi.nexwork_api.exceptions.ValidatorException;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.BudgetDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BudgetsControllerImplTest {

    @Mock
    private BudgetsService budgetsService;
    
    @InjectMocks
    private BudgetsControllerImpl budgetsController;

    @Test
    void givenBudgetsExist_whenGetBudgets_thenReturnsGenericResponseWithBudgets() {
        // Given
        List<BudgetResponse> budgets = BudgetDataHelper.createBudgetResponseList();
        when(budgetsService.getBudgets(1L, null)).thenReturn(budgets);
    
        // When
        GenericResponse<List<BudgetResponse>> response = budgetsController.getBudgets(1L, null);
    
        // Then
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(2, response.getData().size());
    }
    
    @Test
    void givenNullId_whenGetBudgets_thenThrowsValidatorException() {
        // When & Then
        assertThrows(ValidatorException.class, () -> budgetsController.getBudgets(null, null));
    }
    
    @Test
    void givenServiceThrowsException_whenGetBudgets_thenThrowsBudgetsClientException() {
        // Given
        when(budgetsService.getBudgets(1L, null)).thenThrow(new BudgetsClientException(ErrorResponse.builder()
                .code(Constants.STATUS_INTERNAL)
                .message(Constants.INTERNAL_ERROR)
                .detail("Error retrieving budgets")
                .build()));
    
        // When & Then
        assertThrows(BudgetsClientException.class, () -> budgetsController.getBudgets(1L, null));
    }
    
    @Test
    void givenValidId_whenGetBudgetDetailById_thenReturnsGenericResponse() {
        // Given
        BudgetDetailResponse budgetDetail = BudgetDataHelper.createBudgetDetailResponse("budget123");
        when(budgetsService.getBudget("budget123")).thenReturn(budgetDetail);
    
        // When
        GenericResponse<BudgetDetailResponse> response = budgetsController.getBudgetDetail("budget123");
    
        // Then
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals("budget123", response.getData().getId());
        assertEquals("BUDGET-budget123", response.getData().getBudgetNumber());
        assertEquals("Instalación", response.getData().getCategory());
        assertEquals("ACTIVE", response.getData().getState());
        assertFalse(response.getData().getIsRead());
        assertNotNull(response.getData().getApplicants());
        assertNotNull(response.getData().getDetail());
        assertNotNull(response.getData().getBudgets());
    }
    
    @Test
    void givenServiceThrowsException_whenGetBudgetDetailById_thenThrowsBudgetsClientException() {
        // Given
        when(budgetsService.getBudget("budget123")).thenThrow(new BudgetsClientException(ErrorResponse.builder()
                .code(Constants.STATUS_INTERNAL)
                .message(Constants.INTERNAL_ERROR)
                .detail("Error retrieving budget detail")
                .build()));
    
        // When & Then
        assertThrows(BudgetsClientException.class, () -> budgetsController.getBudgetDetail("budget123"));
    }
    
    @Test
    void givenValidRequest_whenCreateBudget_thenReturnsGenericResponseWithCreatedStatus() {
        // Given
        BudgetRequest request = BudgetDataHelper.createBudgetRequest();
        doNothing().when(budgetsService).createBudget(request);
    
        // When
        GenericResponse<Void> response = budgetsController.createBudget(request);
    
        // Then
        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getCode());
        assertEquals(Constants.CREATED_MESSAGE, response.getMessage());
        assertNull(response.getData());
    }
    
    @Test
    void givenServiceThrowsException_whenCreateBudget_thenThrowsBudgetsClientException() {
        // Given
        BudgetRequest request = BudgetDataHelper.createBudgetRequest();
        doThrow(new BudgetsClientException(ErrorResponse.builder()
                .code(Constants.STATUS_INTERNAL)
                .message(Constants.INTERNAL_ERROR)
                .detail("Error retrieving budget detail")
                .build())).when(budgetsService).createBudget(request);
    
        // When & Then
        assertThrows(BudgetsClientException.class, () -> budgetsController.createBudget(request));
    }
  
}