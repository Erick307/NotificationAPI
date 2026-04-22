package com.ericksilva.notificationapi.channel;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationSenderFactory {
    final private List<NotificationSender> senders;

    public NotificationSender getSender(Channel channel) {
        return senders
                .stream()
                .filter(sender -> sender.getChannel().equals(channel))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Channel not found"));
    }
}
