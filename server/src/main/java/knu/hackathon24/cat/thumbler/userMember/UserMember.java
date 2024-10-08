package knu.hackathon24.cat.thumbler.userMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import knu.hackathon24.cat.thumbler.point.Point;
import knu.hackathon24.cat.thumbler.qrCode.QrCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="USER_MEMBER")
@Getter
public class UserMember {
  public UserMember() {
  }

  public UserMember(String name, String phone, String nickname,
                    String userId, String password, String bank, String account,
                    Point point, QrCode qrCode) {
    this.name=name;
    this.phone = phone;
    this.nickname = nickname;
    this.userId = userId;
    this.password = password;
    this.bank = bank;
    this.account = account;
    this.point = point;
    this.qrCode = qrCode;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  @Column(name = "nickname", nullable = false, length = 20)
  private String nickname;

  @Column(name = "userId", nullable = false, length = 20)
  private String userId;

  @Column(name = "password", nullable = false, length = 300)
  private String password;

  @Column(name = "bank", nullable = false, length = 20)
  private String bank;

  @Column(name = "account", nullable = false, length = 20)
  private String account;

  @OneToOne(optional = false)
  @JoinColumn(name = "point", nullable = false)
  private Point point;

  @Setter
  @OneToOne(optional = true)
  @JoinColumn(name = "qrcode", nullable = true)
  private QrCode qrCode;
}
