package knu.hackathon24.cat.thumbler.ranking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/member")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/ranking10")
    public ResponseEntity<Map<String, Map<String, String>>> getRanking() {
        Map<String, String> rankings = rankingService.getTopRanking(10);
        return ResponseEntity.ok(Map.of("ranking", rankings)); // JSON 형식으로 반환
    }
}
