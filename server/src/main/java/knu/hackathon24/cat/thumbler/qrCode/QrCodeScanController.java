package knu.hackathon24.cat.thumbler.qrCode;

import knu.hackathon24.cat.thumbler.point.PointService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/qrcode/stores")
public class QrCodeScanController {
    private final PointService pointService;
    private final QrCodeService qrCodeService;

    public QrCodeScanController(PointService pointService, QrCodeService qrCodeService) {
        this.pointService = pointService;
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/scan")
    public ScanResponse scanQrCode(@RequestBody ScanRequest scanRequest, HttpSession session) {
        // 스캔한 가게의 세션 ID 확인 (가게 로그인 정보)
        String storeId = scanRequest.getStoreId();
        if (storeId == null) {
            throw new IllegalArgumentException("가게 ID가 필요합니다.");
        }

        // QR 코드 URL로 소비자 찾기
        String consumerId = qrCodeService.findConsumerByQrCodeUrl(scanRequest.getQrCodeUrl());
        if (consumerId == null) {
            throw new IllegalArgumentException("유효하지 않은 QR 코드입니다.");
        }

        // 포인트 적립 로직
        pointService.addPoints(consumerId, scanRequest.getPoints());

        // 현재 적립된 포인트 반환
        Long updatedPoints = pointService.getCurrentPoints(consumerId);
        return new ScanResponse(updatedPoints);
    }

    // 요청 DTO
    public static class ScanRequest {
        private String storeId;
        private String qrCodeUrl;
        private int points;

        // Getters and Setters
        public String getStoreId() { return storeId; }
        public void setStoreId(String storeId) { this.storeId = storeId; }

        public String getQrCodeUrl() { return qrCodeUrl; }
        public void setQrCodeUrl(String qrCodeUrl) { this.qrCodeUrl = qrCodeUrl; }

        public int getPoints() { return points; }
        public void setPoints(int points) { this.points = points; }
    }

    // 응답 DTO
    public static class ScanResponse {
        private Long point;

        public ScanResponse(Long point) {
            this.point = point;
        }

        public Long getPoint() {
            return point;
        }
    }
}

