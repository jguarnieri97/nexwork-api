package ar.edu.unlam.tpi.nexwork_api.integration;

import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractUpdateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.WorkContractClientException;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;
import ar.edu.unlam.tpi.nexwork_api.service.WorkContractService;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;
import ar.edu.unlam.tpi.nexwork_api.utils.WorkContractDataHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkContractControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WorkContractService workContractService;

    @MockitoBean
    private DeliveryNoteService deliveryNoteService;
    
    @Test
    void givenValidRequest_whenGetContracts_thenReturnsContractsList() throws Exception {
        // Given
        WorkContractRequest request = WorkContractDataHelper.createWorkContractRequest(1L, "SUPPLIER");
        List<WorkContractResponse> responseList = WorkContractDataHelper.createWorkContractResponseList();
    
        when(workContractService.getContracts(any(WorkContractRequest.class))).thenReturn(responseList);
    
        // When & Then
        mockMvc.perform(post("/nexwork-api/v1/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].codeNumber").value("CONTRACT-1"))
                .andExpect(jsonPath("$.data[0].price").value(150000.0))
                .andExpect(jsonPath("$.data[0].state").value("ACTIVE"));
    
        verify(workContractService).getContracts(any(WorkContractRequest.class));
    }
    
    @Test
    void givenValidId_whenGetContractById_thenReturnsContractDetail() throws Exception {
        // Given
        Long id = 1L;
        WorkContractDetailResponse response = WorkContractDetailResponse.builder()
                .id(id)
                .codeNumber("CONTRACT-1")
                .price(150000.0)
                .dateFrom("2024-03-20")
                .dateTo("2024-03-27")
                .state("ACTIVE")
                .detail("Test contract")
                .suppliers(AccountDataHelper.createUserResponse().getSuppliers())
                .applicants(AccountDataHelper.createUserResponse().getApplicants())
                .workers(List.of(1L, 2L))
                .files(List.of("file1.pdf"))
                .build();
    
        when(workContractService.getContractById(id)).thenReturn(response);
    
        // When & Then
        mockMvc.perform(get("/nexwork-api/v1/contracts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.codeNumber").value("CONTRACT-1"))
                .andExpect(jsonPath("$.data.price").value(150000.0))
                .andExpect(jsonPath("$.data.state").value("ACTIVE"))
                .andExpect(jsonPath("$.data.suppliers").isArray())
                .andExpect(jsonPath("$.data.applicants").isArray())
                .andExpect(jsonPath("$.data.workers").isArray())
                .andExpect(jsonPath("$.data.files").isArray());
    
        verify(workContractService).getContractById(id);
    }
    
    @Test
    void givenValidRequest_whenCreateContract_thenReturnsCreatedContract() throws Exception {
        // Given
        WorkContractCreateRequest request = WorkContractDataHelper.createWorkContractCreateRequest();
        WorkContractResponse response = WorkContractDataHelper.createWorkContractResponse(10L);
    
        when(workContractService.createContract(any(WorkContractCreateRequest.class))).thenReturn(response);
    
        // When & Then
        mockMvc.perform(post("/nexwork-api/v1/contracts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andExpect(jsonPath("$.data.id").value(10))
                .andExpect(jsonPath("$.data.codeNumber").value("CONTRACT-10"))
                .andExpect(jsonPath("$.data.price").value(150000.0))
                .andExpect(jsonPath("$.data.state").value("ACTIVE"));
    
        verify(workContractService).createContract(any(WorkContractCreateRequest.class));
    }
    
    @Test
    void givenValidIdAndRequest_whenFinalizeContract_thenReturnsSuccess() throws Exception {
        // Given
        Long id = 1L;
        WorkContractFinalizeRequest request = WorkContractDataHelper.createContractsFinalizeRequest();
    
        doNothing().when(workContractService).finalizeContract(eq(id), any(WorkContractFinalizeRequest.class));
        doNothing().when(deliveryNoteService).buildDeliveryNote(id);
    
        // When & Then
        mockMvc.perform(post("/nexwork-api/v1/contracts/{id}/finalize", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isEmpty());
    
        verify(workContractService).finalizeContract(eq(id), any(WorkContractFinalizeRequest.class));
    }
    
    @Test
    void givenServiceThrowsException_whenGetContracts_thenReturnsInternalServerError() throws Exception {
        // Given
        WorkContractRequest request = WorkContractDataHelper.createWorkContractRequest(1L, "SUPPLIER");
        when(workContractService.getContracts(any(WorkContractRequest.class))).thenThrow(new WorkContractClientException(ErrorResponse.builder()
                .code(Constants.STATUS_INTERNAL)
                .message(Constants.INTERNAL_ERROR)
                .detail("Error retrieving contracts")
                .build()));
    
        // When & Then
        mockMvc.perform(post("/nexwork-api/v1/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    
        verify(workContractService).getContracts(any(WorkContractRequest.class));
    }
    
    @Test
    void givenServiceThrowsException_whenGetContractById_thenReturnsInternalServerError() throws Exception {
        // Given
        Long id = 1L;
        when(workContractService.getContractById(id)).thenThrow(new WorkContractClientException(ErrorResponse.builder()
                .code(Constants.STATUS_INTERNAL)
                .message(Constants.INTERNAL_ERROR)
                .detail("Error retrieving contract")
                .build()));
    
        // When & Then
        mockMvc.perform(get("/nexwork-api/v1/contracts/{id}", id))
                .andExpect(status().isInternalServerError());
    
        verify(workContractService).getContractById(id);
    }
    
    @Test
    void givenServiceThrowsException_whenCreateContract_thenReturnsInternalServerError() throws Exception {
        // Given
        WorkContractCreateRequest request = WorkContractDataHelper.createWorkContractCreateRequest();
        when(workContractService.createContract(any(WorkContractCreateRequest.class))).thenThrow(new WorkContractClientException(ErrorResponse.builder()
                .code(Constants.STATUS_INTERNAL)
                .message(Constants.INTERNAL_ERROR)
                .detail("Error creating contract")
                .build()));
    
        // When & Then
        mockMvc.perform(post("/nexwork-api/v1/contracts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    
        verify(workContractService).createContract(any(WorkContractCreateRequest.class));
    }
    
    @Test
    void givenServiceThrowsException_whenFinalizeContract_thenReturnsInternalServerError() throws Exception {
        // Given
        Long id = 1L;
        WorkContractFinalizeRequest request = WorkContractDataHelper.createContractsFinalizeRequest();
        doThrow(new WorkContractClientException(ErrorResponse.builder()
                .code(Constants.STATUS_INTERNAL)
                .message(Constants.INTERNAL_ERROR)
                .detail("Error finalizing contract")
                .build())).when(workContractService).finalizeContract(eq(id), any(WorkContractFinalizeRequest.class));
    
        // When & Then
        mockMvc.perform(post("/nexwork-api/v1/contracts/{id}/finalize", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    
        verify(workContractService).finalizeContract(eq(id), any(WorkContractFinalizeRequest.class));
        verify(deliveryNoteService, never()).buildDeliveryNote(id);
    }
    
}

