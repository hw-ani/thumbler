package knu.hackathon24.cat.thumbler.qrCode;

import jakarta.servlet.http.Cookie;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import knu.hackathon24.cat.thumbler.userMember.UserMemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import knu.hackathon24.cat.thumbler.point.Point;
import knu.hackathon24.cat.thumbler.point.PointRepository;
import knu.hackathon24.cat.thumbler.session.Session;
import knu.hackathon24.cat.thumbler.storeMember.StoreMember;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StoreQrCodeController {

    final private PointRepository pointRepository;

    final private Session session;

    final private UserMemberRepository userMemberRepository;

    @PostMapping("/qrcode/stores/scan")
    public ResponseEntity<Map<String, Long>> scanQrCode(@RequestBody ScanRequest request, HttpServletRequest httpRequest) {
        String sessionId = extractSessionId(httpRequest);
        StoreMember store = session.getStoreMemberBySessionId(sessionId);

        if (store == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 요청에서 사용자 ID와 포인트 정보 추출
        String userId = request.getUserId();
        Long points = request.getPoints();

        // 사용자에게 포인트 적립 로직
        UserMember user = userMemberRepository.findByUserId(userId); // UserMemberRepository를 사용하여 사용자 찾기
        if (user != null) {
            Point point = user.getPoint(); // UserMember에 Point 객체가 연결되어 있다고 가정
            point.setRemains(point.getRemains() + points); // 포인트 적립
            pointRepository.save(point); // 포인트 저장
            Map<String, Long> response = new HashMap<>();
            response.put("point", point.getRemains());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 사용자가 없을 경우
    }


    private String extractSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

// 요청 DTO 클래스
@Data
class ScanRequest {
    private String userId;
    private Long points;
}
