package com.ericksilva.notificationapi.notification.dto;

import com.ericksilva.notificationapi.channel.Channel;
import lombok.Data;

@Data
public class NotificationRequest {
    String title;
    String content;
    Channel channel;
}
