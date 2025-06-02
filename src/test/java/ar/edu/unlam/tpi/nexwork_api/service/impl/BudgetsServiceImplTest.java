package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.UserResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.BudgetsClientException;
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

    @InjectMocks
    private BudgetsServiceImpl budgetsService;


    @Test
    void givenApplicantIdWhenGetBudgetsThenReturnBudgetsList() {
        Long applicantId = 1L;
        List<BudgetResponse> mockBudgets = BudgetDataHelper.createBudgetResponseList();

        when(budgetsClient.getApplicantBudgets(applicantId)).thenReturn(mockBudgets);

        List<BudgetResponse> result = budgetsService.getBudgets(applicantId, null);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(budgetsClient).getApplicantBudgets(applicantId);
    }

    @Test
    void givenNoBudgetsWhenGetBudgetsThenReturnEmptyList() {
        Long applicantId = 1L;

        when(budgetsClient.getApplicantBudgets(applicantId)).thenReturn(List.of());

        List<BudgetResponse> result = budgetsService.getBudgets(applicantId, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(budgetsClient).getApplicantBudgets(applicantId);
    }

    @Test
    void givenValidIdWhenGetBudgetThenReturnBudgetDetailResponse() {
        // Given
        String budgetId = "123";
        BudgetResponseDetail mockBudget = BudgetDataHelper.createBudgetResponseDetail(budgetId);
        UserResponse mockUserResponse = AccountDataHelper.createUserResponse();

        when(budgetsClient.getBudgetDetail(budgetId)).thenReturn(mockBudget);
        when(accountsClient.getAccountById(anyList())).thenReturn(mockUserResponse);

        // When
        BudgetDetailResponse result = budgetsService.getBudget(budgetId);

        // Then
        assertNotNull(result);
        verify(budgetsClient).getBudgetDetail(budgetId);
        verify(accountsClient).getAccountById(anyList());
    }

    @Test
    void givenBudgetRequestWhenCreateBudgetThenCallClient() {
        BudgetRequest budgetRequest = BudgetDataHelper.createBudgetRequest();

        budgetsService.createBudget(budgetRequest);

        verify(budgetsClient).createBudget(budgetRequest);
    }

    @Test
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