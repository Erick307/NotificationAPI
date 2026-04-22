package com.ericksilva.notificationapi.channel;

import com.ericksilva.notificationapi.notification.Notification;

public interface NotificationSender {
    void send(Notification notification);
    Channel getChannel();
}
