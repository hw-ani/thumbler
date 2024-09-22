package knu.hackathon24.cat.thumbler.storeMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreMemberRegisterResponseDto {
    private String userId;
    public StoreMemberRegisterResponseDto(String userId) {
        this.userId = userId;
    }
}