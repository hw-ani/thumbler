package knu.hackathon24.cat.thumbler.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "LOCATION")
@Getter
public class Location {
  public Location() {
  }

  public Location(String latitude, String longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "latitude", nullable = false, length = 20)
  private String latitude;

  @Column(name = "longitude", nullable = false, length = 20)
  private String longitude;

  /********** interface **********/
  public boolean isNearBy(Location comparedLocation) {
    if ((Double.parseDouble(this.latitude) - Double.parseDouble(comparedLocation.getLatitude()) < 0.05)
        && (Double.parseDouble(this.longitude) - Double.parseDouble(comparedLocation.getLongitude()) < 0.05))
      return true;
    return false;
  }
}
