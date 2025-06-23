package ar.edu.unlam.tpi.nexwork_api.client;

import ar.edu.unlam.tpi.nexwork_api.dto.request.NotificationCreateRequest;

public interface NotificationsClient {

    /**
     * Método para crear una notificacion
     * desde el servicio Notifications API
     *
     * @param notificationCreateRequest contiene los datos necesarios para crear la notificación
     */
    void createNotification(NotificationCreateRequest notificationCreateRequest);

}
