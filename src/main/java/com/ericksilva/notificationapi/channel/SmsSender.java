package com.ericksilva.notificationapi.channel;

import com.ericksilva.notificationapi.notification.Notification;
import org.springframework.stereotype.Component;

@Component
public class SmsSender implements NotificationSender {

    @Override
    public void send(Notification notification) {
        String template = notification.getTitle() + "\n" + notification.getContent();
        String sms = template.length() > 160 ? template.substring(0,160) : template;

        System.out.println(sms);
    }

    @Override
    public Channel getChannel() {
        return Channel.SMS;
    }
}
