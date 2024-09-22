package knu.hackathon24.cat.thumbler.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/withdraw")
    public ResponseEntity<PointResponseDto> withdrawPoints(@RequestBody PointRequestDto requestDto, HttpServletRequest request) {
        String sessionId = getSessionIdFromCookie(request.getCookies());
        Long withdrawnPoints = pointService.withdrawPoints(sessionId, requestDto.getPoint());
        return ResponseEntity.ok(new PointResponseDto(withdrawnPoints));
    }

    @PostMapping("/donate")
    public ResponseEntity<PointResponseDto> donatePoints(@RequestBody PointRequestDto requestDto, HttpServletRequest request) {
        String sessionId = getSessionIdFromCookie(request.getCookies());
        Long donatedPoints = pointService.donatePoints(sessionId, requestDto.getPoint());
        return ResponseEntity.ok(new PointResponseDto(donatedPoints));
    }

    @GetMapping("/history")
    public ResponseEntity<PointHistoryResponseDto> getPointHistory(HttpServletRequest request) {
        String sessionId = getSessionIdFromCookie(request.getCookies());
        return ResponseEntity.ok(pointService.getPointHistory(sessionId));
    }

    @GetMapping
    public ResponseEntity<PointResponseDto> getCurrentPoints(HttpServletRequest request) {
        String sessionId = getSessionIdFromCookie(request.getCookies());
        Long currentPoints = pointService.getCurrentPoints(sessionId);
        return ResponseEntity.ok(new PointResponseDto(currentPoints));
    }

    private String getSessionIdFromCookie(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if ("sessionId".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
