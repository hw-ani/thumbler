package knu.hackathon24.cat.thumbler.storeMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import knu.hackathon24.cat.thumbler.store.Store;

@Entity
@Table(name = "STORE_MEMBER")
public class StoreMember {
  public StoreMember() {
  }

  public StoreMember(String ownerName, String ownerPhone, String ownerEmail,
                     String userId, String password, Store store) {
    this.ownerName = ownerName;
    this.ownerPhone = ownerPhone;
    this.ownerEmail = ownerEmail;
    this.userId = userId;
    this.password = password;
    this.store = store;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "owner_name", nullable = false, length = 20)
  private String ownerName;

  @Column(name = "owner_phone", nullable = false, length = 20)
  private String ownerPhone;

  @Column(name = "owner_email", nullable = false, length = 20)
  private String ownerEmail;

  @Column(name = "userId", nullable = false, length = 20)
  private String userId;

  @Column(name = "password", nullable = false, length = 20)
  private String password;

  @OneToOne(optional = false)
  @JoinColumn(name = "store", nullable = false)
  private Store store;
}
