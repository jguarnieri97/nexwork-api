package ar.edu.unlam.tpi.nexwork_api.service;

public interface DeliveryNoteService {

    /**
     * Construye un remito basado en el ID del contrato proporcionado.
     *
     * @param contractId ID del contrato a partir del cual se generará el remito.
     */
    void buildDeliveryNote(Long contractId);

}
