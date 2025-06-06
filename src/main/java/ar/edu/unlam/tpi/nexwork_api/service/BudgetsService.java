package ar.edu.unlam.tpi.nexwork_api.service;

import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailResponse;

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
    List<Object> getBudgets(Long applicantId, Long supplierId);

    /**
     * Método para obtener el detalle de un presupuesto por su id
     *
     * @param id: id del presupuesto
     * @return el presupuesto detallado
     */
    BudgetDetailResponse getBudget(String id);

    /**
     * Método para crear un nuevo presupuesto
     *
     * @param budgetRequest: el presupuesto a crear
     */
    void createBudget(BudgetRequest budgetRequest);

    /**
     * Método para finalizar un presupuesto y contratar un proveedor
     *
     * @param id: id del presupuesto
     * @param budgetFinalizeRequest: el presupuesto elegido para contratar
     */
    void finalizeBudget(String id, BudgetFinalizeRequest budgetFinalizeRequest);

    /**
     * Método para actualizar un presupuesto
     *
     * @param budgetId: id del presupuesto a actualizar
     * @param supplierId: id del proveedor que actualiza el presupuesto
     * @param budgetRequest: los datos a actualizar en el presupuesto
     */
    void updateBudget(String budgetId, Long supplierId, BudgetUpdateDataRequestDto budgetRequest);
}
