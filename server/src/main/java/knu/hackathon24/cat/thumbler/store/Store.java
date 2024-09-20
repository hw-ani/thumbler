package knu.hackathon24.cat.thumbler.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import knu.hackathon24.cat.thumbler.location.Location;
import lombok.Getter;

@Entity
@Table(name = "STORE")
@Getter
public class Store {
  public Store() {
  }

  public Store(String name, String address, String phone, String cert, Location location) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.cert = cert;
    this.location = location;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Column(name = "address", nullable = false, length = 20)
  private String address;

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  @Column(name = "cert", nullable = false, length = 20)
  private String cert;  // 사업자등록번호 (사업장마다 등록이 원칙)

  @OneToOne(optional = false)
  @JoinColumn(name = "location", nullable = false)
  private Location location;
}
