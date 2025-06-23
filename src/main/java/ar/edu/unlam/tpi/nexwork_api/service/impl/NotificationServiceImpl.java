package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.NotificationsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetDataRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountTypeEnum;
import ar.edu.unlam.tpi.nexwork_api.utils.Converter;
import ar.edu.unlam.tpi.nexwork_api.utils.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements ar.edu.unlam.tpi.nexwork_api.service.NotificationService {

    private final NotificationsClient notificationsClient;

    @Value("${notification.url}")
    private String notificationUrl;

    @Override
    public void notifySuppliersOfBudgetRequest(BudgetRequest budgetRequest) {
        for (BudgetDataRequest request : budgetRequest.getSuppliers()) {
            Map<String, String> templateVars = buildTemplateVarsBudget(budgetRequest, request);
            NotificationCreateRequest notification = buildNotificationBudget(request, templateVars);
            try {
                notificationsClient.createNotification(notification);
                log.info("Notificación enviada al proveedor con id {}", request.getSupplierId());
            } catch (Exception e) {
                log.error("Error al enviar notificación al proveedor con id {}: {}", request.getSupplierId(), e.getMessage());
            }
        }
    }

    private Map<String, String> buildTemplateVarsBudget(BudgetRequest budgetRequest, BudgetDataRequest request) {
        return Converter.buildTemplateVariables(
                NotificationType.BUDGET,
                request.getSupplierName(),
                budgetRequest.getApplicantName(),
                budgetRequest.getWorkResume(),
                budgetRequest.getWorkDetail(),
                null,
                null,
                null,
                notificationUrl
        );
    }

    private NotificationCreateRequest buildNotificationBudget(BudgetDataRequest request, Map<String, String> templateVars) {
        return Converter.buildNotification(
                request.getSupplierId(),
                AccountTypeEnum.SUPPLIER.getValue().toUpperCase(),
                true,
                "Nueva solicitud de presupuesto recibida",
                NotificationType.BUDGET,
                "Nueva solicitud de presupuesto recibida",
                templateVars
        );
    }
}