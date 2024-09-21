package knu.hackathon24.cat.thumbler.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class PointStoreController {
    @Autowired
    private PointStoreService pointStoreService;

    @GetMapping
    public ResponseEntity<List<Goods>> getGoods(HttpServletRequest request) {
        // 세션 체크 로직 추가 가능
        List<Goods> goodsList = pointStoreService.getAllGoods();
        return ResponseEntity.ok(goodsList);
    }

    @GetMapping("/{goodsId}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable Long goodsId, HttpServletRequest request) {
        // 세션 체크 로직 추가 가능
        return pointStoreService.getGoodsById(goodsId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{goodsId}")
    public ResponseEntity<?> orderGoods(@PathVariable Long goodsId, HttpServletRequest request) {
        // 주문 처리 로직 추가
        // 세션 체크 및 포인트 검증 로직 필요
        return ResponseEntity.ok().build();
    }
}
