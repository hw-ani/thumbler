package knu.hackathon24.cat.thumbler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import knu.hackathon24.cat.thumbler.foods.Food;
import knu.hackathon24.cat.thumbler.foods.FoodRepository;
import knu.hackathon24.cat.thumbler.goods.GoodsRepository;
import knu.hackathon24.cat.thumbler.location.Location;
import knu.hackathon24.cat.thumbler.location.LocationRepository;
import knu.hackathon24.cat.thumbler.point.Point;
import knu.hackathon24.cat.thumbler.point.PointRepository;
import knu.hackathon24.cat.thumbler.qrCode.QrCode;
import knu.hackathon24.cat.thumbler.qrCode.QrCodeRepository;
import knu.hackathon24.cat.thumbler.reservation.Reservation;
import knu.hackathon24.cat.thumbler.reservation.ReservationRepository;
import knu.hackathon24.cat.thumbler.store.Store;
import knu.hackathon24.cat.thumbler.store.StoreRepository;
import knu.hackathon24.cat.thumbler.storeMember.StoreMemberRepository;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import knu.hackathon24.cat.thumbler.userMember.UserMemberRepository;

@SpringBootTest
@Transactional
public class MySqlSimpleTest {
  @Autowired UserMemberRepository userMemberRepository;
  @Autowired StoreMemberRepository storeMemberRepository;
  @Autowired StoreRepository storeRepository;
  @Autowired ReservationRepository reservationRepository;
  @Autowired QrCodeRepository qrCodeRepository;
  @Autowired PointRepository pointRepository;
  @Autowired LocationRepository locationRepository;
  @Autowired GoodsRepository goodsRepository;
  @Autowired FoodRepository foodRepository;

  @Test
  public void userInsert() {
    Point point1 = new Point();
    QrCode qrCode1 = new QrCode();
    UserMember userMember1 = new UserMember("hwan", "010-1234-5678", "hwani", "jh1", "hwan", "kakao", "3333", point1, qrCode1);
    pointRepository.save(point1);
    userMemberRepository.save(userMember1);
    
    Optional<UserMember> findUserMember_Optional = userMemberRepository.findById(userMember1.getId());
		UserMember findUserMember = findUserMember_Optional.orElse(null);
		assertEquals(userMember1, findUserMember);
  }

  @Test
  public void locationStoreFoodReservation() {
    Location location1 = new Location("12.555", "456.63");
    locationRepository.save(location1);
    Optional<Location> findLocation_Optional = locationRepository.findById(location1.getId());
		Location findLocation = findLocation_Optional.orElse(null);
		assertEquals(location1, findLocation);

    Store store1 = new Store("myStore", "대구광역시 북구 10번지", "010-8765-4321", "7556854230", location1);
    storeRepository.save(store1);
    Optional<Store> findStore_Optional = storeRepository.findById(store1.getId());
    Store findStore = findStore_Optional.orElse(null);
    assertEquals(store1, findStore);

    Food food1 = new Food("휘낭시에", (Long) 4000L, (Long) 2500L, (Long) 5L, (Long) 1726848986L, "https://img.org/sdkfoe93er939", store1);
    foodRepository.save(food1);
    Optional<Food> findFood_Optional = foodRepository.findById(food1.getId());
    Food findFood = findFood_Optional.orElse(null);
    assertEquals(food1, findFood);

    Point point1 = new Point();
    QrCode qrCode1 = new QrCode();
    UserMember userMember1 = new UserMember("hwan", "010-1234-5678", "hwani", "jh1", "hwan", "kakao", "3333", point1, qrCode1);
    pointRepository.save(point1);
    userMemberRepository.save(userMember1);

    Reservation reservation1 = new Reservation("AQ15846542", (Long) 1726849015L, userMember1, food1);
    reservationRepository.save(reservation1);
    Optional<Reservation> findReservation_Optional = reservationRepository.findById(reservation1.getId());
    Reservation findReservation = findReservation_Optional.orElse(null);
    assertEquals(reservation1, findReservation);

    System.out.println(reservation1.getFood().getOriginalPrice());
  }
}
