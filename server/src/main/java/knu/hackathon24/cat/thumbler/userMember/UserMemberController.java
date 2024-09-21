package knu.hackathon24.cat.thumbler.userMember;

import knu.hackathon24.cat.thumbler.member.LoginRequestDTO;
import knu.hackathon24.cat.thumbler.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/member/users")
public class UserMemberController {

    @Autowired
    private UserMemberService userMemberService;

    @PostMapping("/register")
    public ResponseEntity<UserMemberResponse> registerUser(@RequestBody UserMemberRequest userMemberRequest) {
        UserMember userMember = userMemberService.registerUser(userMemberRequest);

        // 응답 DTO 생성
        UserMemberResponse response = new UserMemberResponse(userMember.getNickname(), userMember.getUserId());
        return ResponseEntity.ok(response);
    }
}
