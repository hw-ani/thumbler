package knu.hackathon24.cat.thumbler.ranking;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RankingService {

    final private RankingRepository rankingRepository;

    public Map<String, String> getTopRanking(int topN) {
        List<Ranking> rankings = rankingRepository.findAllByOrderByCountDesc(); // 순위에 따라 정렬된 리스트 가져오기
        Map<String, String> topRanking = new LinkedHashMap<>();

        for (int i = 0; i < Math.min(topN, rankings.size()); i++) {
            Ranking ranking = rankings.get(i);
            topRanking.put("user" + (i + 1), ranking.getUserMember().getNickname()); // 유저 닉네임 추가
        }

        return topRanking;
    }
}
