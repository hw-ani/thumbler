package knu.hackathon24.cat.thumbler.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    @Autowired
    private PointRepository pointRepository;

    public void addPoints(String consumerId, int points) {
        Point pointEntity = pointRepository.findByConsumerId(consumerId);
        if (pointEntity == null) {
            pointEntity = new Point(consumerId, (long) points);
        } else {
            pointEntity.setRemains(pointEntity.getRemains() + points);
        }
        pointRepository.save(pointEntity);
    }

    public Long getCurrentPoints(String consumerId) {
        Point pointEntity = pointRepository.findByConsumerId(consumerId);
        if (pointEntity != null) {
            return pointEntity.getRemains();
        }
        return 0L;
    }
}



