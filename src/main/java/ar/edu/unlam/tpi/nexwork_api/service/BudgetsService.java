package ar.edu.unlam.tpi.nexwork_api.service;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;

import java.util.List;

/**
 * Servicio para las operaciones relacionadas con Presupuestos
 */
public interface BudgetsService {

    /**
     * Método para obtener una lista de todos los presupuestos
     * según el solicitante
     *
     * @param applicantId: id del solicitante
     * @return la lista de presupuestos
     */
    List<BudgetResponse> getBudgets(Long applicantId);

}
