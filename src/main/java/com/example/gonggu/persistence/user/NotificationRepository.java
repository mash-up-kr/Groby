package com.example.gonggu.persistence.user;

import com.example.gonggu.domain.user.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification,Long> {
}
