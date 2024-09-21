package knu.hackathon24.cat.thumbler.foods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/food")
@ResponseBody
@RequiredArgsConstructor
public class FoodController {
  final private FoodRepository foodRepository;

  private String[] areas = {"북구", "서구", "동구", "수성", "중구", "달서구", "남구", "달성군", "군위군"};

  @GetMapping("/area/{areaId}")
  public List<Food> getFoods(@PathVariable String areaId) {
    String areaName = areas[Integer.parseInt(areaId)];
    List<Food> allFoods = foodRepository.findAll();
    List<Food> response = new ArrayList<Food>();

    for (Food food: allFoods)
      if (food.getStore().getAddress().contains(areaName))
        response.add(food);
    
    Comparator<Food> comparator = new Comparator<Food>() {
      @Override
      public int compare(Food a, Food b) {
          return a.getDeadline().intValue() - b.getDeadline().intValue();
      }
    };
    response.sort(comparator);
    
    return response;
  }

  @GetMapping("/{foodId}")
  public Food getFoodInDetail(@PathVariable String foodId) {
    return foodRepository.findById(Long.parseLong(foodId)).get();
  }
  
  // 멀티파트
  @PostMapping()
  public String postFood(@RequestParam MultipartFile image,
                         @RequestParam String name,
                         @RequestParam Long originalPrice,
                         @RequestParam Long discountedPrice,
                         @RequestParam Long count,
                         @RequestParam String description,
                         @RequestParam Long deadline) {
      // image upload 후 사진 url 가져오기

      // 디비에 저장하기
      return "success";
  }
}
