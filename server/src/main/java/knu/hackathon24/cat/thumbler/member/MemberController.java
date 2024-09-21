package knu.hackathon24.cat.thumbler.member;

import jakarta.servlet.http.HttpServletResponse;
import knu.hackathon24.cat.thumbler.session.Session;
import knu.hackathon24.cat.thumbler.storeMember.StoreMember;
import knu.hackathon24.cat.thumbler.storeMember.StoreMemberRegisterRequestDto;
import knu.hackathon24.cat.thumbler.storeMember.StoreMemberRegisterResponseDto;
import knu.hackathon24.cat.thumbler.storeMember.StoreMemberService;
import knu.hackathon24.cat.thumbler.userMember.UserMemberService;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final UserMemberService userMemberService;
    private final StoreMemberService storeMemberService;

    @PostMapping("/owner/login")
    public ResponseEntity<StoreMember> loginStoreMember(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        StoreMember storeMember = storeMemberService.loginStoreMember(loginRequest.getUserId(), loginRequest.getPassword(), response);
        String sessionId = Session.getInstance().issueStoreSessionId(storeMember); // 세션 아이디 생성

        return ResponseEntity.ok(storeMember); // 세션 아이디 반환
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserMember> loginUserMember(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        UserMember userMember = userMemberService.loginUser(loginRequest.getUserId(), loginRequest.getPassword(), response);
        String sessionId = Session.getInstance().issueUserSessionId(userMember); // 세션 아이디 생성

        return ResponseEntity.ok(userMember); // 세션 아이디 반환
    }
}
