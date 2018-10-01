package com.example.gonggu.service.user;

import com.example.gonggu.domain.item.Item;
import com.example.gonggu.domain.user.Notification;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.dto.user.NotiContents;
import com.example.gonggu.persistence.user.NotificationRepository;
import com.example.gonggu.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotiService {
    @Autowired
    NotificationRepository notiRepo;
    @Autowired
    UserRepository userRepo;

    public List<Notification> notiList(String userId){
        return userRepo.getOne(Long.parseLong(userId)).getNotifications();
    }

    @Transactional
    public void sendNoti(NotiContents contents){

        Notification noti = new Notification();
        noti.setItemId(contents.getItemId());
        noti.setItemTitle(contents.getItemTitle());
        noti.setTitle(contents.getTitle());
        noti.setContents(contents.getContents());

        User targetUser = contents.getUsr();
        targetUser.getNotifications().add(noti);

        userRepo.save(targetUser);
    }

    public void delNoti(String notiId){
        notiRepo.delete(Long.parseLong(notiId));
    }
}
