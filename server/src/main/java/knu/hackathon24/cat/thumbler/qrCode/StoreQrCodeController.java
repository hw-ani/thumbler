package knu.hackathon24.cat.thumbler.qrCode;

import jakarta.servlet.http.Cookie;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import knu.hackathon24.cat.thumbler.userMember.UserMemberRepository;
import lombok.Data;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StoreQrCodeController {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private Session session;

    @Autowired
    private UserMemberRepository userMemberRepository;

    @PostMapping("/qrcode/stores/scan")
    public ResponseEntity<Map<String, Long>> scanQrCode(@RequestBody ScanRequest request, HttpServletRequest httpRequest) {
        String sessionId = extractSessionId(httpRequest);
        StoreMember store = session.getStoreMemberBySessionId(sessionId);

        if (store == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // QR 코드에서 사용자 ID와 포인트 정보 추출
        String userId = extractUserIdFromQrCodeUrl(request.getQrCodeUrl());
        Long points = request.getPoints(); // 스캔 시 항상 500 포인트로 설정

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

    private String extractUserIdFromQrCodeUrl(String qrCodeUrl) {
        // URL에서 사용자 ID와 포인트 정보를 추출하는 로직
        // 예: "https://example.com/scan?userId=hwan&points=500"
        try {
            URI uri = new URI(qrCodeUrl);
            String query = uri.getQuery();
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if ("userId".equals(pair[0])) {
                    return pair[1]; // 사용자 ID 반환
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null; // 사용자 ID가 없는 경우
    }
}

// 요청 DTO 클래스
@Data
class ScanRequest {
    private String storeId;
    private String qrCodeUrl;
    private Long points;
}
