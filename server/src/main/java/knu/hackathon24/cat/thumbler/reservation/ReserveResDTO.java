package knu.hackathon24.cat.thumbler.reservation;

import lombok.Getter;

@Getter
public class ReserveResDTO {
  private String reservationCode;

  public ReserveResDTO(String reservationCode) {
    this.reservationCode = reservationCode;
  }
}
