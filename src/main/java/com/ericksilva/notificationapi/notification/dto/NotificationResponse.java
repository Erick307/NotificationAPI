package com.ericksilva.notificationapi.notification.dto;

import com.ericksilva.notificationapi.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String title;
    private String content;
    private Channel channel;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
