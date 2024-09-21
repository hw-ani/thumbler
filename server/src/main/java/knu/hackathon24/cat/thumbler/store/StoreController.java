package knu.hackathon24.cat.thumbler.store;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import knu.hackathon24.cat.thumbler.location.Location;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/store")
@ResponseBody
@RequiredArgsConstructor
public class StoreController {
  final private StoreRepository storeRepository;

  @PostMapping("/near")
  public List<Store> postStoresNearBy(@RequestBody Location userLocation) {
      List<Store> allStores = storeRepository.findAll();
      List<Store> response = new ArrayList<Store>();

      for (Store store: allStores)
        if (store.getLocation().isNearBy(userLocation))
          response.add(store);
      
      return response;
  }
  
}
