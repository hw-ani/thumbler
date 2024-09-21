package knu.hackathon24.cat.thumbler.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import knu.hackathon24.cat.thumbler.foods.Food;
import knu.hackathon24.cat.thumbler.foods.FoodRepository;
import knu.hackathon24.cat.thumbler.session.Session;
import knu.hackathon24.cat.thumbler.storeMember.StoreMember;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/reservation")
@ResponseBody
@RequiredArgsConstructor
public class ReservationController {

  final private ReservationRepository reservationRepository;
  final private FoodRepository foodRepository;
  final private Session session;

  @PostMapping("/{foodId}")
  public ReserveResDTO postReserveFood(@CookieValue("sessionId") String sessionId, @PathVariable String foodId, @RequestBody ReserveReqDTO requestBody) {
    String reservationCode = generateReservationCode();
    Long count = requestBody.getCount();
    Long reservationTime = requestBody.getReservationTime();
    
    Food food = foodRepository.findById(Long.parseLong(foodId)).orElse(null);
    food.setAmount(count);

    UserMember userMember = session.getUserMemberBySessionId(sessionId);

    Reservation newReservation = new Reservation(reservationCode, reservationTime, userMember, food);
    reservationRepository.save(newReservation);

    return new ReserveResDTO(reservationCode);
  }

  @GetMapping("/store")
  public List<Reservation> getReservationListStore(@CookieValue("sessionId") String sessionId) {
    List<Reservation> allReservations = reservationRepository.findAll();
    StoreMember storeMember = session.getStoreMemberBySessionId(sessionId);
    
    List<Reservation> response = new ArrayList<Reservation>();
    for (Reservation reservation : allReservations) {
      if (reservation.getFood().getStore().getId() == storeMember.getStore().getId()) {
        response.add(reservation);
      }
    }

    return response;
  }
  
  @GetMapping("/user")
  public List<Reservation> getReservationListUser(@CookieValue("sessionId") String sessionId) {
    List<Reservation> allReservations = reservationRepository.findAll();
    UserMember userMember = session.getUserMemberBySessionId(sessionId);
    
    List<Reservation> response = new ArrayList<Reservation>();
    for (Reservation reservation : allReservations) {
      if (reservation.getUserMember().getId() == userMember.getId()) {
        response.add(reservation);
      }
    }

    return response;
  }
  
  private String generateReservationCode() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < 2; i++) {
      char randomChar = (char) (random.nextInt(26) + 'A');
      sb.append(randomChar);
    }
    
    for (int i = 0; i < 8; i++) {
      int randomDigit = random.nextInt(10);
      sb.append(randomDigit);
    }
    
    return sb.toString();
  }
}
