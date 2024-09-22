package knu.hackathon24.cat.thumbler.qrCode;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import knu.hackathon24.cat.thumbler.session.Session;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class QrCodeController {

    final private Session session;

    @GetMapping("/qrcode/customer")
    public String generateQrCode(@CookieValue("sessionId") String sessionId) {
        UserMember userMember = session.getUserMemberBySessionId(sessionId);
        return userMember.getQrCode().getQrImageUrl();
    }
}
