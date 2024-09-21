package knu.hackathon24.cat.thumbler.ranking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("SELECT r FROM Ranking r ORDER BY r.count DESC")
    List<Ranking> findAllByOrderByCountDesc();
}
