package knu.hackathon24.cat.thumbler.qrCode;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/qrcode/customer")
public class QrCodeController {
    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/generate")
    public QrCodeResponse generateQrCode(HttpSession session) throws Exception {
        String consumerId = (String) session.getAttribute("consumerId");
        if (consumerId == null) {
            throw new IllegalArgumentException("로그인된 사용자만 QR 코드를 생성할 수 있습니다.");
        }
        String qrCodeUrl = qrCodeService.generateQrCode(consumerId);
        return new QrCodeResponse(qrCodeUrl);
    }

    // 응답 DTO
    public static class QrCodeResponse {
        private String qrCodeUrl;

        public QrCodeResponse(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }
    }
}

