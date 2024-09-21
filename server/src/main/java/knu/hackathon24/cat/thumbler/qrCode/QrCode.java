package knu.hackathon24.cat.thumbler.qrCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

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

  @Column(name = "qr_image_url", nullable = false, length = 200)
  private String qrImageUrl;

  /********** interface **********/
  // 프론트에서 인증해서 주면 어떤 유저인지 찾아봐야되려나?
}
