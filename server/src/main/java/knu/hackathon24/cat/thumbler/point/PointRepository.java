package knu.hackathon24.cat.thumbler.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    Point findByConsumerId(String consumerId);
}
