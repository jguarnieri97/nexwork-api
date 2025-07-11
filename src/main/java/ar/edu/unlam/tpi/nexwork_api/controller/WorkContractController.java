package ar.edu.unlam.tpi.nexwork_api.controller;

import ar.edu.unlam.tpi.nexwork_api.dto.request.*;
import ar.edu.unlam.tpi.nexwork_api.dto.response.DeliveryNoteResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractDetailResponseDto;
import ar.edu.unlam.tpi.nexwork_api.dto.response.WorkContractResponse;

import jakarta.validation.Valid;
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
     * @param request datos del tipo de cuenta, id y límite de contratos
     * @return lista de contratos laborales
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<List<WorkContractResponse>> getContracts(@RequestBody @Valid WorkContractRequest request);

    /**
     * Recurso para crear un nuevo contrato laboral
     *
     * @param request datos del contrato a crear
     * @return datos del contrato creado
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    GenericResponse<WorkContractResponse> createContract(@RequestBody @Valid WorkContractCreateRequest request);

    /**
     * Recurso para finalizar un contrato laboral
     *
     * @param id      id del contrato a finalizar
     * @param request datos del contrato a finalizar
     * @return datos del contrato finalizado
     */

    @PostMapping("{id}/finalize")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<Void> finalizeContract(
            @PathVariable Long id,
            @RequestBody WorkContractFinalizeRequest request);

    /**
     * Recurso para obtener un contrato laboral por su ID
     *
     * @param id id del contrato a obtener
     * @return datos del contrato obtenido
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<WorkContractDetailResponseDto> getContractById(@PathVariable Long id);


    /**
     * Recurso para obtener un remito asociado a un contrato laboral
     *
     * @param contractId id del contrato asociado a la nota de entrega
     * @return datos de la nota de entrega
     */
    @GetMapping("{contractId}/delivery-note")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<DeliveryNoteResponse> getDeliveryNoteById(@PathVariable("contractId") Long contractId);

        /**
         * Recurso para firmar una nota de entrega asociada a un contrato laboral
         *
         * @param id      id del contrato asociado a la nota de entrega
         * @param request datos de la firma de la nota de entrega
         * @return respuesta genérica sin datos
        */
        @PostMapping("{contractId}/delivery-note")
        GenericResponse<DeliveryNoteResponse> signatureDeliveryNote(
                @PathVariable("contractId") Long id,
                @RequestBody @Valid DeliverySignatureRequest request);

    /**
     * Recurso para iniciar un contrato laboral
     *
     * @param contractId id del contrato a iniciar
     * @return respuesta genérica sin datos
     */
    @PostMapping("{contractId}/iniciate")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<Void> iniciateContract(
            @PathVariable("contractId") Long contractId);

    /**
     * Recurso para agregar items a un contrato laboral
     *
     * @param contractId id del contrato a actualizar
     * @param request items del contrato
     */
    @PostMapping("{contractId}/items")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<Void> updateTasks(
            @PathVariable("contractId") Long contractId,
            @RequestBody UpdateItemsRequest request);

}