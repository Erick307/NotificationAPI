package com.ericksilva.notificationapi.notification;

import com.ericksilva.notificationapi.auth.JwtService;
import com.ericksilva.notificationapi.notification.dto.NotificationRequest;
import com.ericksilva.notificationapi.notification.dto.NotificationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtService jwtService;

    @GetMapping()
    public ResponseEntity<List<NotificationResponse>> getNotification(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = jwtService
                .getUserId(authHeader.substring(7))
                .orElseThrow(RuntimeException::new);
        return ResponseEntity
                .ok(notificationService.getNotifications(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Long notificationId
    ) {
        Long userId = jwtService
                .getUserId(authHeader.substring(7))
                .orElseThrow(RuntimeException::new);
        return ResponseEntity
                .ok(notificationService.getNotification(userId,notificationId));
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification (
            @RequestHeader("Authorization") String authHeader,
            @RequestBody NotificationRequest request
    ) {
        Long userId = jwtService
                .getUserId(authHeader.substring(7))
                .orElseThrow(RuntimeException::new);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService.createNotification(request,userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @RequestBody NotificationRequest request
    ){
        Long userId = jwtService
                .getUserId(authHeader.substring(7))
                .orElseThrow(RuntimeException::new);
        return ResponseEntity
                .ok(notificationService.updateNotification(id,request,userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id
    ){
        Long userId = jwtService
                .getUserId(authHeader.substring(7))
                .orElseThrow(RuntimeException::new);
        notificationService.deleteNotification(id,userId);
        return ResponseEntity
                .noContent()
                .build();
    }

}
