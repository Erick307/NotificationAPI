package com.ericksilva.notificationapi.notification.dto;

import com.ericksilva.notificationapi.channel.Channel;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class NotificationRequest {
    @NotEmpty(message = "Title is required")
    String title;
    @NotEmpty(message = "Content is required")
    String content;
    @NotEmpty(message = "Channel is required")
    Channel channel;
}
