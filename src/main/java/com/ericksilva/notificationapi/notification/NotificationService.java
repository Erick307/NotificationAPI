package com.ericksilva.notificationapi.notification;

import com.ericksilva.notificationapi.channel.Channel;
import com.ericksilva.notificationapi.channel.NotificationSender;
import com.ericksilva.notificationapi.channel.NotificationSenderFactory;
import com.ericksilva.notificationapi.notification.dto.NotificationRequest;
import com.ericksilva.notificationapi.notification.dto.NotificationResponse;
import com.ericksilva.notificationapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationSenderFactory notificationSenderFactory;
    private final NotificationRepository repository;
    private final UserRepository userRepository;

    public NotificationResponse createNotification(NotificationRequest notificationRequest, Long userId) {
        Notification notification = Notification.builder()
                .title(notificationRequest.getTitle())
                .channel(notificationRequest.getChannel())
                .content(notificationRequest.getContent())
                .user(userRepository.getReferenceById(userId))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Notification noti = repository.save(notification);
        NotificationSender sender = notificationSenderFactory.getSender(noti.getChannel());
        sender.send(noti);
        return toResponse(noti);
    }

    public NotificationResponse updateNotification(Long id, NotificationRequest notificationRequest, Long userId) {
        Notification notification = repository.findById(id).orElseThrow(RuntimeException::new);
        if (!notification.getUser().getId().equals(userId)){
            throw new RuntimeException();
        }
        notification.setTitle(notificationRequest.getTitle());
        notification.setContent(notificationRequest.getContent());
        notification.setChannel(notificationRequest.getChannel());
        notification.setUpdatedAt(LocalDateTime.now());
        return toResponse(repository.save(notification));
    }

    public void deleteNotification(Long id, Long userId) {
        Notification notification = repository.findById(id).orElseThrow(RuntimeException::new);
        if(!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException();
        }
        repository.delete(notification);
    }

    public List<NotificationResponse> getNotifications(Long userId) {
        List<Notification> notificationList = repository.findByUserId(userId);

        return notificationList
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public NotificationResponse getNotification(Long userId, Long id){
        Notification notification = repository.findById(id).orElseThrow(RuntimeException::new);
        if(!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException();
        }
        return toResponse(notification);
    }

    private NotificationResponse toResponse(Notification notification){
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getContent(),
                notification.getChannel(),
                notification.getUser().getId(),
                notification.getCreatedAt(),
                notification.getUpdatedAt());
    }
}

