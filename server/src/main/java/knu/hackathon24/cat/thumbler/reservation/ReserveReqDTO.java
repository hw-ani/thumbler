package knu.hackathon24.cat.thumbler.reservation;

import lombok.Getter;

@Getter
public class ReserveReqDTO {
  private Long count;
  private Long reservationTime;

  public ReserveReqDTO(Long count, Long reservationTime) {
    this.count = count;
    this.reservationTime = reservationTime;
  }
}
