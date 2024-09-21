package knu.hackathon24.cat.thumbler.qrCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.Cookie;
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
public class QrCodeController {

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private Session session;

    @PostMapping("/qrcode/customer/generate")
    public ResponseEntity<byte[]> generateQrCode(HttpServletRequest request) {
        String sessionId = extractSessionId(request);
        UserMember user = session.getUserMemberBySessionId(sessionId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // QR 코드 이미지 생성 (사용자의 ID와 포인트 정보를 포함)
        byte[] qrCodeImage = generateQrCodeImageWithUserInfo(user.getUserId(), 500L); // 500 포인트 추가

        if (qrCodeImage == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 오류 처리
        }

        // 생성된 QR 코드 저장
        QrCode qrCode = new QrCode("data:image/png;base64," + Base64.getEncoder().encodeToString(qrCodeImage));
        qrCodeRepository.save(qrCode);

        // 응답 반환 (이미지 데이터를 바이트 배열로 반환)
        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(qrCodeImage);
    }

    private byte[] generateQrCodeImageWithUserInfo(String userId, Long points) {
        String data = String.format("https://example.com/scan?userId=%s&points=%d", userId, points);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);

            // QR 코드 이미지를 바이트 배열로 반환
            return byteArrayOutputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null; // 오류 처리
        }
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
