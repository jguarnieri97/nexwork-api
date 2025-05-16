package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractResponse;

import java.util.List;

public interface WorkContractClient {

     /**
     * Método para obtener contratos laborales según el tipo de cuenta
     *
     * @param request parámetros de búsqueda
     * @return lista de contratos obtenidos
     */
    List<WorkContractResponse> getContracts(WorkContractRequest request);

    /**
     * Método para crear un nuevo contrato laboral
     *
     * @param request datos del contrato a crear
     * @return datos del contrato creado
     */
    WorkContractResponse createContract(WorkContractCreateRequest request);

    /**
     * Método para finalizar un contrato laboral
     *
     * @param id id del contrato a finalizar
     * @param request datos del contrato a finalizar
     * @return datos del contrato finalizado
     */
    void finalizeContract(Long id, WorkContractFinalizeRequest request);

}
