package knu.hackathon24.cat.thumbler.storeMember;

import knu.hackathon24.cat.thumbler.storeMember.StoreMemberRegisterRequestDto;
import knu.hackathon24.cat.thumbler.storeMember.StoreMemberRegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/owner")
@RequiredArgsConstructor
public class StoreMemberController {

    private final StoreMemberService storeMemberService;

    @PostMapping("/register")
    public ResponseEntity<StoreMemberRegisterResponseDto> registerStoreMember(
            @RequestBody StoreMemberRegisterRequestDto requestDto) {
        StoreMemberRegisterResponseDto responseDto = storeMemberService.registerStoreMember(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
