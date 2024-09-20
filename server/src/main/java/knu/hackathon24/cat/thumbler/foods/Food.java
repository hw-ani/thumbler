package knu.hackathon24.cat.thumbler.foods;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import knu.hackathon24.cat.thumbler.store.Store;

@Entity
@Table(name = "FOOD")
public class Food {
  public Food() {
  }

  public Food(String name, Long originalPrice, Long discountedPrice,
               Long amount, Long deadline, String imageUrl, Store store) {
    this.name = name;
    this.originalPrice = originalPrice;
    this.discountedPrice = discountedPrice;
    this.amount = amount;
    this.deadline = deadline;
    this.imageUrl = imageUrl;
    this.store = store;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Column(name = "original_price", nullable = false)
  private Long originalPrice;

  @Column(name = "discounted_price", nullable = false)
  private Long discountedPrice;

  @Column(name = "amount", nullable = false)
  private Long amount;

  @Column(name = "deadline", nullable = false)
  private Long deadline;

  @Column(name = "image_url", nullable = false, length = 20)
  private String imageUrl;

  @ManyToOne(optional = false)
  @JoinColumn(name = "store", nullable = false)
  private Store store;
}
