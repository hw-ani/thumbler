package knu.hackathon24.cat.thumbler.ranking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import lombok.Getter;

@Entity
@Table(name = "RANKING")
@Getter
public class Ranking {
  public Ranking() {
  }

  public Ranking(UserMember userMember, Long count) {
    this.userMember = userMember;
    this.count = count;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "user_member", nullable = false)
  private UserMember userMember;

  @Column(name = "count", nullable = false)
  private Long count;
}
