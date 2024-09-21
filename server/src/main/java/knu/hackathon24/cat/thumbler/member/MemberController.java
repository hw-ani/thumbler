package knu.hackathon24.cat.thumbler.member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import knu.hackathon24.cat.thumbler.storeMember.StoreMember;
import knu.hackathon24.cat.thumbler.storeMember.StoreMemberService;
import knu.hackathon24.cat.thumbler.userMember.UserMemberService;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import knu.hackathon24.cat.thumbler.session.Session;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final UserMemberService userMemberService;
    private final StoreMemberService storeMemberService;
    private final Session session;

    @PostMapping("/owner/login")
    public ResponseEntity<StoreMember> loginStoreMember(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        StoreMember storeMember = storeMemberService.loginStoreMember(loginRequest.getUserId(), loginRequest.getPassword(), response);

        return ResponseEntity.ok(storeMember);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserMember> loginUserMember(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        UserMember userMember = userMemberService.loginUser(loginRequest.getUserId(), loginRequest.getPassword(), response);

        return ResponseEntity.ok(userMember);
    }

    // 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
        // 쿠키에서 sessionId 가져오기
        String sessionId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("sessionId")) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        // 세션 ID가 없다면 오류 반환
        if (sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(401).body("Session not found");
        }

        // 세션 확인 및 사용자 정보 조회
        UserMember userMember = session.getUserMemberBySessionId(sessionId);
        if (userMember == null) {
            return ResponseEntity.status(401).body("Invalid session");
        }

        // 사용자 프로필 응답
        UserProfileResponseDTO profileResponse = new UserProfileResponseDTO(
                userMember.getNickname(),
                userMember.getPoint().getRemains(),
                "https://image.org/test/" + userMember.getId()
        );

        return ResponseEntity.ok(profileResponse);
    }
}
