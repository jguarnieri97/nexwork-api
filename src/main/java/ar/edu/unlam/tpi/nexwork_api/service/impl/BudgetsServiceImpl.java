package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetsServiceImpl implements BudgetsService {

    private final BudgetsClient budgetsClient;

    @Override
    public List<BudgetResponse> getBudgets(Long applicantId) {

        log.info("Obteniendo presupuestos para el solicitante: {}", applicantId);

        var budgets = budgetsClient.getBudgets(applicantId);

        log.info("Cantidad de presupuestos obtenidos: {}", budgets.size());

        return budgets;
    }

    @Override
    public BudgetResponseDetail getBudget(Long id) {

        log.info("Obteniendo presupuesto con id: {}", id);

        var budget = budgetsClient.getBudgetDetail(id);

        log.info("Presupuesto obtenido: {}", Converter.convertToString(budget));

        return budget;
    }
}
