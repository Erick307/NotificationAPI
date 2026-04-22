package com.ericksilva.notificationapi.channel;

import com.ericksilva.notificationapi.notification.Notification;
import org.springframework.stereotype.Component;

@Component
public class EmailSender implements NotificationSender{

    private final String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    @Override
    public void send(Notification notification) {
        String email = notification.getUser().getEmail();
        if (!email.matches(regex)){
            return;
        }

        String template = "Asunto: " +  notification.getTitle() + "\n" + notification.getContent();
        System.out.println(template);
    }

    @Override
    public Channel getChannel() {
        return Channel.EMAIL;
    }
}
