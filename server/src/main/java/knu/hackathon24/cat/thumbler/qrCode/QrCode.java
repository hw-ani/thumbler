package knu.hackathon24.cat.thumbler.qrCode;

import jakarta.persistence.*;
import knu.hackathon24.cat.thumbler.location.Location;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "QRCODE")
@Getter
public class QrCode {
  public QrCode() {
  }

  public QrCode(String qrImageUrl) {
    this.qrImageUrl = qrImageUrl;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Setter
  @Column(name = "qr_image_url", nullable = false, length = 200)
  private String qrImageUrl;

  @Setter
  @Column(name = "user_id", nullable = false, length = 20)
  private String userId; // userId만 저장

  /********** interface **********/
}
