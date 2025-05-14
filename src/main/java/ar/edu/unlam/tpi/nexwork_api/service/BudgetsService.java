package ar.edu.unlam.tpi.nexwork_api.service;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponseDetail;

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

    /**
     * Método para obtener el detalle de un presupuesto por su id
     *
     * @param id: id del presupuesto
     * @return el presupuesto detallado
     */
    BudgetResponseDetail getBudget(String id);

    /**
     * Método para crear un nuevo presupuesto
     *
     * @param budgetRequest: el presupuesto a crear
     */
    void createBudget(BudgetRequest budgetRequest);
}
