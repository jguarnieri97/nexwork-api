package ar.edu.unlam.tpi.nexwork_api.controller;

import ar.edu.unlam.tpi.nexwork_api.dto.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.WorkContractResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para las operaciones relacionadas con contratos laborales
 */
@RequestMapping("nexwork-api/v1/contracts")
public interface WorkContractController {

    /**
     * Recurso para obtener los contratos laborales según el tipo de cuenta
     *
     * @param request datos del tipo de cuenta, id y límite
     * @return lista de contratos laborales
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<List<WorkContractResponse>> getContracts(@RequestBody WorkContractRequest request);

    /**
     * Recurso para crear un nuevo contrato laboral
     *
     * @param request datos del contrato a crear
     * @return datos del contrato creado
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    GenericResponse<WorkContractResponse> createContract(@RequestBody WorkContractCreateRequest request);

}