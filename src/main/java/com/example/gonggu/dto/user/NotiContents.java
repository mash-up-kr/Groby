package com.example.gonggu.dto.user;

import com.example.gonggu.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotiContents {
    private User usr;
    private Long itemId;
    private String itemTitle;
    private String title;
    private String contents;

    public NotiContents() {
        // Basic Noti Obj
    }

    public NotiContents(int tab , User targetUser , Long itemId ,String itemTitle){
        this.usr = targetUser;
        this.itemId = itemId;
        this.itemTitle = itemTitle;

        switch (tab){
            case 2:
                this.title = "수량조사";
                this.contents = "공동구매에 참여하세요!";
                break;
            case 3:
                this.title = "공구진행";
                this.contents = "공동구매가 진행되고 있습니다.";
                break;
            case 4:
                this.title = "주문 완료";
                this.contents = "참여하신 공구의 주문이 완료되었습니다.";
                break;
            case 5:
                this.title = "배부공지";
                this.contents = "배부공지를 확인해주세요.";
                break;
            default:
                break;

        }
    }
}
