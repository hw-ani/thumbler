package knu.hackathon24.cat.thumbler.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "POINT")
public class Point {
  public Point() {
    this.remains = 0L;
  }

  public Point(Long initialPoint) {
    this.remains = initialPoint;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "remains", nullable = false)
  private Long remains;

  /********** interface **********/
}
