package knu.hackathon24.cat.thumbler.qrCode;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class QrCodeController {

    @PostMapping("/qrcode/customer")
    public String generateQrCode(HttpServletRequest request) {
        return "ok";
    }
}
