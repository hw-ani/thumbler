package knu.hackathon24.cat.thumbler.storeMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreMemberRegisterRequestDto {
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;
    private String userId;
    private String password;

    private StoreInfo store;  // StoreInfo 클래스를 새로 만들어 이곳에 넣기
}

@Getter
@Setter
class StoreInfo {
    private String name;
    private String address;
    private String phone;
    private String cert;
}
