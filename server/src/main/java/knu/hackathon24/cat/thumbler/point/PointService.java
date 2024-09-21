package knu.hackathon24.cat.thumbler.point;

import knu.hackathon24.cat.thumbler.session.Session;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import knu.hackathon24.cat.thumbler.userMember.UserMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointService {

    @Autowired
    private UserMemberRepository userMemberRepository;

    @Autowired
    private Session session; // Session을 주입받습니다.

    public Long withdrawPoints(String sessionId, Long points) {
        if (!session.verifyUserSessionId(sessionId)) {
            throw new IllegalArgumentException("Unauthorized access: Invalid session");
        }
        UserMember user = session.getUserMemberBySessionId(sessionId);
        if (user.getPoint().getRemains() < points) {
            throw new IllegalArgumentException("Insufficient points");
        }
        user.getPoint().setRemains(user.getPoint().getRemains() - points);
        return points;
    }

    public Long donatePoints(String sessionId, Long points) {
        if (!session.verifyUserSessionId(sessionId)) {
            throw new IllegalArgumentException("Unauthorized access: Invalid session");
        }
        UserMember user = session.getUserMemberBySessionId(sessionId);
        if (user.getPoint().getRemains() < points) {
            throw new IllegalArgumentException("Insufficient points");
        }
        user.getPoint().setRemains(user.getPoint().getRemains() - points);
        return points;
    }

    public PointHistoryResponseDto getPointHistory(String sessionId) {
        if (!session.verifyUserSessionId(sessionId)) {
            throw new IllegalArgumentException("Unauthorized access: Invalid session");
        }
        UserMember user = session.getUserMemberBySessionId(sessionId);
        // 포인트 히스토리 조회 로직 구현
        List<PointHistoryDto> history = new ArrayList<>();
//        // 예시 데이터 추가
//        history.add(new PointHistoryDto(1726560348L, 3000L, "포인트 출금"));
        return new PointHistoryResponseDto(history);
    }

    public Long getCurrentPoints(String sessionId) {
        if (!session.verifyUserSessionId(sessionId)) {
            throw new IllegalArgumentException("Unauthorized access: Invalid session");
        }
        UserMember user = session.getUserMemberBySessionId(sessionId);
        return user.getPoint().getRemains();
    }
}
