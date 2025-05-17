package ar.edu.unlam.tpi.nexwork_api.service;

import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractResponse;

import java.util.List;

/**
 * Servicio para operaciones relacionadas con contratos laborales
 */
public interface WorkContractService {

    /**
     * Obtener contratos según tipo de cuenta
     *
     * @param request datos del tipo de cuenta e ID
     * @return lista de contratos
     */
    List<WorkContractResponse> getContracts(WorkContractRequest request);

    /**
     * Crear un nuevo contrato laboral
     *
     * @param request datos del contrato a crear
     * @return datos del contrato creado
     */
    WorkContractResponse createContract(WorkContractCreateRequest request);

    /**
     * Finalizar un contrato laboral
     *
     * @param id      id del contrato a finalizar
     * @param request datos del contrato a finalizar
     */
    void finalizeContract(Long id, WorkContractFinalizeRequest request);

    /**
     * Obtener un contrato laboral por su ID
     *
     * @param id id del contrato a obtener
     * @return datos del contrato obtenido
     */
    WorkContractResponse getContractById(Long id);
}