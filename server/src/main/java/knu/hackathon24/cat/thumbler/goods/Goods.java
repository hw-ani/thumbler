package knu.hackathon24.cat.thumbler.goods;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GOODS")
public class Goods {
  public Goods() {
  }

  public Goods(String name, Long point, String imageUrl, String description) {
    this.name=name;
    this.point = point;
    this.imageUrl = imageUrl;
    this.description = description;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Column(name = "point", nullable = false)
  private Long point;

  @Column(name = "image_url", nullable = false, length = 20)
  private String imageUrl;

  @Column(name = "description", nullable = false, length = 150)
  private String description;
}
