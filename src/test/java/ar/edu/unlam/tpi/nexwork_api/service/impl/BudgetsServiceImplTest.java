package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailFromBudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetSupplierResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.UserResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.BudgetsClientException;
import ar.edu.unlam.tpi.nexwork_api.service.NotificationService;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.BudgetDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.BudgetUpdateDataRequestHelper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BudgetsServiceImplTest {

    @Mock
    private BudgetsClient budgetsClient;

    @Mock
    private AccountsClient accountsClient;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BudgetsServiceImpl budgetsService;


    @Test
    void givenApplicantId_whenGetBudgets_thenReturnsApplicantBudgets() {
        Long applicantId = 1L;
        List<BudgetResponse> mockBudgets = BudgetDataHelper.createBudgetResponseList();
    
        when(budgetsClient.getApplicantBudgets(applicantId)).thenReturn(mockBudgets);
    
        List<?> result = budgetsService.getBudgets(applicantId, null);
    
        assertNotNull(result);
        assertEquals(mockBudgets.size(), result.size());
        verify(budgetsClient).getApplicantBudgets(applicantId);
        verify(budgetsClient, never()).getSupplierBudgets(any());
    }
    
    @Test
    void givenSupplierId_whenGetBudgets_thenReturnsSupplierBudgets() {
        Long supplierId = 2L;
        List<BudgetSupplierResponse> mockBudgets = BudgetDataHelper.createBudgetSupplierResponseList();
    
        when(budgetsClient.getSupplierBudgets(supplierId)).thenReturn(mockBudgets);
    
        List<?> result = budgetsService.getBudgets(null, supplierId);
    
        assertNotNull(result);
        assertEquals(mockBudgets.size(), result.size());
        verify(budgetsClient).getSupplierBudgets(supplierId);
        verify(budgetsClient, never()).getApplicantBudgets(any());
    }
    
    

    @Test
    void givenValidIdWhenGetBudgetThenReturnBudgetDetailResponse() {
        // Given
        String budgetId = "123";
    
        // El presupuesto crudo que devuelve budgetsClient
        BudgetDetailFromBudgetsClient mockRawBudget = BudgetDataHelper.createBudgetDetailFromBudgetsClient(budgetId);
        
        // El account que devuelve accountsClient
        UserResponse mockUserResponse = AccountDataHelper.createUserResponse();
    
        // Lo que se espera después del merge
        BudgetResponseDetail expected = BudgetDataHelper.createBudgetDetailResponse(budgetId);
    
        when(budgetsClient.getBudgetDetail(budgetId)).thenReturn(mockRawBudget);
        when(accountsClient.getAccountById(anyList())).thenReturn(mockUserResponse);
    
        // When
        BudgetResponseDetail result = budgetsService.getBudget(budgetId);
    
        // Then
        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getApplicant().getId(), result.getApplicant().getId());
        assertEquals(expected.getApplicant().getName(), result.getApplicant().getName());
        assertEquals(expected.getDetail().getWorkResume(), result.getDetail().getWorkResume());
        assertEquals(expected.getBudgets().size(), result.getBudgets().size());
    
        verify(budgetsClient).getBudgetDetail(budgetId);
        verify(accountsClient).getAccountById(anyList());
    }
    

    @Test
    void givenBudgetRequestWhenCreateBudgetThenCallClient() {
        BudgetRequest budgetRequest = BudgetDataHelper.createBudgetRequest();

        doNothing().when(notificationService).notifySuppliersOfBudgetRequest(budgetRequest);
        budgetsService.createBudget(budgetRequest);

        verify(budgetsClient).createBudget(budgetRequest);
    }

    void givenValidRequest_whenUpdateBudget_thenSucceeds() {
        BudgetUpdateDataRequestDto validRequest = BudgetUpdateDataRequestHelper.buildBudgetDataRequestDto();

        doNothing().when(budgetsClient).updateBudget(eq("budget123"), eq(1L), any(BudgetUpdateDataRequestDto.class));

        assertDoesNotThrow(() -> budgetsService.updateBudget("budget123", 1L, validRequest));

        verify(budgetsClient).updateBudget("budget123", 1L, validRequest);
    }

    @Test
    void givenClientError_whenUpdateBudget_thenThrowsException() {

        BudgetUpdateDataRequestDto validRequest = BudgetUpdateDataRequestHelper.buildBudgetDataRequestDto();

        doThrow(new BudgetsClientException(ErrorResponse.builder()
                .code(400)
                .message("Bad Request")
                .detail("Invalid budget update data")
                .build()))
            .when(budgetsClient).updateBudget(eq("budget123"), eq(1L), any(BudgetUpdateDataRequestDto.class));

        assertThrows(BudgetsClientException.class, 
            () -> budgetsService.updateBudget("budget123", 1L, validRequest));
    }
}