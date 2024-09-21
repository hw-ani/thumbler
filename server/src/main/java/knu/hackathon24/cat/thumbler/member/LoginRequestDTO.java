package knu.hackathon24.cat.thumbler.member;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private int classType; // 0: 사용자, 1: 사업자
    private String userId;
    private String password;
}

