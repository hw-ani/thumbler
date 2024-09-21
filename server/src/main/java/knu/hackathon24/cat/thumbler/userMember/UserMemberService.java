package knu.hackathon24.cat.thumbler.userMember;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import knu.hackathon24.cat.thumbler.imageUpload.ImageUpload;
import knu.hackathon24.cat.thumbler.point.Point;
import knu.hackathon24.cat.thumbler.point.PointRepository;
import knu.hackathon24.cat.thumbler.qrCode.QrCode;
import knu.hackathon24.cat.thumbler.qrCode.QrCodeRepository;
import knu.hackathon24.cat.thumbler.session.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserMemberService {

    final private UserMemberRepository userMemberRepository;
    final private PointRepository pointRepository;
    final private Session session;
    final private QrCodeRepository qrCodeRepository;
    final private ImageUpload imageUpload;

    public UserMember registerUser(UserMemberRequest userMemberRequest) {
        // 기본 포인트 생성 (초기값 0)
        Point point = new Point(0L);
        pointRepository.save(point);

        // 비밀번호 해싱
        String hashedPassword = hashPassword(userMemberRequest.getPassword());

        // 사용자 생성
        UserMember userMember = new UserMember(
                userMemberRequest.getName(),
                userMemberRequest.getPhone(),
                userMemberRequest.getNickname(),
                userMemberRequest.getUserId(),
                hashedPassword,
                userMemberRequest.getBank(),
                userMemberRequest.getAccount(),
                point// 포인트를 함께 저장
        );

        // 임시 사용자 생성
        Point testPoint = new Point(0L);
        pointRepository.save(testPoint);

        String testHashedPassword = hashPassword("test");

        UserMember testUser = new UserMember(
                "테스트이름",
                "010-1234-5678",
                "테스트닉네임",
                "test",
                testHashedPassword,
                "우리은행",
                "123-456-789",
                testPoint
        );

        // QR 코드 생성
        byte[] qrCodeImage = generateQrCodeImageWithUserInfo(userMember.getUserId(), 500L);
        if (qrCodeImage != null) {
            // 이미지 base64로 변환 후 저장
            String imageString = Base64.getEncoder().encodeToString(qrCodeImage);
            String imageUrl = imageUpload.uploadImage(imageString);

            // QR 코드 URL을 QrCode 엔티티에 저장
            QrCode qrCode = new QrCode(imageUrl);
            qrCodeRepository.save(qrCode);
            qrCode.setUserId(userMember.getUserId());

            //qr usermember에 저장
            userMember.getQrCode().setQrImageUrl(imageUrl);
        }else{
            throw new RuntimeException(("qr 생성 실패..."));
        }

        userMemberRepository.save(testUser);

        return userMemberRepository.save(userMember);
    }

//    public UserMember loginUser(String userId, String password, HttpServletResponse response) {
//        UserMember userMember = userMemberRepository.findByUserId(userId);
//        if (userMember != null && userMember.getPassword().equals(hashPassword(password))) {
//            String sessionId = issueUserSessionId(userMember);
//
//            // 쿠키에 세션 ID 추가
//            Cookie cookie = new Cookie("SESSIONID", sessionId);
//            cookie.setHttpOnly(true); // JavaScript에서 접근하지 못하게 설정
//            cookie.setPath("/"); // 쿠키의 유효 범위를 설정 (기본적으로 모든 경로에서 유효)
//            response.addCookie(cookie); // 응답에 쿠키 추가
//
//            return userMember; // 로그인 성공
//        }
//        throw new IllegalArgumentException("Invalid userId or password"); // 로그인 실패
//    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    public String issueUserSessionId(UserMember userMember) {
        return session.issueUserSessionId(userMember);
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
}

