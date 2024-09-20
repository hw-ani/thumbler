package knu.hackathon24.cat.thumbler.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import knu.hackathon24.cat.thumbler.foods.Food;
import knu.hackathon24.cat.thumbler.userMember.UserMember;

@Entity
@Table(name = "RESERVATION")
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "reservation_code", nullable = false, length = 20)
  private String reservationCode;

  @Column(name = "reservation_time", nullable = false)
  private Long reservationTime;

  @OneToOne(optional = false)
  @JoinColumn(name = "userMember", nullable = false)
  private UserMember userMember;

  @OneToOne(optional = false)
  @JoinColumn(name = "food", nullable = false)
  private Food food;
}
