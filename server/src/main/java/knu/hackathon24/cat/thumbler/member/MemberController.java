package knu.hackathon24.cat.thumbler.member;

import jakarta.servlet.http.HttpServletResponse;
import knu.hackathon24.cat.thumbler.storeMember.StoreMember;
import knu.hackathon24.cat.thumbler.storeMember.StoreMemberService;
import knu.hackathon24.cat.thumbler.userMember.UserMemberService;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final UserMemberService userMemberService;
    private final StoreMemberService storeMemberService;

//    @PostMapping("/owner/login")
//    public ResponseEntity<StoreMember> loginStoreMember(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
//        StoreMember storeMember = storeMemberService.loginStoreMember(loginRequest.getUserId(), loginRequest.getPassword(), response);
//
//        return ResponseEntity.ok(storeMember);
//    }
//
//    @PostMapping("/users/login")
//    public ResponseEntity<UserMember> loginUserMember(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
//        UserMember userMember = userMemberService.loginUser(loginRequest.getUserId(), loginRequest.getPassword(), response);
//
//        return ResponseEntity.ok(userMember);
//    }
}
