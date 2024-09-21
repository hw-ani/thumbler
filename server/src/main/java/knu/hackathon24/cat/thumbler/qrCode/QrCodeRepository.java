package knu.hackathon24.cat.thumbler.qrCode;

import knu.hackathon24.cat.thumbler.userMember.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
    QrCode findByUserId(String userId); // userId로 조회
}
