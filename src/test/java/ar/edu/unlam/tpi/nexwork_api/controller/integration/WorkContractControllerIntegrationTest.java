package ar.edu.unlam.tpi.nexwork_api.controller.integration;

import ar.edu.unlam.tpi.nexwork_api.client.WorkContractClient;
import ar.edu.unlam.tpi.nexwork_api.dto.*;
import ar.edu.unlam.tpi.nexwork_api.dto.request.ContractsFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;
import ar.edu.unlam.tpi.nexwork_api.service.DeliveryNoteService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @MockBean
    private WorkContractClient workContractClient;

    @MockBean
    private DeliveryNoteService deliveryNoteService;

    private WorkContractRequest workContractRequest;
    private WorkContractCreateRequest createRequest;
    private ContractsFinalizeRequest finalizeRequest;
    private WorkContractResponse mockResponse;


    @BeforeEach
    void setUp() {
        workContractRequest = WorkContractRequest.builder()
            .id(1L)
            .accountType("Worker")
            .limit(true)
            .build();

        createRequest = WorkContractCreateRequest.builder()
            .price(200000.0)
            .dateFrom("2025-06-01T09:00:00Z")
            .dateTo("2025-06-10T18:00:00Z")
            .detail("Mock: instalación eléctrica")
            .supplierId(2L)
            .applicantId(3L)
            .workers(List.of(4L, 5L))
            .build();

        finalizeRequest = ContractsFinalizeRequest.builder()
            .detail("Trabajo finalizado correctamente")
            .files(List.of("base64mock1", "base64mock2"))
            .build();

        mockResponse = WorkContractResponse.builder()
            .id(101L)
            .price(200000.0)
            .dateFrom("2025-06-01T09:00:00Z")
            .dateTo("2025-06-10T18:00:00Z")
            .state("PENDING")
            .detail("Mock: instalación eléctrica")
            .supplierId(2L)
            .applicantId(3L)
            .build();

            doNothing().when(deliveryNoteService).buildDeliveryNote(anyLong());
    }

    
    @Test
    void getContracts_shouldReturnMockedList() throws Exception {
        when(workContractClient.getContracts(any(WorkContractRequest.class)))
            .thenReturn(List.of(mockResponse));

        mockMvc.perform(post("/nexwork-api/v1/contracts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workContractRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data[0].detail").value("Mock: instalación eléctrica"));
    }

    @Test
    void createContract_shouldReturnMockedContract() throws Exception {
        when(workContractClient.createContract(any(WorkContractCreateRequest.class)))
            .thenReturn(mockResponse);

        mockMvc.perform(post("/nexwork-api/v1/contracts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value(201))
            .andExpect(jsonPath("$.message").value("CREATED"))
            .andExpect(jsonPath("$.data.id").value(101));
    }

    @Test
    void finalizeContract_shouldReturnSuccessMocked() throws Exception {
        Long contractId = 55L;
    
        
        doNothing().when(workContractClient).finalizeContract(eq(contractId), any());
        when(workContractClient.getContractById(contractId))
            .thenReturn(WorkContractResponse.builder().id(contractId).build());
    
        mockMvc.perform(post("/nexwork-api/v1/contracts/" + contractId + "/finalize")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(finalizeRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    
        verify(workContractClient, times(1)).finalizeContract(eq(contractId), any());
    }
    

    @Test
    void getContractById_shouldReturnMockedContract() throws Exception {
        Long contractId = 99L;
        when(workContractClient.getContractById(contractId)).thenReturn(WorkContractResponse.builder().id(contractId).build());

        mockMvc.perform(get("/nexwork-api/v1/contracts/" + contractId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(99));
    }
}
