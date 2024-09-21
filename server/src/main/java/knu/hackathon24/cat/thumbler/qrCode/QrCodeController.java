package knu.hackathon24.cat.thumbler.qrCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.Cookie;
import knu.hackathon24.cat.thumbler.imageUpload.ImageUpload;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import knu.hackathon24.cat.thumbler.session.Session;
import knu.hackathon24.cat.thumbler.userMember.UserMember;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class QrCodeController {

    final private QrCodeRepository qrCodeRepository;

    final private Session session;

    @PostMapping("/qrcode/customer/generate")
    public ResponseEntity<?> getQrCodeUrl(HttpServletRequest request) {
        String sessionId = extractSessionId(request);
        UserMember user = session.getUserMemberBySessionId(sessionId);

        String userId = user.getUserId();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 세션 ID가 유효하지 않음
        }

        // QR 코드 조회 (userId로 QR 코드 찾기)
        QrCode qrCode = qrCodeRepository.findByUserId(userId);

        if (qrCode == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QR code not found for this user.");
        }

        // 응답 JSON으로 QR 코드 URL 반환
        return ResponseEntity.ok(new QrCodeResponse(qrCode.getQrImageUrl()));
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

    // 응답용 DTO 클래스 정의
    @Data
    private static class QrCodeResponse {
        private String qrCodeUrl;
        public QrCodeResponse(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }
    }
}
