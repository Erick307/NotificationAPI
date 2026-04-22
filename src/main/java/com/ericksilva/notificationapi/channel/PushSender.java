package com.ericksilva.notificationapi.channel;

import com.ericksilva.notificationapi.notification.Notification;
import org.springframework.stereotype.Component;

@Component
public class PushSender implements NotificationSender {

    @Override
    public void send(Notification notification) {

        //deberia validar el token del user pero no hay token
        String template = "{\"title\":\""+notification.getTitle()+"\",\"content\":\""+notification.getContent()+"\"}";
        System.out.println(template);
    }

    @Override
    public Channel getChannel() {
        return Channel.PUSH;
    }
}
