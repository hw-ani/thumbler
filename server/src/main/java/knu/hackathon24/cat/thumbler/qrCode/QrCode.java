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

  @Column(name = "qr_image_url", nullable = true, length = 200)
  private String qrImageUrl;

  /********** interface **********/
  public void setQrImageUrl(String qrImageUrl) {
    this.qrImageUrl = qrImageUrl;
  }
}
