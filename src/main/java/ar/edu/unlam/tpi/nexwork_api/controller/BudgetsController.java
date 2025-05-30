package ar.edu.unlam.tpi.nexwork_api.controller;

import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para las operaciones relacionadas con Presupuestos
 */
@RequestMapping("nexwork-api/v1/budgets")
public interface BudgetsController {


    /**
     * Recurso para obtener una lista de todos los presupuestos
     * según el solicitante
     *
     * @param applicantId: id del solicitante
     * @return la lista de presupuestos
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<List<BudgetResponse>> getBudgets(@RequestParam(required = false) Long applicantId, @RequestParam(required = false) Long supplierId);

    /**
     * Recurso para obtener un presupuesto específico
     *
     * @param id: id del presupuesto
     * @return el presupuesto solicitado
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<BudgetResponseDetail> getBudgetDetail(@PathVariable String id);

    /**
     * Recurso para crear un nuevo presupuesto
     *
     * @param budgetRequest: el presupuesto a crear
     * @return el presupuesto creado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    GenericResponse<Void> createBudget(@RequestBody BudgetRequest budgetRequest);

}
